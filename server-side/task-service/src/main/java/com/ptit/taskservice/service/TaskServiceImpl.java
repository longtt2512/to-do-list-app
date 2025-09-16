package com.ptit.taskservice.service;


import com.ptit.taskservice.dto.*;
import com.ptit.taskservice.dto.request.TaskCategorySelectionRequest;
import com.ptit.taskservice.dto.request.TaskRequest;
import com.ptit.taskservice.dto.response.TaskResponse;
import com.ptit.taskservice.entity.Task;
import com.ptit.taskservice.entity.TaskCategorySelection;
import com.ptit.taskservice.entity.TaskCategoryValue;
import com.ptit.taskservice.repository.TaskCategoryValueRepository;
import com.ptit.taskservice.repository.TaskRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author long.truong
 */
@Service
@Transactional
public class TaskServiceImpl implements TaskService {

  private final TaskRepository tasks;
  private final TaskCategoryValueRepository valuesRepo;
  private final S3FileService files;

  public TaskServiceImpl(TaskRepository tasks, TaskCategoryValueRepository valuesRepo, S3FileService files) {
    this.tasks = tasks;
    this.valuesRepo = valuesRepo;
    this.files = files;
  }

  @Override
  public TaskResponse create(TaskRequest req) {
    Task t = new Task();
    mapBasics(t, req);
    t.getSelections().clear();
    mapSelections(t, req.selections());
    return toDto(tasks.save(t));
  }

  @Override
  @Transactional(readOnly = true)
  public TaskResponse get(UUID id) {
    Task t = tasks.findWithSelectionsById(id)
        .orElseThrow(() -> new NoSuchElementException("Task not found: " + id));
    return toDto(t);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<TaskResponse> list(Pageable pageable) {
    return tasks.findAllBy(pageable).map(this::toDtoShallow);
  }

  @Override
  public TaskResponse update(UUID id, TaskRequest req) {
    Task t = tasks.findWithSelectionsById(id)
        .orElseThrow(() -> new NoSuchElementException("Task not found: " + id));
    mapBasics(t, req);
    t.getSelections().clear();
    mapSelections(t, req.selections());
    return toDto(tasks.save(t));
  }

  @Override
  public void delete(UUID id) {
    if (!tasks.existsById(id)) throw new NoSuchElementException("Task not found: " + id);
    tasks.deleteById(id);
  }

  /* mapping */

  private void mapBasics(Task t, TaskRequest req) {
    t.setTitle(req.title());
    t.setStartDate(req.startDate());
    t.setDescription(req.description());
    t.setImageKey(req.imageKey());
  }

  private void mapSelections(Task task, List<TaskCategorySelectionRequest> reqs) {
    if (reqs == null || reqs.isEmpty()) return;

    // de-dupe by valueId (the DB also enforces unique (task,value))
    LinkedHashMap<UUID, TaskCategorySelectionRequest> byValue = new LinkedHashMap<>();
    for (TaskCategorySelectionRequest r : reqs) byValue.put(r.valueId(), r);
    var unique = new ArrayList<>(byValue.values());

    // load referenced values
    Map<UUID, TaskCategoryValue> valueById = valuesRepo.findAllById(
        unique.stream().map(TaskCategorySelectionRequest::valueId).collect(Collectors.toSet())
    ).stream().collect(Collectors.toMap(TaskCategoryValue::getId, v -> v));

    for (TaskCategorySelectionRequest r : unique) {
      TaskCategoryValue val = valueById.get(r.valueId());
      if (val == null) throw new IllegalArgumentException("Value not found: " + r.valueId());
      if (!val.getCategory().getId().equals(r.categoryId()))
        throw new IllegalArgumentException("Value doesn't belong to category: " + r.valueId());

      TaskCategorySelection sel = new TaskCategorySelection();
      sel.setTask(task);
      sel.setCategory(val.getCategory());
      sel.setValue(val);
      task.getSelections().add(sel);
    }
  }

  @Transactional
  public TaskResponse upload(UUID taskId, MultipartFile file) throws Exception {
    var task = tasks.findWithSelectionsById(taskId)
        .orElseThrow(() -> new NoSuchElementException("Task not found: " + taskId));

    //String key = buildKey(taskId, Objects.requireNonNullElse(file.getOriginalFilename(), "file"));
    String key = taskId + "/" + Instant.now().toEpochMilli() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
    files.upload(file, key);


    task.setImageKey(key);
    tasks.save(task);

    return toDto(task);
  }

  /* ---------- helpers ---------- */

  private String buildKey(UUID taskId, String originalName) {
    String clean = originalName.replace('\\', '/');
    int slash = clean.lastIndexOf('/');
    if (slash >= 0) clean = clean.substring(slash + 1);
    // keep letters, numbers, dot, dash, underscore; replace others with _
    clean = clean.replaceAll("[^A-Za-z0-9._-]", "_");
    long ts = Instant.now().toEpochMilli();
    return "tasks/%s/%d_%s".formatted(taskId, ts, clean);
  }

  private TaskResponse toDto(Task t) {
    var sels = t.getSelections().stream()
        .sorted(Comparator.comparing(s -> s.getCategory().getCategoryName()))
        .map(s -> new TaskCategorySelectionDto(
            s.getCategory().getId(),
            s.getCategory().getCategoryName(),
            s.getValue().getId(),
            s.getValue().getValue(),
            s.getValue().getSortOrder()))
        .toList();
    return new TaskResponse(t.getId(), t.getTitle(), t.getStartDate(), t.getDescription(), StringUtils.hasText(t.getImageKey()) ? files.presignGet(t.getImageKey(), Duration.ofMinutes(15)).toString() : null,
        sels, t.getCreatedAt(), t.getUpdatedAt(), t.getCreatedBy(), t.getUpdatedBy());
  }

  private TaskResponse toDtoShallow(Task t) {
    var sels = t.getSelections().stream()
        .sorted(Comparator.comparing(s -> s.getCategory().getCategoryName()))
        .map(s -> new TaskCategorySelectionDto(
            s.getCategory().getId(),
            s.getCategory().getCategoryName(),
            s.getValue().getId(),
            s.getValue().getValue(),
            s.getValue().getSortOrder()
        ))
        .toList();

    return new TaskResponse(
        t.getId(),
        t.getTitle(),
        t.getStartDate(),
        t.getDescription(),
        StringUtils.hasText(t.getImageKey()) ? files.presignGet(t.getImageKey(), Duration.ofMinutes(300)).toString() : null,
        sels,
        t.getCreatedAt(),
        t.getUpdatedAt(),
        t.getCreatedBy(),
        t.getUpdatedBy()
    );
  }
}
