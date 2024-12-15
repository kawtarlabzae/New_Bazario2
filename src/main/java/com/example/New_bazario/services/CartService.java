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

    public Cart createCart(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        
        // Check if user already has a cart
        Cart existingCart = getCartByUserId(userId);
        if (existingCart != null) {
            return existingCart;
        }

        Cart cart = new Cart(user, LocalDateTime.now(), LocalDateTime.now());
        return cartRepository.save(cart);
    }

    public Cart getCartByUserId(Integer userId) {
        Cart cart = cartRepository.findByUser_Id(userId);
        if (cart != null) {
            cart.getCartItems(); // Initialize cart items
        }
        return cart;
    }

    public Cart getCartById(Integer cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with ID: " + cartId));
    }

    public boolean validateCartOwner(Integer cartId, Integer userId) {
        Cart cart = getCartById(cartId);
        return cart.getUser().getId().equals(userId);
    }
}