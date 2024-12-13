package com.example.New_bazario.controllers;

import com.example.New_bazario.entities.CartItem;
import com.example.New_bazario.services.CartItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart-items")
public class CartItemController {

    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping
    public CartItem addCartItem(
            @RequestParam Integer cartId,
            @RequestParam Integer productId,
            @RequestParam Integer quantity) {
        return cartItemService.addCartItem(cartId, productId, quantity);
    }

    @GetMapping("/{cartId}")
    public List<CartItem> getCartItemsByCartId(@PathVariable Integer cartId) {
        return cartItemService.getCartItemsByCartId(cartId);
    }
}
