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
class RabbitMqInvoiceConsumer {
  private static final Logger logger = LogManager.getLogger();
  private final String queueName;
  private final ConnectionFactory connectionFactory;
  private final DeliverCallback deliverCallback;
  private Connection connection;
  private Channel channel;

  RabbitMqInvoiceConsumer(
      @Value("${rabbitmq.consumer-queue.name}") String queueName,
      ConnectionFactory connectionFactory,
      DeliverCallback deliverCallback) {
    this.queueName = queueName;
    this.connectionFactory = connectionFactory;
    this.deliverCallback = deliverCallback;
  }

  @PostConstruct
  void init() {
    logger.info("starting invoice Consumer");
    final int maxAttempt = 5;
    int currentAttempt = 0;
    while (currentAttempt < maxAttempt) {
      try {
        connection = connectionFactory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(queueName, false, false, false, null);
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});
      } catch (IOException e) {
        logger.error(e);
        throw new RuntimeException(e);
      } catch (TimeoutException e) {
        logger.error(e);
        currentAttempt++;
        if (currentAttempt == maxAttempt) {
          throw new RuntimeException(e);
        }
        try {
          Thread.sleep(TimeUnit.SECONDS.toMillis(1));
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
