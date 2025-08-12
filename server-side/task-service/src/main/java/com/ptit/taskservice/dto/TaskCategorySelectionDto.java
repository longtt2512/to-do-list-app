package com.ptit.taskservice.dto;

import java.util.UUID;

/**
 * @author long.truong
 */
public record TaskCategorySelectionDto(
    UUID categoryId,
    String categoryName,
    UUID valueId,
    String value,
    Integer sortOrder
) {}
