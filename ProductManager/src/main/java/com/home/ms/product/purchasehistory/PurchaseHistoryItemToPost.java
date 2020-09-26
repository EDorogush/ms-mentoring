package com.home.ms.product.purchasehistory;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.Instant;

public class PurchaseHistoryItemToPost {
  private final String userId;
  private final String gameId;
  private final Instant date;
  private final BigDecimal cost;

  @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
  public PurchaseHistoryItemToPost(
      @JsonProperty("userId") String userId,
      @JsonProperty("gameId") String gameId,
      @JsonProperty("date") Instant date,
      @JsonProperty("cost") BigDecimal cost) {
    this.userId = userId;
    this.gameId = gameId;
    this.date = date;
    this.cost = cost;
  }

  public String getUserId() {
    return userId;
  }

  public String getGameId() {
    return gameId;
  }

  public Instant getDate() {
    return date;
  }

  public BigDecimal getCost() {
    return cost;
  }
}
