package com.example.New_bazario.controllers;

import com.example.New_bazario.entities.Payment;
import com.example.New_bazario.services.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public Payment createPayment(
            @RequestParam Integer orderId,
            @RequestParam String paymentMethod,
            @RequestParam String paymentStatus,
            @RequestParam String transactionId) {
        return paymentService.createPayment(orderId, paymentMethod, paymentStatus, transactionId);
    }

  
}
