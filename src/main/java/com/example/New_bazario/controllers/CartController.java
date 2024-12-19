package com.example.New_bazario.controllers;

import com.example.New_bazario.entities.Cart;
import com.example.New_bazario.entities.CartItem;
import com.example.New_bazario.services.CartService;
import com.example.New_bazario.services.CartItemService;

import jakarta.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final CartItemService cartItemService;

    public CartController(CartService cartService, CartItemService cartItemService) {
        this.cartService = cartService;
        this.cartItemService = cartItemService;
    }

    @GetMapping
    public String getCartDetails(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "Error"; // Redirect to an error page
        }

        Cart cart = cartService.getOrCreateCart(userId);
        Integer cartId = cart.getCartId();

        List<CartItem> cartItems = cartItemService.getCartItemsByCartId(cartId);

        model.addAttribute("cartItems", cartItems);
        return "cart-details"; // Name of the HTML template (cart-details.html)
    }
}
