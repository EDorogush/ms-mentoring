package com.home.ms.product;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler {
  private static final String INTERNAL_ERROR_MESSAGE = "Internal error, sorry";

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorModel> all(Exception e) {
    ErrorModel error = new ErrorModel();
    error.setMessage(INTERNAL_ERROR_MESSAGE);
    error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorModel> notFound(NotFoundException e) {
    ErrorModel error = new ErrorModel();
    error.setMessage(e.getMessage());
    error.setStatus(HttpStatus.NOT_FOUND);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ErrorModel> notSupported(HttpRequestMethodNotSupportedException e) {
    ErrorModel error = new ErrorModel();
    error.setMessage(e.getMessage());
    return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(error);
  }
}
