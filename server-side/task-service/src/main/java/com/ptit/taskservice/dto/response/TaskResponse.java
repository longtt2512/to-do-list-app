package com.ptit.taskservice.dto.response;

import com.ptit.taskservice.dto.TaskCategorySelectionDto;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
/**
 * @author long.truong
 */
public record TaskResponse(
    UUID id,
    String title,
    LocalDate startDate,
    String description,
    String imageKey,
    List<TaskCategorySelectionDto> selections,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    String createdBy,
    String updatedBy
) {}
