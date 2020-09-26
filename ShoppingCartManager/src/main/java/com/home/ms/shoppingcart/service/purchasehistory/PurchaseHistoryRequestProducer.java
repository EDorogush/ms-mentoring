package com.home.ms.shoppingcart.service.purchasehistory;

import com.home.ms.shoppingcart.service.exception.RequestFailedException;
import com.home.ms.shoppingcart.service.exception.SendRequestException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.http.HttpRequest;
import java.text.MessageFormat;
import java.time.Duration;
import java.time.Instant;

@Service
public class PurchaseHistoryRequestProducer {
  private static final Logger logger = LogManager.getLogger();
  private static final String MESSAGE_PATTERN = "request returns code {0}";
  private final HttpRequestResultStatusCodeResolver statusCodeResolver;
  private final RequestHandler<Integer, PurchaseHistoryToPost> requestHandler;

  public PurchaseHistoryRequestProducer(
      PurchaseHistoryPostRequestStatusCodeResolver statusCodeResolver,
      PurchaseHistoryPostRequestHandler requestHandler) {
    this.statusCodeResolver = statusCodeResolver;
    this.requestHandler = requestHandler;
  }


  public int sendPostOne(String userId, String gameId, BigDecimal price, Instant purchaseTime) {
    final HttpRequest request =
        requestHandler.prepareRequest(
            new PurchaseHistoryToPost(userId, gameId, purchaseTime, price));
    boolean lastAttempt = false;
    int attemptCounter = 0;
    Duration delayBeforeNextAttempt;
    do {
      attemptCounter++;
      try {
        int statusCode = requestHandler.performRequest(request, 2);
        if (statusCodeResolver.isRequestSuccess(statusCode)) {
          return statusCode;
        } else {
          lastAttempt = statusCodeResolver.isLastAttempt(statusCode, attemptCounter);
          if (lastAttempt) {
            throw new RequestFailedException(
                MessageFormat.format(MESSAGE_PATTERN, statusCode), statusCode);
          }
          delayBeforeNextAttempt =
              statusCodeResolver.countDelayBeforeAttempt(statusCode, attemptCounter);
        }
      } catch (SendRequestException e) {
        // connection problems, no status code received
        if (lastAttempt) {
          throw new RequestFailedException(e);
        } else {
          lastAttempt = attemptCounter >= 5;
          delayBeforeNextAttempt = Duration.ofSeconds(1);
        }
      }
      // this cycle will be finished through either return statusCode or throwing
      // RequestFailedException
      waitBeforeRequest(delayBeforeNextAttempt);
    } while (true);
  }

  public String getRequestURI() {
    return requestHandler.getRequestURI().toString();
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
