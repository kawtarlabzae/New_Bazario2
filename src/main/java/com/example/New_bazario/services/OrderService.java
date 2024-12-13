package com.example.New_bazario.services;

import com.example.New_bazario.entities.Order;

import com.example.New_bazario.repositories.CartRepository;
import com.example.New_bazario.repositories.OrderRepository;
import com.example.New_bazario.security.user.User;
import com.example.New_bazario.security.user.UserRepository;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, CartRepository cartRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    public Order createOrder(Integer userId, Integer cartId, BigDecimal totalPrice) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        Order order = new Order("PENDING", totalPrice, LocalDateTime.now(), LocalDateTime.now(), user, cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with ID: " + cartId)));
        return orderRepository.save(order);
    }

    public List<Order> getOrdersByUserId(Integer userId) {
        return orderRepository.findByUser_Id(userId);
    }
}
