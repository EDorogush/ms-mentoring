package com.home.ms.product;

public class RequestFailedException extends RuntimeException {

  public RequestFailedException(String message) {
    super(message);
  }
}
