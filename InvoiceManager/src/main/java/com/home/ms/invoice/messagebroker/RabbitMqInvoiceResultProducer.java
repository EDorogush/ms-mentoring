package com.home.ms.invoice.messagebroker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.home.ms.invoice.MessageBrokerMessageProducer;
import com.home.ms.invoice.exception.SendMessageException;
import com.home.ms.invoice.model.InvoiceResult;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
class RabbitMqInvoiceResultProducer implements MessageBrokerMessageProducer<InvoiceResult> {
  private static final Logger logger = LogManager.getLogger();
  private final String queueName;
  private final ConnectionFactory factory;
  private final ObjectMapper objectMapper;

  public RabbitMqInvoiceResultProducer(
      @Value("${rabbitmq.producer-queue.name}") String queueName,
      ConnectionFactory factory,
      ObjectMapper objectMapper) {
    this.queueName = queueName;
    this.factory = factory;
    this.objectMapper = objectMapper;
  }

  @Override
  public void sendMessage(InvoiceResult dataToSend) {
    try (Connection connection = factory.newConnection();
        Channel channel = connection.createChannel()) {
      channel.queueDeclare(queueName, false, false, false, null);
      channel.basicPublish("", queueName, null, objectMapper.writeValueAsBytes(dataToSend));
      logger.info(" Sent invoiceResult: {}", dataToSend.toString());
    } catch (Exception e) {
      throw new SendMessageException(e);
    }
  }
}
