package com.home.ms.invoice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.home.ms.invoice.model.InvoiceToPay;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class InvoiceServiceDeliveryCallback implements DeliverCallback {
  private final ObjectMapper objectMapper;
  private final InvoiceService invoiceService;

  public InvoiceServiceDeliveryCallback(ObjectMapper objectMapper, InvoiceService invoiceService) {
    this.objectMapper = objectMapper;
    this.invoiceService = invoiceService;
  }

  @Override
  public void handle(String consumerTag, Delivery message) throws IOException {
    InvoiceToPay invoice = objectMapper.readValue(message.getBody(), InvoiceToPay.class);
    invoiceService.createInvoice(invoice);
  }
}
