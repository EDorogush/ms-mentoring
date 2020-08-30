package com.home.ms.product;

import java.text.MessageFormat;

public class NotFoundException extends RuntimeException {
  private static final String MESSAGE_NOT_FOUND = "{0} with id = {1} not found";

  public NotFoundException(String name, String id) {
    super(MessageFormat.format(MESSAGE_NOT_FOUND, name, id));
  }

  public NotFoundException(String message) {
    super(message);
  }
}
