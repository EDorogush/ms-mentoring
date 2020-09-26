package com.home.ms.product.purchasehistory.usergamehistory;

import com.home.ms.product.RequestFailedException;
import com.home.ms.product.SendRequestException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.net.http.HttpRequest;
import java.text.MessageFormat;
import java.time.Duration;

@Service
public class UserGameHistoryRequestProducer {
  private static final Logger logger = LogManager.getLogger();
  private static final String MESSAGE_PATTERN = "request returns code {0}";
  private final HttpRequestResultStatusCodeResolver statusCodeResolver;
  private final RequestHandler<Integer, UserGameItemToPost> requestHandler;

  public UserGameHistoryRequestProducer(
      HttpRequestResultStatusCodeResolver statusCodeResolver,
      RequestHandler<Integer, UserGameItemToPost> requestHandler) {
    this.statusCodeResolver = statusCodeResolver;
    this.requestHandler = requestHandler;
  }

  public int sendPostOne(String id, String userId, String gameId) {
    final HttpRequest request =
        requestHandler.prepareRequest(new UserGameItemToPost(userId, gameId, id));
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
    return requestHandler.getRequestURI();
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
