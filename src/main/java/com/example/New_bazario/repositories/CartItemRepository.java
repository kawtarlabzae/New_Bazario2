package com.example.New_bazario.repositories;

import com.example.New_bazario.entities.CartItem;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findCartItemsWithProductsByCartId(Integer cartId);

    Optional<CartItem> findByCartIdAndProduct_ProductId(Integer cartId, Integer productId);
}
