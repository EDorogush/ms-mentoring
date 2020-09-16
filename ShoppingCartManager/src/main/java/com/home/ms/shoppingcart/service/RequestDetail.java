package com.home.ms.shoppingcart.service;

public class RequestDetail {
  private final int maxAttempt;
  private final String userId;
  private final String requestUrl;
  private int currentAttempt;

  // todo: do not like that some fields are final, some not
  public RequestDetail(int maxAttempt, int currentAttempt, String userId, String requestUrl) {
    this.maxAttempt = maxAttempt;
    this.userId = userId;
    this.requestUrl = requestUrl;
    this.currentAttempt = currentAttempt;
  }

  public int getMaxAttempt() {
    return maxAttempt;
  }

  public String getUserId() {
    return userId;
  }

  public String getRequestUrl() {
    return requestUrl;
  }

  public int getCurrentAttempt() {
    return currentAttempt;
  }

  public void setCurrentAttempt(int currentAttempt) {
    this.currentAttempt = currentAttempt;
  }
}
