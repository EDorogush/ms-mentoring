package com.home.ms.shoppingcart.service.invoice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class InvoiceProducer {
  private static final Logger logger = LogManager.getLogger();
  private final String queueName;
  private final ConnectionFactory factory;
  private final ObjectMapper objectMapper;

  public InvoiceProducer(
      @Value("${rabbitmq.producer-queue.name}") String queueName,
      ConnectionFactory factory,
      ObjectMapper objectMapper) {
    this.queueName = queueName;
    this.factory = factory;
    this.objectMapper = objectMapper;
  }

  public void sendInvoice(InvoiceToProduce item) {
    try (Connection connection = factory.newConnection();
        Channel channel = connection.createChannel()) {
      channel.queueDeclare(queueName, false, false, false, null);
      channel.basicPublish("", queueName, null, objectMapper.writeValueAsBytes(item));
      logger.info(" Sent invoice: {}", item.toString());
    } catch (Exception e) {

    }
  }
}
