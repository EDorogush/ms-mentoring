package com.home.ms.invoice.model;

import com.home.ms.invoice.InvoiceEntity;
import org.springframework.stereotype.Component;

@Component
public class InvoiceMapper {

  public Invoice fromEntity(InvoiceEntity entity) {
    Invoice invoice = new Invoice();
    invoice.setId(entity.getId());
    invoice.setUserId(entity.getUserId());
    invoice.setBill(entity.getBill());
    invoice.setStatus(entity.getStatus().ordinal());
    return invoice;
  }
}
