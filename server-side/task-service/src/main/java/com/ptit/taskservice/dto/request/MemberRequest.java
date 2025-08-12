package com.ptit.taskservice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ptit.taskservice.entity.Company;

import java.time.LocalDate;

/**
 * @author long.truong
 */
public record MemberRequest(
    String name,
    String code,
    LocalDate dob,
    String className,
    String phone,
    String email,
    Company company
) {
}
