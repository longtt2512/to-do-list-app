package com.ptit.taskservice.dto;

import java.util.UUID;

/**
 * @author long.truong
 */
public record TaskCategoryValueDto(
    UUID id,
    String value,
    Integer sortOrder
) {}
