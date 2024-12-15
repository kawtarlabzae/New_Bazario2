package com.example.New_bazario.controllers;

import com.example.New_bazario.entities.Cart;
import com.example.New_bazario.entities.CartItem;
import com.example.New_bazario.services.CartItemService;
import com.example.New_bazario.services.CartService;

import jakarta.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/cart-items")
public class CartItemController {
    private static final Logger logger = LoggerFactory.getLogger(CartItemController.class);

    private final CartItemService cartItemService;
    private final CartService cartService;

    public CartItemController(CartItemService cartItemService, CartService cartService) {
        this.cartItemService = cartItemService;
        this.cartService = cartService;
    }

    @PostMapping
    public String addCartItem(
            @RequestParam Integer productId,
            @RequestParam Integer quantity,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        try {
            // Get user ID from session
            Integer userId = (Integer) session.getAttribute("userId");
            logger.info("User ID from session: {}", userId);

            if (userId == null) {
                redirectAttributes.addFlashAttribute("error", "Please login to add items to cart");
                return "redirect:/login";
            }

            // Get or create cart for user
            Cart cart = cartService.getCartByUserId(userId);
            if (cart == null) {
                logger.info("No cart found for user ID: {}. Creating a new cart.", userId);
                cart = cartService.createCart(userId);
            }

            logger.info("Cart ID: {}", cart.getCartId());
            logger.info("Product ID: {}, Quantity: {}", productId, quantity);

            // Add item to cart
            cartItemService.addCartItem(cart.getCartId(), productId, quantity);
            logger.info("Successfully added item to cart");

            return "redirect:/cart-items/" + cart.getCartId();
        } catch (Exception e) {
            logger.error("Error adding item to cart", e);
            redirectAttributes.addFlashAttribute("error", "Could not add item to cart: " + e.getMessage());
            return "redirect:/products/" + productId;
        }
    }

    @GetMapping("/{cartId}")
    public String getCartItemsByCartId(@PathVariable Integer cartId, Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        try {
            List<CartItem> cartItems = cartItemService.getCartItemsByCartId(cartId);
            model.addAttribute("cartItems", cartItems);

            double total = cartItems.stream()
                    .mapToDouble(item -> item.getQuantity().doubleValue() * item.getProduct().getPrice().doubleValue())
                    .sum();
            model.addAttribute("total", total);

            return "cart";
        } catch (Exception e) {
            logger.error("Error retrieving cart items", e);
            model.addAttribute("error", "Could not retrieve cart items");
            return "redirect:/products";
        }
    }
}
