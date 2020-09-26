package com.home.ms.product.purchasehistory.usergamehistory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.home.ms.product.SendRequestException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
public class UserGameHistoryPostRequestHandler
    implements RequestHandler<Integer, UserGameItemToPost> {
  private static final Logger logger = LogManager.getLogger();
  private final HttpClient httpClient;
  private final String baseUri;
  private final ObjectMapper objectMapper;

  public UserGameHistoryPostRequestHandler(
      HttpClient httpClient, @Value("${game-app.url}") String baseUrl, ObjectMapper objectMapper) {
    this.httpClient = httpClient;
    this.baseUri = baseUrl;
    this.objectMapper = objectMapper;
  }

  @Override
  public Integer performRequest(HttpRequest request, int timeout) {

    CompletableFuture<Integer> future =
        httpClient
            .sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .orTimeout(timeout, TimeUnit.SECONDS)
            .thenApply(HttpResponse::statusCode)
            .exceptionally(this::handleExceptions);
    try {
      return future.join();
    } catch (CompletionException ex) {
      try {
        throw ex.getCause();
      } catch (Error | RuntimeException possible) {
        throw possible;
      } catch (Throwable impossible) {
        throw new AssertionError(impossible);
      }
    }
  }

  @Override
  public HttpRequest prepareRequest(UserGameItemToPost dataToPost) {

    URI uri = URI.create(baseUri + dataToPost.getGameId());
    return HttpRequest.newBuilder(uri)
        .header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(convertDataToJson(dataToPost)))
        .build();
  }

  @Override
  public String getRequestURI() {
    return baseUri;
  }

  private String convertDataToJson(UserGameItemToPost postData) {
    try {
      return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(postData);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException(e);
    }
  }

  private int handleExceptions(Throwable ex) {
    if (ex instanceof TimeoutException) {
      logger.error("request time exceeded, ", ex);
    } else {
      logger.error("error during connection:", ex);
    }
    throw new SendRequestException(ex);
  }
}
