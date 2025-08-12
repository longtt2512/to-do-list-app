package com.ptit.taskservice.dto.response;

import com.ptit.taskservice.dto.TaskCategoryValueDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author long.truong
 */
public record TaskCategoryResponse(
    UUID id,
    String categoryName,
    List<TaskCategoryValueDto> values,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}
