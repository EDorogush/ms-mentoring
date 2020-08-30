package com.home.ms.product;

import org.springframework.http.HttpStatus;

public class ErrorModel {
  private HttpStatus status;
  private String message;

  public HttpStatus getStatus() {
    return status;
  }

  public void setStatus(HttpStatus status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
