package com.home.ms.shoppingcart.service.exception;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * Exception is used to catch all possible scenarios which do not lead to expected success status code in
 * response
 */
public class RequestFailedException extends RuntimeException {
  private final Integer responseStatusCode;

  public RequestFailedException(String message, @Nonnull Integer statusCode) {
    super(message);
    this.responseStatusCode = Objects.requireNonNull(statusCode);
  }

  public RequestFailedException(Throwable cause) {
    super(cause);
    this.responseStatusCode = null;
  }

  public Integer getResponseStatusCode() {
    return responseStatusCode;
  }
}
