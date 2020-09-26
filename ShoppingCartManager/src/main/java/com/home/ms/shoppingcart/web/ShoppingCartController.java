package com.home.ms.shoppingcart.web;

import com.home.ms.shoppingcart.service.ShoppingCartService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
  private final ShoppingCartService shoppingCartService;

  public ShoppingCartController(ShoppingCartService shoppingCartService) {
    this.shoppingCartService = shoppingCartService;
  }

  @GetMapping
  public ShoppingCart findShoppingCart() {
    final String userId = getUserId();
    return shoppingCartService.findShoppingCart(userId);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @RequestMapping("/items")
  public void create(@RequestBody ShoppingCartElement element) {
    final String userId = getUserId();
    shoppingCartService.addItem(userId, element);
  }

  @DeleteMapping()
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@RequestParam String gameId) {
    final String userId = getUserId();
    shoppingCartService.removeItemFromList(userId, gameId);
  }

  @PatchMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateStatus(@RequestParam String status) {
    final String userId = getUserId();
    shoppingCartService.updateStateWithStatusInvoice(userId);
  }

  private String getUserId() {
    return "user_id-9";
  }

  private URI buildLocation(String id) {
    return ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(id)
        .toUri();
  }
}
