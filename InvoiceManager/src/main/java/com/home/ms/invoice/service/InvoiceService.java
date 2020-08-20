package com.home.ms.invoice.service;

import com.home.ms.invoice.repository.InvoiceEntity;
import com.home.ms.invoice.repository.InvoiceRepository;
import com.home.ms.invoice.web.Invoice;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceService {
    private final InvoiceRepository repository;
    private final IdGenerator idGenerator;
    private final InvoiceMapper mapper;

    public InvoiceService(InvoiceRepository repository, IdGenerator idGenerator, InvoiceMapper mapper) {
        this.repository = repository;
        this.idGenerator = idGenerator;
        this.mapper = mapper;
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

    public String createInvoice(@RequestBody Invoice invoice) {
        InvoiceEntity entity = mapper.toEntity(invoice);
        entity.setId(idGenerator.generateRandomUUID());
        entity.setLastUpdate(Instant.now());
        repository.save(entity);
        return entity.getId();
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }


}
