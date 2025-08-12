package com.ptit.taskservice.controller;


import com.ptit.taskservice.dto.request.TaskRequest;
import com.ptit.taskservice.dto.response.TaskResponse;
import com.ptit.taskservice.service.TaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * @author long.truong
 */
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

  private final TaskService service;

  public TaskController(TaskService service) {
    this.service = service;
  }

  @PostMapping
  public TaskResponse create(@Valid @RequestBody TaskRequest req) {
    return service.create(req);
  }

  @GetMapping("/{id}")
  public TaskResponse get(@PathVariable UUID id) {
    return service.get(id);
  }

  @GetMapping
  public Page<TaskResponse> list(Pageable pageable) {
    return service.list(pageable);
  }

  @PutMapping("/{id}")
  public TaskResponse update(@PathVariable UUID id, @Valid @RequestBody TaskRequest req) {
    return service.update(id, req);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable UUID id) {
    service.delete(id);
  }

  @PostMapping(path = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public TaskResponse uploadImage(@PathVariable("id") UUID taskId,
                                  @RequestPart("file") @NotNull MultipartFile file) throws Exception {
    return service.upload(taskId, file);
  }
}
