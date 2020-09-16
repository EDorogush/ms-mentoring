package com.home.ms.invoice;

import com.home.ms.invoice.model.Invoice;
import com.home.ms.invoice.model.InvoiceMapper;
import com.home.ms.invoice.model.InvoiceToPay;
import com.home.ms.invoice.model.InvoiceToPayMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceService {
  private static final Logger logger = LogManager.getLogger();
  private final InvoiceRepository repository;
  private final IdGenerator idGenerator;
  private final InvoiceMapper mapper;
  private final InvoiceToPayMapper invoiceToPayMapper;

  public InvoiceService(
      InvoiceRepository repository,
      IdGenerator idGenerator,
      InvoiceMapper mapper,
      InvoiceToPayMapper invoiceToPayMapper) {
    this.repository = repository;
    this.idGenerator = idGenerator;
    this.mapper = mapper;
    this.invoiceToPayMapper = invoiceToPayMapper;
  }

  public List<Invoice> searchInvoicesByUserId(String userId) {
    List<InvoiceEntity> invoices = repository.findByUserId(userId);
    return invoices.stream().map(mapper::fromEntity).collect(Collectors.toList());
  }

  public List<Invoice> searchInvoicesByTime(Instant searchFrom, Instant searchTo) {
    List<InvoiceEntity> invoices = repository.findByLastUpdateBetween(searchFrom, searchTo);
    return invoices.stream().map(mapper::fromEntity).collect(Collectors.toList());
  }

  public Invoice searchInvoiceById(String id) {
    InvoiceEntity entity = repository.findById(id).orElse(new InvoiceEntity());
    return mapper.fromEntity(entity);
  }

  public String createInvoice(InvoiceToPay invoice) {
    InvoiceEntity entity = invoiceToPayMapper.toEntity(invoice);
    entity.setId(idGenerator.generateRandomUUID());
    entity.setLastUpdate(Instant.now());
    repository.saveAndFlush(entity);
    return entity.getId();
  }

  public void deleteById(String id) {
    repository.deleteById(id);
  }
}
