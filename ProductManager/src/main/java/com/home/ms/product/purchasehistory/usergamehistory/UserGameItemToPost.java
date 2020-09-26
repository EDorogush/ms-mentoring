package com.home.ms.product.purchasehistory.usergamehistory;

public class UserGameItemToPost {
  private final String userId;
  private final String gameId;
  /** recordId fits to purchaseItem id from purchase history */
  private final String recordId;

  public UserGameItemToPost(String userId, String gameId, String recordId) {
    this.userId = userId;
    this.gameId = gameId;
    this.recordId = recordId;
  }

  public String getUserId() {
    return userId;
  }

  public String getGameId() {
    return gameId;
  }

  public String getRecordId() {
    return recordId;
  }
}
