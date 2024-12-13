package com.example.New_bazario.repositories;

import com.example.New_bazario.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    //fixing n+1 problem
    @Query("SELECT ci FROM CartItem ci JOIN FETCH ci.product WHERE ci.cart.cartId = :cartId")
    List<CartItem> findCartItemsWithProductsByCartId(@Param("cartId") Integer cartId);

}
