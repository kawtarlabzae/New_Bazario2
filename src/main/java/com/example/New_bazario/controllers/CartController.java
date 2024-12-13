package com.example.New_bazario.controllers;

import com.example.New_bazario.entities.Cart;
import com.example.New_bazario.services.CartService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/{userId}")
    public Cart createCart(@PathVariable Integer userId) {
        return cartService.createCart(userId);
    }

    @GetMapping("/user/{userId}")
    public Cart getCartByUserId(@PathVariable Integer userId) {
        return cartService.getCartByUserId(userId);
    }
}
