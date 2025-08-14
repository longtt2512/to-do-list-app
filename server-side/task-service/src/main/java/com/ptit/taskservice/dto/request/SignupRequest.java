package com.ptit.taskservice.dto.request;

/**
 * @author long.truong
 */
public record SignupRequest(
    String firstName,
    String lastName,
    String username,
    String email,
    String password,
    String confirmPassword
) {
}
