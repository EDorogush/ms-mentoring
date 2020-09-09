package com.home.ms.shoppingcart.service.purchasehistory;

class RequestDetail {
  private final int maxAttempt;
  private final String userId;
  private final String requestUrl;
  private int currentAttempt;

  // todo: do not like that some fields are final, some not
  RequestDetail(int maxAttempt, int currentAttempt, String userId, String requestUrl) {
    this.maxAttempt = maxAttempt;
    this.userId = userId;
    this.requestUrl = requestUrl;
    this.currentAttempt = currentAttempt;
  }

  int getMaxAttempt() {
    return maxAttempt;
  }

  String getUserId() {
    return userId;
  }

  String getRequestUrl() {
    return requestUrl;
  }

  int getCurrentAttempt() {
    return currentAttempt;
  }

  void setCurrentAttempt(int currentAttempt) {
    this.currentAttempt = currentAttempt;
  }
}
