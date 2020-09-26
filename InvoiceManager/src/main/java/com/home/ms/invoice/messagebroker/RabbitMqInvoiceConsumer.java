package com.home.ms.invoice.messagebroker;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component
/**
 * class is used as message broker queue listener. queue name to listen is configured by {@code
 * rabbitmq.consumer-queue.name}
 */
class RabbitMqInvoiceConsumer {
  private static final Logger logger = LogManager.getLogger();
  private final String queueName;
  private final int maxAttempt;
  private final int awaitTimeSeconds;
  private final ConnectionFactory connectionFactory;
  private final DeliverCallback deliverCallback;
  private Connection connection;
  private Channel channel;

  RabbitMqInvoiceConsumer(
      @Value("${rabbitmq.consumer-queue.name}") String queueName,
      @Value("${rabbitmq.consumer-queue.attempt-to-connect}") int maxAttempt,
      @Value("${rabbitmq.consumer-queue.duration-between-connect-attempt-seconds}") int awaitTime,
      ConnectionFactory connectionFactory,
      DeliverCallback deliverCallback) {
    this.queueName = queueName;
    this.maxAttempt = maxAttempt;
    this.awaitTimeSeconds = awaitTime;
    this.connectionFactory = connectionFactory;
    this.deliverCallback = deliverCallback;
  }

  @PostConstruct
  void init() {
    logger.info("starting invoice Consumer");
    int currentAttempt = 0;
    while (currentAttempt < maxAttempt) {
      try {
        connection = connectionFactory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(queueName, false, false, false, null);
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});
      } catch (IOException e) {
        // errors should be logged here as they are not processed by ExceptionHandler
        logger.error(e);
        throw new RuntimeException(e);
      } catch (TimeoutException e) {
        logger.error(e);
        currentAttempt++;
        if (currentAttempt == maxAttempt) {
          throw new RuntimeException(e);
        }
        try {
          Thread.sleep(TimeUnit.SECONDS.toMillis(awaitTimeSeconds));
        } catch (InterruptedException interruptedException) {
          Thread.currentThread().interrupt();
        }
      }
      currentAttempt = maxAttempt;
    }
  }

  @PreDestroy
  void destroy() {
    logger.info("closing invoice Consumer");
    try {
      channel.close();
      connection.close();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
