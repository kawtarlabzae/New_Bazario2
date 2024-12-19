package com.example.New_bazario.services;

import com.example.New_bazario.entities.Cart;
import com.example.New_bazario.entities.CartItem;
import com.example.New_bazario.entities.Product;
import com.example.New_bazario.repositories.CartItemRepository;
import com.example.New_bazario.repositories.CartRepository;
import com.example.New_bazario.repositories.ProductRepository;
import com.example.New_bazario.security.user.User;
import com.example.New_bazario.security.user.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository; // Add this

    public CartItemService(CartItemRepository cartItemRepository, 
                         CartRepository cartRepository, 
                         ProductRepository productRepository,
                         UserRepository userRepository) {
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public CartItem addCartItem(Integer userId, Integer productId, Integer quantity) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        
        Cart cart = user.getCart();
        if (cart == null) {
            cart = new Cart();
            cart.setCreatedAt(LocalDateTime.now());
            cart.setUpdatedAt(LocalDateTime.now());
            cart = cartRepository.save(cart);
            user.setCart(cart);
            userRepository.save(user);
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

        // Check if product is already in cart
        CartItem cartItem = cartItemRepository
                .findByCart_CartIdAndProduct_ProductId(cart.getCartId(), productId)
                .orElse(null);

        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            cartItem = new CartItem(cart, product, quantity);
        }

        cartItem = cartItemRepository.save(cartItem);

        cartRepository.save(cart);

        return cartItem;
    }
    
    
    public List<CartItem> getCartItemsByCartId(Integer cartId) {
        return cartItemRepository.findCartItemsWithProductsByCartId(cartId);
    }
}