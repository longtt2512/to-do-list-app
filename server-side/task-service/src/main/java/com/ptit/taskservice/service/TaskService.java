package com.ptit.taskservice.service;

import com.ptit.taskservice.dto.request.TaskRequest;
import com.ptit.taskservice.dto.response.TaskResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * @author long.truong
 */
public interface TaskService {

  TaskResponse create(TaskRequest req);

  TaskResponse get(UUID id);

  Page<TaskResponse> list(Pageable pageable);

  TaskResponse update(UUID id, TaskRequest req);

  void delete(UUID id);

  TaskResponse upload(UUID taskId, MultipartFile file) throws Exception;
}
