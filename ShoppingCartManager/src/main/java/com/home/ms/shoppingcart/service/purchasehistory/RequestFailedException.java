package com.home.ms.shoppingcart.service.purchasehistory;

public class RequestFailedException extends RuntimeException {

  public RequestFailedException(String message) {
    super(message);
  }

  public RequestFailedException(Throwable cause) {
    super(cause);
  }
}
