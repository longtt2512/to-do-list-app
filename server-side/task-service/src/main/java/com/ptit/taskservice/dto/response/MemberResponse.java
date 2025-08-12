package com.ptit.taskservice.dto.response;

import com.ptit.taskservice.entity.Company;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author long.truong
 */
public record MemberResponse(
    UUID id,
    String name,
    String code,
    LocalDate dob,
    String className,
    String phone,
    String email,
    Company company,
    String avatarUrl,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}
