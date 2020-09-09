package com.home.ms.product.purchasehistory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.home.ms.product.RequestFailedException;
import com.home.ms.product.game.UserGameItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.MessageFormat;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class PostUserHistoryRequestOperation {
  private static final Logger logger = LogManager.getLogger();
  private static final String RETRY_MESSAGE_PATTERN =
      "request to {0} returns code {1}, userId = {2}";
  private static final String NO_RETRY_MESSAGE_PATTERN =
      "request to {0} returns code {1}, userId = {2}";
  private static final String REQUEST_COMPLETED_MESSAGE_PATTERN =
      "request to {0} completed user id = {1} gameId = {2}";
  private final HttpClient client;
  private final URI uri;
  private final ObjectMapper objectMapper;

  public PostUserHistoryRequestOperation(HttpClient client, URI uri, ObjectMapper objectMapper) {
    this.client = client;
    this.uri = uri;
    this.objectMapper = objectMapper;
  }

  public void doOperation(String id, String userId, String gameId) {
    UserGameItem postData = composePostData(id, userId, gameId);
    final String requestBody = dataToJson(postData);
    HttpRequest request =
        HttpRequest.newBuilder(uri)
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .build();

    final int maxAttempt = 10;
    Duration delay = Duration.ZERO;
    int attemptCurrent = 0;
    int statusCode = 0;
    while (attemptCurrent < maxAttempt) {
      waitBeforeRequest(delay);
      statusCode = performRequest(request, 2);
      switch (statusCode) {
        case 500:
          logger.error(MessageFormat.format(RETRY_MESSAGE_PATTERN, uri, statusCode, userId));
          delay = Duration.ofSeconds(2);
          attemptCurrent++;
          break;
        case 404:
          logger.error(MessageFormat.format(RETRY_MESSAGE_PATTERN, uri, statusCode, userId));
          attemptCurrent++;
          break;
        case 400:
        case 403:
          logger.error(MessageFormat.format(NO_RETRY_MESSAGE_PATTERN, uri, statusCode, userId));
          attemptCurrent = maxAttempt;
          break;
        case 200:
        case 204:
          logger.error(
              MessageFormat.format(REQUEST_COMPLETED_MESSAGE_PATTERN, uri, userId, gameId));
          attemptCurrent = maxAttempt;
          break;
        case 0:
          // exception was logged before, just try again
          attemptCurrent++;
          break;
        default:
          logger.error(MessageFormat.format(RETRY_MESSAGE_PATTERN, uri, statusCode, userId));
      }
    }
    if (!(statusCode == HttpStatus.NO_CONTENT.value()) && !(statusCode == HttpStatus.OK.value())) {
      //todo: set it as Checked exception?
      throw new RequestFailedException("failed post request to service");
    }
  }

  private String dataToJson(UserGameItem postData) {
    final String requestBody;
    try {
      requestBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(postData);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException(e);
    }
    return requestBody;
  }

  private int performRequest(HttpRequest request, int timeoutSec) {
    return client
        .sendAsync(request, HttpResponse.BodyHandlers.ofString())
        .orTimeout(timeoutSec, TimeUnit.SECONDS)
        .thenApply(HttpResponse::statusCode)
        .exceptionally(this::handleExceptions)
        .join();
  }

  private int handleExceptions(Throwable ex) {
    if (ex instanceof TimeoutException) {
      logger.error("request time exceeded, ", ex);
    } else {
      logger.error("error during connection:", ex);
    }
    return 0;
  }

  private void waitBeforeRequest(Duration delay) {
    try {
      Thread.sleep(delay.toMillis());
    } catch (InterruptedException e) {
      logger.info(e);
      Thread.currentThread().interrupt();
    }
  }

  private UserGameItem composePostData(String id, String userId, String gameId) {
    UserGameItem postData = new UserGameItem();
    postData.setRecordId(id);
    postData.setGameId(gameId);
    postData.setUserId(userId);
    return postData;
  }
}
