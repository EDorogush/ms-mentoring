package com.home.ms.product;

/**
 * Exception should be used to catch connection issues like: no connection available, request
 * timeout exceeds etc. this exception should not be used to catch issues connected to wrong status
 * code.
 */
public class SendRequestException extends RuntimeException {
  public SendRequestException(Throwable cause) {
    super(cause);
  }
}
