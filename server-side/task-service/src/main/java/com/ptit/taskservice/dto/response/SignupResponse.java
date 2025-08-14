package com.ptit.taskservice.dto.response;

import java.util.UUID;

/**
 * @author long.truong
 */
public record SignupResponse(
    UUID userId,
    String username,
    String firstName,
    String lastName,
    String email
) {}
