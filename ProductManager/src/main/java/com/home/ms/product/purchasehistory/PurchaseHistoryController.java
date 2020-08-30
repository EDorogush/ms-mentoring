package com.home.ms.product.purchasehistory;

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/purchase-history")
public class PurchaseHistoryController {
  private final PurchaseHistoryService service;

  public PurchaseHistoryController(PurchaseHistoryService service) {
    this.service = service;
  }

  @RequestMapping
  public List<PurchaseHistoryItem> getPurchaseHistory(
      @RequestParam @Nullable String userIdFromParam) {
    final String userId = getUserId(userIdFromParam);
    return service.getUserItems(userId);
  }

  @PostMapping
  public PurchaseHistoryItem addToPurchased(@RequestBody PurchaseHistoryItem item) {
    final String userId = getUserId(null);
    return service.addItem(item, userId);
  }

  @RequestMapping("/games")
  public List<String> getPurchasedGameIds(@RequestParam String userIdFromParam) {
    final String userId = getUserId(userIdFromParam);
    return service.getUserGameIds(userId);
  }

  @DeleteMapping("/games/{gameId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteItem(
      @PathVariable String gameId, @RequestParam @Nullable String userIdFromParam) {
    final String userId = getUserId(userIdFromParam);
    service.deleteGameFromHistory(userId, gameId);
  }

  private String getUserId(String userIdFromParam) {
    // request to authorisation service to retrieve userId and rights
    return Objects.requireNonNullElse(userIdFromParam, "abc");
  }
}
