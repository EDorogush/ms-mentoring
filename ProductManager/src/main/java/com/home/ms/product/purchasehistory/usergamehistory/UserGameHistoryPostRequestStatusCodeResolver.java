package com.home.ms.product.purchasehistory.usergamehistory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserGameHistoryPostRequestStatusCodeResolver
    implements HttpRequestResultStatusCodeResolver {

  private static final Logger logger = LogManager.getLogger();
  private static final String RETRY_MESSAGE_PATTERN = "request returns code {0}";
  private static final String UNEXPECTED_CODE_MESSAGE_PATTERN = "unexpected code {0} ";
  private static final Set<Integer> SUCCESS_RESPONSE_STATUS_CODE =
      new HashSet<>(
          Arrays.asList(
              HttpStatus.NO_CONTENT.value(), HttpStatus.CREATED.value(), HttpStatus.OK.value()));

  @Override
  public boolean isLastAttempt(int statusCode, int attemptCounter) {
    switch (statusCode) {
      case 500:
      case 404:
        logger.error(MessageFormat.format(RETRY_MESSAGE_PATTERN, statusCode));
        return attemptCounter >= 10;
      case 400:
      case 403:
      case 200:
      case 201:
      case 204:
        // stop retry
        return true;
      default:
        logger.error(MessageFormat.format(UNEXPECTED_CODE_MESSAGE_PATTERN, statusCode));
        return attemptCounter >= 3;
    }
  }

  @Override
  public Duration countDelayBeforeAttempt(int statusCode, int attemptCounter) {
    switch (statusCode) {
      case 500:
      case 404:
        logger.error(MessageFormat.format(RETRY_MESSAGE_PATTERN, statusCode));
        return Duration.ofSeconds(2);
      case 400:
      case 403:
      case 200:
      case 201:
      case 204:
        return Duration.ZERO;
      default:
        return Duration.ofSeconds(1);
    }
  }

  @Override
  public boolean isRequestSuccess(int statusCode) {
    return SUCCESS_RESPONSE_STATUS_CODE.contains(statusCode);
  }
}
