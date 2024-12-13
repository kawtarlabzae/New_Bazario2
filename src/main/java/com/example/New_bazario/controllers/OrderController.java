package com.example.New_bazario.controllers;

import com.example.New_bazario.entities.Order;
import com.example.New_bazario.services.OrderService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public Order createOrder(
            @RequestParam Integer userId,
            @RequestParam Integer cartId,
            @RequestParam BigDecimal totalPrice) {
        return orderService.createOrder(userId, cartId, totalPrice);
    }

    @GetMapping("/user/{userId}")
    public List<Order> getOrdersByUserId(@PathVariable Integer userId) {
        return orderService.getOrdersByUserId(userId);
    }
}
