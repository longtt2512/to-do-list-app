package com.ptit.taskservice.dto.request;

import jakarta.validation.constraints.NotBlank;

/**
 * @author long.truong
 */
public record ChangePasswordRequest(
    @NotBlank String currentPassword,
    @NotBlank String newPassword,
    String confirmNewPassword
) {
}
