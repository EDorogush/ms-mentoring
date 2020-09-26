package com.home.ms.shoppingcart.service;

import com.home.ms.shoppingcart.repository.ShoppingCartEntity;
import com.home.ms.shoppingcart.repository.ShoppingCartEntityElement;
import com.home.ms.shoppingcart.repository.ShoppingCartRepository;
import com.home.ms.shoppingcart.repository.ShoppingCartStatus;
import com.home.ms.shoppingcart.service.exception.NotFoundException;
import com.home.ms.shoppingcart.service.exception.RequestFailedException;
import com.home.ms.shoppingcart.service.exception.SendRequestException;
import com.home.ms.shoppingcart.service.invoice.InvoiceToSend;
import com.home.ms.shoppingcart.service.invoice.MessageBrokerMessageProducer;
import com.home.ms.shoppingcart.service.purchasehistory.PurchaseHistoryRequestProducer;
import com.home.ms.shoppingcart.web.ShoppingCart;
import com.home.ms.shoppingcart.web.ShoppingCartElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;

@Service
public class ShoppingCartService {
  private static final Logger logger = LogManager.getLogger();
  private final ShoppingCartRepository repository;
  private final ShoppingCartMapper mapper;
  private final IdGenerator idGenerator;
  private final MessageBrokerMessageProducer<InvoiceToSend> invoiceProducer;
  private final PurchaseHistoryRequestProducer purchaseHistoryProducer;

  public ShoppingCartService(
      ShoppingCartRepository repository,
      ShoppingCartMapper mapper,
      IdGenerator idGenerator,
      MessageBrokerMessageProducer<InvoiceToSend> invoiceProducer,
      PurchaseHistoryRequestProducer purchaseHistoryProducer) {
    this.repository = repository;
    this.mapper = mapper;
    this.idGenerator = idGenerator;
    this.invoiceProducer = invoiceProducer;
    this.purchaseHistoryProducer = purchaseHistoryProducer;
  }

  public ShoppingCart findShoppingCart(String userId) {
    ShoppingCartEntity entity =
        repository
            .findById(userId)
            .orElseGet(
                () -> {
                  ShoppingCartEntity entityToCreate = new ShoppingCartEntity();
                  entityToCreate.setUserId(userId);
                  entityToCreate.setStatus(ShoppingCartStatus.OPEN);
                  entityToCreate.setElements(new ArrayList<>());
                  return entityToCreate;
                });
    ;
    return mapper.fromEntity(entity);
  }

  /**
   * method adds element to user's shopping cart, and provide shopping cart lazy creation if it
   * doesn't exist
   *
   * @param userId
   * @param itemElement
   */
  @Transactional
  public void addItem(String userId, ShoppingCartElement itemElement) {
    ShoppingCartEntity entity =
        repository
            .findById(userId)
            .orElseGet(
                () -> {
                  ShoppingCartEntity entityToCreate = new ShoppingCartEntity();
                  entityToCreate.setUserId(userId);
                  entityToCreate.setStatus(ShoppingCartStatus.OPEN);
                  return entityToCreate;
                });
    ShoppingCartEntityElement element = mapper.toEntityElement(itemElement);
    element.setId(idGenerator.generateRandomUUID());
    element.setUserId(userId);
    entity.addElement(element);
    repository.saveAndFlush(entity);
  }

  public void removeItemFromList(String userId, String itemId) {
    ShoppingCartEntity entity = repository.findById(userId).orElseThrow();
    entity.getElements().removeIf(element -> element.getGameId().equals(itemId));
    repository.saveAndFlush(entity);
  }

  public void updateStateWithStatusInvoice(String userId) {
    ShoppingCartEntity entity = repository.findById(userId).orElseThrow();
    entity.setStatus(ShoppingCartStatus.INVOICE);
    repository.saveAndFlush(entity);

    BigDecimal invoice =
        entity.getElements().stream()
            .map(ShoppingCartEntityElement::getPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    InvoiceToSend invoiceItem = new InvoiceToSend(userId, invoice);
    try {
      invoiceProducer.sendMessage(invoiceItem);
    } catch (SendRequestException e) {
      logger.error(e);
    }
  }

  public void updateStateWithStatusApproved(String userId, Instant purchaseTime) {
    ShoppingCartEntity entity0 = repository.findById(userId).orElseThrow();
    entity0.setStatus(ShoppingCartStatus.APPROVED);
    repository.saveAndFlush(entity0);
    ShoppingCartEntity entity = repository.findById(userId).orElseThrow();
    for (ShoppingCartEntityElement element : entity.getElements()) {
      try {
        purchaseHistoryProducer.sendPostOne(
            userId, element.getGameId(), element.getPrice(), purchaseTime);
        removeItemFromList(userId, element.getGameId());
      } catch (RequestFailedException e) {
        updateStateWithStatusStuck(e, userId, element, entity);
      }
    }
  }

  public void updateStateWithStatusRejected(String userId) {
    ShoppingCartEntity entity =
        repository
            .findById(userId)
            .orElseThrow(() -> new NotFoundException("shopping cart", userId));
    entity.setStatus(ShoppingCartStatus.REJECTED);
    repository.saveAndFlush(entity);
    // leave as it is, do not delete

  }

  private void updateStateWithStatusStuck(
      RequestFailedException e,
      String userId,
      ShoppingCartEntityElement element,
      ShoppingCartEntity entity) {
    if (e.getResponseStatusCode() != null) {
      logger.error(
          "sending data to {} for userId={}, cartElement = {} price = {} failed with codee = {}",
          purchaseHistoryProducer.getRequestURI(),
          userId,
          element.getGameId(),
          element.getPrice(),
          e.getResponseStatusCode());
    } else {
      logger.error(
          "sending data to {} for userId={}, cartElement = {} price = {} failed ",
          purchaseHistoryProducer.getRequestURI(),
          userId,
          element.getGameId(),
          element.getPrice(),
          e);
    }
    entity.setStatus(ShoppingCartStatus.STUCK);
    repository.saveAndFlush(entity);
  }
}
