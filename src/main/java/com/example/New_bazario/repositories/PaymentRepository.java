package com.example.New_bazario.repositories;

import com.example.New_bazario.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    Payment findByOrder_OrderId(Integer orderId); // Retrieve payment by order ID
}
