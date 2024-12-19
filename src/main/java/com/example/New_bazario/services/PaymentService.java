package com.example.New_bazario.services;

import com.example.New_bazario.entities.Order;
import com.example.New_bazario.entities.Payment;
import com.example.New_bazario.repositories.OrderRepository;
import com.example.New_bazario.repositories.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentService(PaymentRepository paymentRepository, OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    public Payment createPayment(Integer orderId, String paymentMethod, String paymentStatus, String transactionId) {
        // Find the associated order
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

        // Ensure the order has not already been paid
        if (order.isPaid()) {
            throw new RuntimeException("Order has already been paid.");
        }

        // Create a new payment instance
        Payment payment = new Payment(paymentMethod, paymentStatus, transactionId, LocalDateTime.now(), order);
        Payment savedPayment = paymentRepository.save(payment);

        // Update the order as paid if the payment is successful
        if ("SUCCESS".equalsIgnoreCase(paymentStatus)) {
            order.setPaid(true); // Mark the order as paid
            orderRepository.save(order);
        }

        return savedPayment;
    }
}
