package com.example.New_bazario.services;

import com.example.New_bazario.entities.Cart;

import com.example.New_bazario.repositories.CartRepository;
import com.example.New_bazario.security.user.User;
import com.example.New_bazario.security.user.UserRepository;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    public Cart getOrCreateCart(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        
        if (user.getCart() == null) {
            Cart newCart = new Cart();
            newCart.setCreatedAt(LocalDateTime.now());
            newCart.setUpdatedAt(LocalDateTime.now());
            newCart = cartRepository.save(newCart);
            user.setCart(newCart);
            userRepository.save(user);
            return newCart;
        }
        
        return user.getCart();
    }
}
