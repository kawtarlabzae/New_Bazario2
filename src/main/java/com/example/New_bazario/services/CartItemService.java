package com.example.New_bazario.services;

import com.example.New_bazario.entities.Cart;
import com.example.New_bazario.entities.CartItem;
import com.example.New_bazario.entities.Product;
import com.example.New_bazario.repositories.CartItemRepository;
import com.example.New_bazario.repositories.CartRepository;
import com.example.New_bazario.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartItemService(CartItemRepository cartItemRepository, CartRepository cartRepository, ProductRepository productRepository) {
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public CartItem addCartItem(Integer cartId, Integer productId, Integer quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with ID: " + cartId));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));
        CartItem cartItem = new CartItem(cart, product, quantity);
        return cartItemRepository.save(cartItem);
    }

    public List<CartItem> getCartItemsByCartId(Integer cartId) {
        return cartItemRepository.findCartItemsWithProductsByCartId(cartId);
    }
}