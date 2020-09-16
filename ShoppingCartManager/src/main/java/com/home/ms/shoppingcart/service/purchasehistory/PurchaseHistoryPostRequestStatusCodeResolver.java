package com.home.ms.shoppingcart.service.purchasehistory;

import com.home.ms.shoppingcart.service.HttpRequestResultStatusCodeResolver;
import com.home.ms.shoppingcart.service.RequestDetail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.Duration;

@Service
public class PurchaseHistoryPostRequestStatusCodeResolver
    implements HttpRequestResultStatusCodeResolver<RetryDataModel> {

  private static final Logger logger = LogManager.getLogger();
  private static final String RETRY_MESSAGE_PATTERN = "request returns code {0}";
  private static final String STOP_RETRY_MESSAGE_PATTERN = "stop retry, status code {0}";
  private static final String REQUEST_COMPLETED_MESSAGE_PATTERN =
      "request completed in attempt {0}";
  private static final String UNEXPECTED_CODE_MESSAGE_PATTERN = "unexpected coed {0} ";

  @Override
  public RetryDataModel resolveStatusCode(Integer statusCode, RequestDetail requestDetail) {
    switch (statusCode) {
      case 500:
      case 404:
        logger.error(MessageFormat.format(RETRY_MESSAGE_PATTERN, statusCode));
        // todo: duration value configure from application.yaml
        return new RetryDataModel(requestDetail.getCurrentAttempt() + 1, Duration.ofSeconds(2));
      case 400:
      case 403:
        logger.error(MessageFormat.format(STOP_RETRY_MESSAGE_PATTERN, statusCode));
        return new RetryDataModel(requestDetail.getMaxAttempt(), Duration.ZERO);
      case 200:
      case 204:
        logger.error(
            MessageFormat.format(
                REQUEST_COMPLETED_MESSAGE_PATTERN, requestDetail.getCurrentAttempt()));
        return new RetryDataModel(requestDetail.getMaxAttempt(), Duration.ZERO);
      case 0:
        // exception was logged before, just try again
        return new RetryDataModel(requestDetail.getCurrentAttempt() + 1, Duration.ofSeconds(2));
      default:
        logger.error(MessageFormat.format(UNEXPECTED_CODE_MESSAGE_PATTERN, statusCode));
        return new RetryDataModel(requestDetail.getMaxAttempt(), Duration.ZERO);
    }
  }
}
