package com.example.New_bazario.controllers;

import com.example.New_bazario.entities.CartItem;
import com.example.New_bazario.services.CartItemService;

import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cart-items")
public class CartItemController {

    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping
    public CartItem addCartItem(
            @RequestParam Integer productId,
            @RequestParam Integer quantity,
            HttpSession session) {
        
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            throw new RuntimeException("User not authenticated");
        }

        return cartItemService.addCartItem(userId, productId, quantity);
    }

    @GetMapping("/{cartId}")
    public List<Map<String, Object>> getCartItemsByCartId(@PathVariable Integer cartId) {
        return cartItemService.getCartItemsByCartId(cartId).stream()
                .map(item -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("cartItemId", item.getCartItemId());
                    map.put("productId", item.getProduct().getProductId());
                    map.put("productName", item.getProduct().getName());
                    map.put("quantity", item.getQuantity());
                    map.put("price", item.getProduct().getPrice());
                    return map;
                })
                .collect(Collectors.toList());
    }
}
