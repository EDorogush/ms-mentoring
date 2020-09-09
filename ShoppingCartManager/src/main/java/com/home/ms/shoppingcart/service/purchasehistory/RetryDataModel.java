package com.home.ms.shoppingcart.service.purchasehistory;

import java.time.Duration;
//todo: rename!
/**
 * class to keep data about retry-mechanism iteration for http request
 */
class RetryDataModel {
  private final int attempt;
  private final Duration delay;

  RetryDataModel(int attempt, Duration delay) {
    this.attempt = attempt;
    this.delay = delay;
  }

  int getAttempt() {
    return attempt;
  }

  Duration getDelay() {
    return delay;
  }
}
