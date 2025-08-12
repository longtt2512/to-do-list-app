package com.ptit.taskservice.service;

import com.ptit.taskservice.dto.TaskCategoryValueDto;
import com.ptit.taskservice.dto.request.TaskCategoryRequest;
import com.ptit.taskservice.dto.response.TaskCategoryResponse;
import com.ptit.taskservice.entity.TaskCategory;
import com.ptit.taskservice.entity.TaskCategoryValue;
import com.ptit.taskservice.repository.TaskCategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;

/**
 * @author long.truong
 */
@Service
@Transactional
public class TaskCategoryServiceImpl implements TaskCategoryService {

  private final TaskCategoryRepository categories;

  public TaskCategoryServiceImpl(TaskCategoryRepository categories) {
    this.categories = categories;
  }

  @Override
  public TaskCategoryResponse create(TaskCategoryRequest req) {
    if (categories.existsByCategoryName(req.categoryName())) {
      throw new IllegalArgumentException("Category already exists: " + req.categoryName());
    }

    TaskCategory c = new TaskCategory();
    c.setCategoryName(req.categoryName());
    c.setValues(mapValues(req.values(), c));           // << uses value+sortOrder from DTO
    return toDto(categories.save(c));
  }

  @Override
  @Transactional(readOnly = true)
  public TaskCategoryResponse get(UUID id) {
    TaskCategory c = categories.findWithValuesById(id)
        .orElseThrow(() -> new NoSuchElementException("Not found: " + id));
    c.getValues().sort(Comparator.comparingInt(TaskCategoryValue::getSortOrder).thenComparing(TaskCategoryValue::getId));
    return toDto(c);
  }

  @Override
  public void delete(UUID id) {
    if (!categories.existsById(id)) throw new NoSuchElementException("Not found: " + id);
    categories.deleteById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<TaskCategoryResponse> list(Pageable pageable) {
    return categories.findAll(pageable).map(this::toDtoShallow);
  }

  @Override
  public TaskCategoryResponse replace(UUID id, TaskCategoryRequest req) {
    TaskCategory c = categories.findWithValuesById(id)
        .orElseThrow(() -> new NoSuchElementException("Not found: " + id));

    if (!c.getCategoryName().equals(req.categoryName())
        && categories.existsByCategoryName(req.categoryName())) {
      throw new IllegalArgumentException("Category already exists: " + req.categoryName());
    }
    c.setCategoryName(req.categoryName());

    // full replace for simplicity (ignore incoming ids)
    c.getValues().clear();
    c.getValues().addAll(mapValues(req.values(), c));

    c.getValues().sort(Comparator.comparingInt(TaskCategoryValue::getSortOrder));
    return toDto(categories.save(c));
  }

  /* ---------- helpers ---------- */

  private List<TaskCategoryValue> mapValues(List<TaskCategoryValueDto> dtos, TaskCategory owner) {
    // validate duplicates
    Set<Integer> seenOrder = new HashSet<>();
    Set<String> seenValue = new HashSet<>();

    List<TaskCategoryValue> list = new ArrayList<>(dtos.size());
    for (TaskCategoryValueDto dto : dtos) {
      String val = safeTrim(dto.value());
      if (val == null) throw new IllegalArgumentException("value is required");
      if (!seenOrder.add(dto.sortOrder())) {
        throw new IllegalArgumentException("Duplicate sortOrder: " + dto.sortOrder());
      }
      if (!seenValue.add(val)) {
        throw new IllegalArgumentException("Duplicate value: " + val);
      }

      TaskCategoryValue v = new TaskCategoryValue();
      v.setCategory(owner);
      v.setValue(val);
      v.setSortOrder(dto.sortOrder());
      list.add(v);
    }
    return list;
  }

  private String safeTrim(String s) {
    if (s == null) return null;
    String t = s.trim();
    return t.isEmpty() ? null : t;
  }

  private TaskCategoryResponse toDto(TaskCategory c) {
    List<TaskCategoryValueDto> vals = c.getValues().stream()
        .sorted(Comparator.comparingInt(TaskCategoryValue::getSortOrder).thenComparing(TaskCategoryValue::getId))
        .map(v -> new TaskCategoryValueDto(v.getId(), v.getValue(), v.getSortOrder()))
        .toList();
    return new TaskCategoryResponse(c.getId(), c.getCategoryName(), vals, c.getCreatedAt(), c.getUpdatedAt());
  }

  // for list paging to avoid N+1; return without values (or add a lightweight top-N if you want)
  private TaskCategoryResponse toDtoShallow(TaskCategory c) {
    return new TaskCategoryResponse(c.getId(), c.getCategoryName(), List.of(), c.getCreatedAt(), c.getUpdatedAt());
  }
}
