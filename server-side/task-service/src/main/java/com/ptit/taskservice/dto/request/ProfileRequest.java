package com.ptit.taskservice.dto.request;

/**
 * @author long.truong
 */
public record ProfileRequest(
    String firstName,
    String lastName,
    String email,
    String contactNumber,
    String position
) {
}
