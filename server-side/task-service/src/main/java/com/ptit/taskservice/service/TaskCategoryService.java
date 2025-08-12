package com.ptit.taskservice.service;

import com.ptit.taskservice.dto.request.TaskCategoryRequest;
import com.ptit.taskservice.dto.response.TaskCategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * @author long.truong
 */
public interface TaskCategoryService {

  TaskCategoryResponse create(TaskCategoryRequest req);

  TaskCategoryResponse get(UUID id);

  Page<TaskCategoryResponse> list(Pageable pageable);

  TaskCategoryResponse replace(UUID id, TaskCategoryRequest req);

  void delete(UUID id);
}
