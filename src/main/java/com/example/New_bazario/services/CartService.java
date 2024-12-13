package com.example.New_bazario.services;

import com.example.New_bazario.entities.Cart;

import com.example.New_bazario.repositories.CartRepository;
import com.example.New_bazario.security.user.User;
import com.example.New_bazario.security.user.UserRepository;

import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    public Cart createCart(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        Cart cart = new Cart(user, java.time.LocalDateTime.now(), java.time.LocalDateTime.now());
        return cartRepository.save(cart);
    }

    public Cart getCartByUserId(Integer userId) {
        return cartRepository.findByUser_Id(userId);
    }
}
