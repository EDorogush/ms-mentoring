package com.home.ms.invoice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<InvoiceEntity, String> {
    
    List<InvoiceEntity> findByUserId(String userId);

}
