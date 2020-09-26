package com.home.ms.shoppingcart.service.purchasehistory;

import java.time.Duration;

public interface HttpRequestResultStatusCodeResolver {
  boolean isLastAttempt(int statusCode, int attemptCounter);

  boolean isRequestSuccess(int statusCode);

  Duration countDelayBeforeAttempt(int statusCode, int attemptCounter);
}
