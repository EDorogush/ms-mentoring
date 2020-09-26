package com.home.ms.shoppingcart.repository;

import org.hibernate.engine.internal.Collections;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * statuses to display shopping cart state: OPEN - shopping cart in edition state, user is able to
 * add and delete elements in shopping cart INVOICE - purchase is formed and invoice is sent. It is
 * not allowed to delete or add elements APPROVED - purchase is approved. REJECTED - purchase is
 * rejected. STUCK - sending data about purchased games to other services failed.
 */
public enum ShoppingCartStatus {
  OPEN("open"),
  INVOICE("invoice sent"),
  APPROVED("invoice approved"),
  REJECTED("invoice rejected"),
  STUCK("stuck");

  private final String value;
//  private Map<String, ShoppingCartStatus> map = Stream.of(ShoppingCartStatus.values())
//          .collect(Collectors.toMap(ShoppingCartStatus.valueOf()))

  ShoppingCartStatus(String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }
}
