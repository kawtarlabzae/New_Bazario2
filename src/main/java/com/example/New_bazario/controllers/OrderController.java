package com.example.New_bazario.controllers;

import com.example.New_bazario.entities.Order;
import com.example.New_bazario.entities.Payment;
import com.example.New_bazario.services.OrderService;
import com.example.New_bazario.services.PaymentService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final PaymentService paymentService;

    public OrderController(OrderService orderService, PaymentService paymentService) {
        this.orderService = orderService;
        this.paymentService = paymentService;
    }

    @PostMapping("/{orderId}/payment")
    public Payment initiatePayment(
            @PathVariable Integer orderId,
            @RequestBody Map<String, String> payload) {
        String paymentMethod = payload.get("paymentMethod");
        String transactionId = payload.get("transactionId");
        String paymentStatus = payload.get("paymentStatus");

        if (paymentMethod == null || transactionId == null || paymentStatus == null) {
            throw new IllegalArgumentException("Payment method, status, and transaction ID are required.");
        }

        return paymentService.createPayment(orderId, paymentMethod, paymentStatus, transactionId);
    }
}
