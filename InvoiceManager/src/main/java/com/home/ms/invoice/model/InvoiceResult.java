package com.home.ms.invoice.model;

/**
 * class keeps invoice's result data which is sent to other application after invoice processing finished
 */
public class InvoiceResult {
  private final String userId;
  private final boolean approved;

  public InvoiceResult(String userId, boolean approved) {
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
