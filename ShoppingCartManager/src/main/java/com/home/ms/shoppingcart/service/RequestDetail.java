package com.home.ms.shoppingcart.service;

public class RequestDetail {
  private final int maxAttempt;
  private final String userId;
  private final String requestUrl;

  public RequestDetail(int maxAttempt, String userId, String requestUrl) {
    this.maxAttempt = maxAttempt;
    this.userId = userId;
    this.requestUrl = requestUrl;
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
}
