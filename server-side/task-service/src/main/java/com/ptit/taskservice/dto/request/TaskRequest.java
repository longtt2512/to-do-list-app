package com.ptit.taskservice.dto.request;

/**
 * @author long.truong
 */
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public record TaskRequest(
    @NotBlank @Size(max = 200) String title,
    LocalDate startDate,
    @Size(max = 10000) String description,
    String imageKey,
    @Valid List<TaskCategorySelectionRequest> selections
) {}
