package com.home.ms.shoppingcart.service.invoice;

public class InvoiceToConsume {
  private final String userId;
  private final boolean approved;

  public InvoiceToConsume(String userId, boolean approved) {
    this.userId = userId;
    this.approved = approved;
  }

  public String getUserId() {
    return userId;
  }

  public boolean isApproved() {
    return approved;
  }
}
