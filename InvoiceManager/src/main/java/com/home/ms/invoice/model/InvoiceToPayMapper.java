package com.home.ms.invoice.model;

import com.home.ms.invoice.InvoiceEntity;
import com.home.ms.invoice.model.InvoiceToPay;

public class InvoiceToPayMapper {
  public InvoiceEntity toEntity(InvoiceToPay invoice) {
    InvoiceEntity invoiceEntity = new InvoiceEntity();
    invoiceEntity.setUserId(invoice.getUserId());
    invoiceEntity.setBill(invoice.getBill());
    return invoiceEntity;
  }
}
