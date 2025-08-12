package com.ptit.taskservice.controller;

import com.ptit.taskservice.dto.request.TaskCategoryRequest;
import com.ptit.taskservice.dto.response.TaskCategoryResponse;
import com.ptit.taskservice.service.TaskCategoryService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author long.truong
 */
@RestController
@RequestMapping("/api/task-categories")
public class TaskCategoryController {

  private final TaskCategoryService service;

  public TaskCategoryController(TaskCategoryService service) {
    this.service = service;
  }

  @PostMapping
  public TaskCategoryResponse create(@Valid @RequestBody TaskCategoryRequest req) {
    return service.create(req);
  }

  @GetMapping("/{id}")
  public TaskCategoryResponse get(@PathVariable UUID id) {
    return service.get(id);
  }

  @GetMapping
  public Page<TaskCategoryResponse> list(Pageable pageable) {
    return service.list(pageable);
  }

  @PutMapping("/{id}")
  public TaskCategoryResponse replace(@PathVariable UUID id,
                                      @Valid @RequestBody TaskCategoryRequest req) {
    return service.replace(id, req);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable UUID id) {
    service.delete(id);
  }
}
