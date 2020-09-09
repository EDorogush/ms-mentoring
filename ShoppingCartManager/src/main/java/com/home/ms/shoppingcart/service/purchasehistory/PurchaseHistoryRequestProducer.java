package com.home.ms.shoppingcart.service.purchasehistory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.time.Instant;

@Service
public class PurchaseHistoryRequestProducer {
  private static final Logger logger = LogManager.getLogger();

  private final PurchaseHistoryPostRequestStatusCodeResolver statusCodeResolver;
  private final PurchaseHistoryPostRequestHandler requestHandler;

  public PurchaseHistoryRequestProducer(
      PurchaseHistoryPostRequestStatusCodeResolver statusCodeResolver,
      PurchaseHistoryPostRequestHandler requestHandler) {
    this.statusCodeResolver = statusCodeResolver;
    this.requestHandler = requestHandler;
  }

  public void sendPostOne(String userId, String gameId, BigDecimal price) {
    // todo: do not use instant.now what time should be used?
    // todo: need to pass userId!!!
    final HttpRequest request =
        requestHandler.prepareRequest(new PurchaseHistoryToPost(gameId, Instant.now(), price));
    Duration delay = Duration.ZERO;
    int statusCode = 0;
    RequestDetail requestDetail = new RequestDetail(10, 0, userId, request.uri().toString());
    while (requestDetail.getCurrentAttempt() < requestDetail.getMaxAttempt()) {
      waitBeforeRequest(delay);
      try {
        statusCode = requestHandler.performRequest(request, 2);
        RetryDataModel retryDataModel =
            statusCodeResolver.resolveStatusCode(statusCode, requestDetail);
        requestDetail.setCurrentAttempt(retryDataModel.getAttempt());
        delay = retryDataModel.getDelay();

      } catch (RequestFailedException e) {
        logger.info(e);
        requestDetail.setCurrentAttempt(requestDetail.getCurrentAttempt() + 1);
        delay = Duration.ofSeconds(1);
      }
    }
    if (!(statusCode == HttpStatus.NO_CONTENT.value()) && !(statusCode == HttpStatus.OK.value())) {
      // todo: set it as Checked exception?
      throw new RequestFailedException("failed post request to service");
    }
  }

  private void waitBeforeRequest(Duration delay) {
    try {
      Thread.sleep(delay.toMillis());
    } catch (InterruptedException e) {
      logger.info(e);
      Thread.currentThread().interrupt();
    }
  }
}
