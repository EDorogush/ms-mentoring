package com.home.ms.shoppingcart.service.invoice;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public class InvoiceToConsume {
  private final String id;
  private final String userId;
  private final boolean approved;
  private final Instant purchaseTime;

  @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
  public InvoiceToConsume(
      @JsonProperty("id") String id,
      @JsonProperty("userId") String userId,
      @JsonProperty("approved") boolean approved,
      @JsonProperty("purchaseTime") Instant purchaseTime) {
    this.id = id;
    this.userId = userId;
    this.approved = approved;
    this.purchaseTime = purchaseTime;
  }

  public String getUserId() {
    return userId;
  }

  public boolean isApproved() {
    return approved;
  }

  public String getId() {
    return id;
  }

  public Instant getPurchaseTime() {
    return purchaseTime;
  }
}
