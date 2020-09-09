package com.home.ms.shoppingcart.service;

import com.home.ms.shoppingcart.repository.ShoppingCartEntity;
import com.home.ms.shoppingcart.repository.ShoppingCartEntityElement;
import com.home.ms.shoppingcart.repository.ShoppingCartRepository;
import com.home.ms.shoppingcart.repository.ShoppingCartStatus;
import com.home.ms.shoppingcart.service.invoice.InvoiceProducer;
import com.home.ms.shoppingcart.service.invoice.InvoiceToProduce;
import com.home.ms.shoppingcart.service.purchasehistory.PurchaseHistoryRequestProducer;
import com.home.ms.shoppingcart.service.purchasehistory.RequestFailedException;
import com.home.ms.shoppingcart.web.ShoppingCart;
import com.home.ms.shoppingcart.web.ShoppingCartElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;

@Service
public class ShoppingCartService {
  private static final Logger logger = LogManager.getLogger();
  private final ShoppingCartRepository repository;
  private final ShoppingCartMapper mapper;
  private final IdGenerator idGenerator;
  private final InvoiceProducer invoiceProducer;
  private final PurchaseHistoryRequestProducer purchaseHistoryProducer;

  public ShoppingCartService(
      ShoppingCartRepository repository,
      ShoppingCartMapper mapper,
      IdGenerator idGenerator,
      InvoiceProducer invoiceProducer,
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
    // todo: check if elements are not empty
    entity.setStatus(ShoppingCartStatus.INVOICE);
    repository.saveAndFlush(entity);

    BigDecimal invoice =
        entity.getElements().stream()
            .map(ShoppingCartEntityElement::getPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    InvoiceToProduce invoiceItem = new InvoiceToProduce(userId, invoice);
    invoiceProducer.sendInvoice(invoiceItem);
  }

  public void updateStateWithStatusApproved(String userId) {
    ShoppingCartEntity entity = repository.findById(userId).orElseThrow();
    entity.setStatus(ShoppingCartStatus.APPROVED);
    repository.saveAndFlush(entity);
    for (ShoppingCartEntityElement element : entity.getElements()) {
      try {
        purchaseHistoryProducer.sendPostOne(userId, element.getGameId(), element.getPrice());
        removeItemFromList(userId, element.getGameId());
      } catch (RequestFailedException e) {
        // todo: need details. RequestFailedException e should contain reason to use in circuit
        // breaker
        logger.error("failed to send data");
        entity.setStatus(ShoppingCartStatus.STUCK);
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
}
