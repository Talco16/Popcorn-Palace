package com.popcornpalace.moviebookingsystem.util.exception;

import static org.springframework.http.HttpStatus.*;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleNotFound(
      EntityNotFoundException ex, HttpServletRequest req) {
    return build(NOT_FOUND, "Not Found", ex.getMessage(), req, null);
  }

  @ExceptionHandler(IllegalStateException.class)
  public ResponseEntity<ErrorResponse> handleConflict(
      IllegalStateException ex, HttpServletRequest req) {
    return build(CONFLICT, "Conflict", ex.getMessage(), req, null);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidation(
      MethodArgumentNotValidException ex, HttpServletRequest req) {
    List<ErrorResponse.FieldError> fields =
        ex.getBindingResult().getFieldErrors().stream()
            .map(fe -> new ErrorResponse.FieldError(fe.getField(), fe.getDefaultMessage()))
            .toList();
    return build(BAD_REQUEST, "Validation Failed", "Request validation failed", req, fields);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorResponse> handleConstraint(
      ConstraintViolationException ex, HttpServletRequest req) {
    List<ErrorResponse.FieldError> fields =
        ex.getConstraintViolations().stream()
            .map(v -> new ErrorResponse.FieldError(v.getPropertyPath().toString(), v.getMessage()))
            .toList();
    return build(BAD_REQUEST, "Constraint Violation", "Request validation failed", req, fields);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponse> handleParse(
      HttpMessageNotReadableException ex, HttpServletRequest req) {
    return build(BAD_REQUEST, "Malformed JSON", ex.getMostSpecificCause().getMessage(), req, null);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ErrorResponse> handleSql(
      DataIntegrityViolationException ex, HttpServletRequest req) {
    return build(
        CONFLICT, "Data Integrity Violation", ex.getMostSpecificCause().getMessage(), req, null);
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ErrorResponse> handleMethod(
      HttpRequestMethodNotSupportedException ex, HttpServletRequest req) {
    return build(METHOD_NOT_ALLOWED, "Method Not Allowed", ex.getMessage(), req, null);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleUnknown(Exception ex, HttpServletRequest req) {
    return build(
        HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", ex.getMessage(), req, null);
  }

  private ResponseEntity<ErrorResponse> build(
      HttpStatus status,
      String error,
      String message,
      HttpServletRequest req,
      List<ErrorResponse.FieldError> details) {
    return ResponseEntity.status(status)
        .body(
            new ErrorResponse(
                status.value(),
                error,
                message != null ? message : error,
                req.getRequestURI(),
                details == null || details.isEmpty() ? null : details));
  }
}
