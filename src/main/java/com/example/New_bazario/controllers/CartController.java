package com.example.New_bazario.controllers;

import com.example.New_bazario.entities.Cart;
import com.example.New_bazario.entities.CartItem;
import com.example.New_bazario.entities.Order;
import com.example.New_bazario.services.CartService;
import com.example.New_bazario.services.OrderService;
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
    private final OrderService orderService;
    public CartController(OrderService orderService,CartService cartService, CartItemService cartItemService) {
        this.cartService = cartService;
        this.cartItemService = cartItemService;
        this.orderService=orderService;
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

        // Calculate the price for each item and the total amount
        final BigDecimal[] totalAmount = {BigDecimal.ZERO};

        List<Map<String, Object>> itemsWithPrice = cartItems.stream().map(item -> {
            Map<String, Object> itemDetails = new HashMap<>();
            itemDetails.put("cartItemId", item.getCartItemId()); // Include cartItemId
            itemDetails.put("itemName", item.getProduct().getName());
            itemDetails.put("productDescription", item.getProduct().getDescription());
            itemDetails.put("quantity", item.getQuantity());
            itemDetails.put("pricePerUnit", item.getProduct().getPrice()); // Assuming this exists
            BigDecimal itemTotal = item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            itemDetails.put("totalPrice", itemTotal);

            // Add to total amount
            totalAmount[0] = totalAmount[0].add(itemTotal);

            return itemDetails;
        }).collect(Collectors.toList());

        model.addAttribute("cartItems", itemsWithPrice);
        model.addAttribute("totalAmount", totalAmount[0]);

        return "cart-details"; // Name of the HTML template (cart-details.html)
    }

    @PostMapping("/cart-items/delete")
    public String deleteCartItem(@RequestParam("cartItemId") Integer cartItemId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        System.out.println("we want to remove " + cartItemId);
        if (userId == null) {
            return "Error"; // Redirect to an error page
        }
        cartItemService.deleteCartItem(cartItemId);
        return "redirect:/cart"; // Refresh the cart page
    }
   


}
