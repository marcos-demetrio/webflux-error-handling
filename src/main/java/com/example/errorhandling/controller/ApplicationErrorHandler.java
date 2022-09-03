package com.example.errorhandling.controller;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.example.errorhandling.dto.ErrorResponse;
import com.example.errorhandling.exception.BusinessException;
import com.example.errorhandling.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationErrorHandler {

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e) {
    var errorResponse = buildErrorResponse(100, e.getMessage());

    return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(errorResponse);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException e) {
    var errorResponse = buildErrorResponse(101, e.getMessage());

    return ResponseEntity.status(NOT_FOUND).body(errorResponse);
  }

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
    var errorResponse = buildErrorResponse(101, e.getMessage());

    return ResponseEntity.status(BAD_REQUEST).body(errorResponse);
  }

  private ErrorResponse buildErrorResponse(int code, String message) {
    return new ErrorResponse(code, message);
  }
}
