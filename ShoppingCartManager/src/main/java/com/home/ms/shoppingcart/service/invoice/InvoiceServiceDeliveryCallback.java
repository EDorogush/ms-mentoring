package com.home.ms.shoppingcart.service.invoice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.home.ms.shoppingcart.service.ShoppingCartService;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class InvoiceServiceDeliveryCallback implements DeliverCallback {
  private final ObjectMapper objectMapper;
  private final ShoppingCartService shoppingCartService;

  public InvoiceServiceDeliveryCallback(
      ObjectMapper objectMapper, ShoppingCartService shoppingCartService) {
    this.objectMapper = objectMapper;
    this.shoppingCartService = shoppingCartService;
  }

  @Override
  public void handle(String consumerTag, Delivery message) throws IOException {
    InvoiceToConsume invoiceResult =
        objectMapper.readValue(message.getBody(), InvoiceToConsume.class);
    if (invoiceResult.isApproved()) {
      shoppingCartService.updateStateWithStatusApproved(
          invoiceResult.getUserId(), invoiceResult.getPurchaseTime());
    } else {
      shoppingCartService.updateStateWithStatusRejected(invoiceResult.getUserId());
    }
  }
}
