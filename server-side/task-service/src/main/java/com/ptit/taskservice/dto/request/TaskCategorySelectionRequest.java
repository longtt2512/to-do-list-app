package com.ptit.taskservice.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * @author long.truong
 */
public record TaskCategorySelectionRequest(
    @NotNull UUID categoryId,
    @NotNull UUID valueId
) {}
