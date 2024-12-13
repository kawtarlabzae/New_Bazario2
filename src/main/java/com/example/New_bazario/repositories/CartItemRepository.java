package com.example.New_bazario.repositories;

import com.example.New_bazario.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findByCart_CartId(Integer cartId); // Retrieve cart items by cart ID
}
