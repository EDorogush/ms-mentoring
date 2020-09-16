package com.home.ms.invoice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<InvoiceEntity, String> {

  List<InvoiceEntity> findByUserId(String userId);

  @Query(
      value = "SELECT * FROM invoices u WHERE u.last_update > :from and u.last_update < :to",
      nativeQuery = true)
  List<InvoiceEntity> findByLastUpdateBetween(@Param("from") Instant from, @Param("to") Instant to);

  Optional<InvoiceEntity> findByStatus(InvoiceEntityStatus status);

  @Modifying
  @Query(
      value = "UPDATE invoices u SET status = :to WHERE u.id = :id AND u.status = :from",
      nativeQuery = true)
  int updateStatus(@Param("id") String id, @Param("from") String from, @Param("to") String to);
}
