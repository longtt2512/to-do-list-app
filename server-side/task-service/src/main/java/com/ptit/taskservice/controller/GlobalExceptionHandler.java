package com.ptit.taskservice.controller;

import com.ptit.taskservice.service.MessageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;

/**
 * @author long.truong
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  private final MessageService messageService;

  public GlobalExceptionHandler(MessageService messageService) {
    this.messageService = messageService;
  }

  // --- Login failures / bad password ---
  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ApiError> handleBadCredentials(BadCredentialsException ex, HttpServletRequest req) {
    HttpStatus status = HttpStatus.UNAUTHORIZED; // 401
    // Generic message to avoid user enumeration
    return ResponseEntity.status(status).body(
        new ApiError(Instant.now(), status.value(), status.getReasonPhrase(),
            messageService.getMessage("auth.invalid.credentials"), req.getRequestURI(), null)
    );
  }

  // Catch other auth errors (e.g., missing user, disabled, etc.) as 401 with a generic message
  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ApiError> handleAuth(AuthenticationException ex, HttpServletRequest req) {
    HttpStatus status = HttpStatus.UNAUTHORIZED;
    return ResponseEntity.status(status).body(
        new ApiError(Instant.now(), status.value(), status.getReasonPhrase(),
            messageService.getMessage("auth.failed"), req.getRequestURI(), null)
    );
  }

  // Your earlier rules
  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<ApiError> handleNoSuchElement(NoSuchElementException ex, HttpServletRequest req) {
    HttpStatus status = HttpStatus.UNAUTHORIZED;
    return ResponseEntity.status(status).body(
        new ApiError(Instant.now(), status.value(), status.getReasonPhrase(), ex.getMessage(), req.getRequestURI(), null)
    );
  }

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<ApiError> handleResponseStatus(ResponseStatusException ex, HttpServletRequest req) {
    HttpStatus status = HttpStatus.valueOf(ex.getStatusCode().value());
    String msg = ex.getReason() != null ? ex.getReason() : ex.getMessage();
    return ResponseEntity.status(status).body(
        new ApiError(Instant.now(), status.value(), status.getReasonPhrase(), msg, req.getRequestURI(), null)
    );
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    List<FieldError> fields = ex.getBindingResult().getFieldErrors().stream()
        .map(fe -> new FieldError(fe.getField(), fe.getDefaultMessage()))
        .toList();
    return ResponseEntity.status(status).body(
        new ApiError(Instant.now(), status.value(), status.getReasonPhrase(),
            messageService.getMessage("validation.failed"), req.getRequestURI(), fields)
    );
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleOther(Exception ex, HttpServletRequest req) {
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    return ResponseEntity.status(status).body(
        new ApiError(Instant.now(), status.value(), status.getReasonPhrase(),
            messageService.getMessage("error.unexpected"), req.getRequestURI(), null)
    );
  }

  public record ApiError(Instant timestamp, int status, String error, String message, String path,
                         List<FieldError> fieldErrors) {
  }

  public record FieldError(String field, String message) {
  }
}
