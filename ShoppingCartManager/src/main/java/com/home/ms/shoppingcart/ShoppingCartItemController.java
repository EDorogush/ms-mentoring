package com.home.ms.shoppingcart;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/shoppingCartItems")
public class ShoppingCartItemController {
    private final ShoppingCartService shoppingCartService;

    public ShoppingCartItemController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping
    public List<ShoppingCartItem> getItems() {
        final String userId = getUserId();
        return shoppingCartService.getItems(userId);
    }

//    @GetMapping("/id")
//    public ShoppingCartItem getItemById(@PathVariable String id) {
//        return new ShoppingCartItem();
//    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ShoppingCartItem> create(@RequestBody ShoppingCartItem shoppingCartItem) {
        final String userId = getUserId();
        ShoppingCartItem item = shoppingCartService.addItem(userId, shoppingCartItem);
        return ResponseEntity.created(buildLocation(shoppingCartItem.getId())).body(shoppingCartItem);
    }

    @DeleteMapping("/id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String itemId) {

        //check rights
        shoppingCartService.removeItemById(itemId);
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
