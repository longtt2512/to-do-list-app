package com.ptit.taskservice.dto.request;

import com.ptit.taskservice.dto.TaskCategoryValueDto;

import java.util.List;

/**
 * @author long.truong
 */
public record TaskCategoryRequest(
    String categoryName,
    List<TaskCategoryValueDto> values
) {}
