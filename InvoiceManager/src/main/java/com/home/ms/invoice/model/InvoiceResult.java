package com.home.ms.invoice.model;

import java.time.Instant;

/**
 * class keeps invoice's result data which is sent to other application after invoice processing finished
 */
public class InvoiceResult {
  private final String id;
  private final String userId;
  private final boolean approved;
  private final Instant purchaseTime;

  public InvoiceResult(String id, String userId, boolean approved, Instant purchaseTime) {
    this.id = id;
    this.userId = userId;
    this.approved = approved;
    this.purchaseTime = purchaseTime;
  }

  public String getId() {
    return id;
  }

  public String getUserId() {
    return userId;
  }

  public boolean isApproved() {
    return approved;
  }

  public Instant getPurchaseTime() {
    return purchaseTime;
  }
}
