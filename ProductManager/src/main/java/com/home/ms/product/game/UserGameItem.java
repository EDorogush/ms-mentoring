package com.home.ms.product.game;

public class UserGameItem {
  private String userId;
  private String gameId;
  /** recordId fits to purchaseItem id from purchase history */
  private String recordId;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getGameId() {
    return gameId;
  }

  public void setGameId(String gameId) {
    this.gameId = gameId;
  }

  public String getRecordId() {
    return recordId;
  }

  public void setRecordId(String recordId) {
    this.recordId = recordId;
  }
}
