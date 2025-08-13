package com.ptit.taskservice.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author long.truong
 */
public record ProfileResponse(
    UUID id,
    UUID userId,
    String firstName,
    String lastName,
    String email,
    String contactNumber,
    String position,
    String avatarKey,
    String avatarUrl,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    String createdBy,
    String updatedBy
) {}
