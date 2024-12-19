package com.example.New_bazario.controllers;

import com.example.New_bazario.entities.Cart;
import com.example.New_bazario.entities.CartItem;
import com.example.New_bazario.services.CartItemService;
import com.example.New_bazario.services.CartService;

import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cart-items")
public class CartItemController {

    private final CartItemService cartItemService;
    private final CartService cartService;
    public CartItemController(CartItemService cartItemService,CartService cartService) {
        this.cartItemService = cartItemService;
		this.cartService = cartService;
    }

    @GetMapping
    public String getCartItems(HttpSession session, Model model) {
        // Retrieve the user's cart ID from the session
        Integer userId = (Integer) session.getAttribute("userId");
        Integer cartId = (Integer) session.getAttribute("cartId");

        if (userId == null || cartId == null) {
            model.addAttribute("error", "No cart found for the current user.");
            return "cart";
        }

        // Fetch cart items for the user's cart
        List<CartItem> cartItems = cartItemService.getCartItemsByCartId(cartId);
        model.addAttribute("cartItems", cartItems);

        // Calculate the total cost of the items
        double total = cartItems.stream()
                .mapToDouble(item -> item.getQuantity() * item.getProduct().getPrice().doubleValue())
                .sum();
        model.addAttribute("total", total);

        return "cart";
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
