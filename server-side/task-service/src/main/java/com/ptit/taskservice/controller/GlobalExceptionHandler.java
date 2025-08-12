package com.ptit.taskservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.NoSuchElementException;

/**
 * @author long.truong
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  // 401 for NoSuchElementException (as requested)
  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<ApiError> handleNoSuchElement(NoSuchElementException ex,
                                                      HttpServletRequest req) {
    HttpStatus status = HttpStatus.NOT_FOUND; // 401
    return ResponseEntity.status(status).body(
        new ApiError(Instant.now(), status.value(), status.getReasonPhrase(),
            ex.getMessage(), req.getRequestURI())
    );
  }

  // Fallback: anything else -> 500
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleOtherExceptions(Exception ex,
                                                        HttpServletRequest req) {
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // 500
    return ResponseEntity.status(status).body(
        new ApiError(Instant.now(), status.value(), status.getReasonPhrase(),
            "Unexpected error", req.getRequestURI())
    );
  }

  // Simple error payload
  public record ApiError(
      Instant timestamp,
      int status,
      String error,
      String message,
      String path
  ) {}
}
