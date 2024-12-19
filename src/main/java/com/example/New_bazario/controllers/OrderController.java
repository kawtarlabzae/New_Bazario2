package com.example.New_bazario.controllers;

import com.example.New_bazario.entities.CartItem;
import com.example.New_bazario.entities.Order;
import com.example.New_bazario.security.user.User;
import com.example.New_bazario.services.OrderService;
import com.example.New_bazario.services.UserService;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    public OrderController(OrderService orderService,UserService userService) {
        this.orderService = orderService;
		this.userService = userService;
    }

    // Create an order from selected cart items for the logged-in user
    @PostMapping("/create")
    public Order createOrderFromCart(
            @RequestBody List<Integer> selectedCartItemIds,
            HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            throw new RuntimeException("User not authenticated");
        }
        User user= userService.getUserById(userId);
        return orderService.createOrderForUser(user, selectedCartItemIds);
    }

    // Fetch all orders for the logged-in user
    @GetMapping
    public List<Order> getOrdersForUser(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            throw new RuntimeException("User not authenticated");
        }
        User user= userService.getUserById(userId);
        return orderService.getOrdersByUser(user);
    }

    // Fetch a specific order by ID for the logged-in user
    @GetMapping("/{orderId}")
    public Order getOrderById(
            @PathVariable Integer orderId,
            HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            throw new RuntimeException("User not authenticated");
        }
        User user= userService.getUserById(userId);
        return orderService.getOrderByIdForUser(user, orderId);
    }

    // Remove an item from an existing order for the logged-in user
    @DeleteMapping("/{orderId}/items/{cartItemId}")
    public Order removeItemFromOrder(
            @PathVariable Integer orderId,
            @PathVariable Integer cartItemId,
            HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            throw new RuntimeException("User not authenticated");
        }

        return orderService.removeItemFromOrder(orderId, cartItemId, userId);
    }
    @GetMapping("/{orderId}/items")
    public List<CartItem> getCartItemsByOrderId(
            @PathVariable Integer orderId,
            HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            throw new RuntimeException("User not authenticated");
        }
        User user= userService.getUserById(userId);
        return orderService.getCartItemsByOrderIdForUser(user, orderId);
    }
    @PostMapping("/create-from-cart")
    public String createOrderFromCartAndRedirect(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            throw new RuntimeException("User not authenticated");
        }
        User user = userService.getUserById(userId);

        Order newOrder = orderService.createOrderFromCart(user);
        if (newOrder == null || newOrder.getOrderItems() == null) {
            throw new RuntimeException("Order creation failed or no items in the order.");
        }

        BigDecimal totalAmount = newOrder.getOrderItems().stream()
                .filter(cartItem -> cartItem != null && cartItem.getProduct() != null)
                .map(cartItem -> cartItem.getProduct().getPrice()
                        .multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("order", newOrder);
        model.addAttribute("cartItems", new ArrayList<>(newOrder.getOrderItems())); // Convert to List if needed
        model.addAttribute("totalAmount", totalAmount);

        return "order";
    }
}
