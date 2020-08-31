package com.home.ms.invoice;

import org.springframework.stereotype.Component;

@Component
public class InvoiceMapper {

    public Invoice fromEntity(InvoiceEntity entity) {
        Invoice invoice = new Invoice();
        invoice.setId(entity.getId());
        invoice.setUserId(entity.getUserId());
        invoice.setBill(entity.getBill());
        invoice.setStatus(entity.getStatus());
        return invoice;
    }

    public InvoiceEntity toEntity(Invoice invoice) {
        InvoiceEntity invoiceEntity = new InvoiceEntity();
        invoiceEntity.setId(invoice.getId());
        invoiceEntity.setUserId(invoice.getUserId());
        invoiceEntity.setBill(invoice.getBill());
        invoiceEntity.setStatus(invoice.getStatus());
        return invoiceEntity;

    }

}
