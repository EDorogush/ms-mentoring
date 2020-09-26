package com.home.ms.invoice;

import com.home.ms.invoice.exception.SendMessageException;
import com.home.ms.invoice.model.InvoiceResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Random;

/**
 * this is invoice processor imitator. it search db for records with {@link InvoiceEntityStatus
 * WAIT} status process invoice, update status and send result to other needed services
 */
@Service
public class InvoiceProcessor {
  private static final Logger logger = LogManager.getLogger();
  private static final List<InvoiceEntityStatus> STATUSES =
      List.of(InvoiceEntityStatus.OK, InvoiceEntityStatus.REJECT);
  private static final int SIZE = STATUSES.size();
  private static final Random RANDOM = new Random();
  private final MessageBrokerMessageProducer<InvoiceResult> messageProducer;
  private final InvoiceRepository repository;

  public InvoiceProcessor(
      MessageBrokerMessageProducer<InvoiceResult> messageProducer, InvoiceRepository repository) {
    this.messageProducer = messageProducer;
    this.repository = repository;
  }

  @Scheduled(fixedDelay = 1000)
  public void searchAndProcess() {
    repository.findByStatus(InvoiceEntityStatus.WAIT).ifPresent(this::processInvoice);
  }

  private void processInvoice(InvoiceEntity entityToProcess) {

    final int rowsUpdated =
        repository.updateStatus(
            entityToProcess.getId(),
            InvoiceEntityStatus.WAIT.name(),
            InvoiceEntityStatus.PROCESS.name());
    if (rowsUpdated == 0) {
      // means that somebody else updated this entity. Than do nothing until next attempt
      return;
    }
    entityToProcess.setStatus(STATUSES.get(RANDOM.nextInt(SIZE)));
    entityToProcess.setLastUpdate(Instant.now());
    repository.saveAndFlush(entityToProcess);
    final boolean invoicePaid = entityToProcess.getStatus() == InvoiceEntityStatus.OK;
    try {
      messageProducer.sendMessage(
          new InvoiceResult(
              entityToProcess.getId(),
              entityToProcess.getUserId(),
              invoicePaid,
              entityToProcess.getLastUpdate()));
    } catch (SendMessageException e) {
      logger.warn(e);
    }
  }
}
