package com.ptit.taskservice.dto.request;

import lombok.Data;

/**
 * @author long.truong
 */
public record LoginRequest(String username, String password) {
}
