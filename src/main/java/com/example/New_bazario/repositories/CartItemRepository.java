package com.example.New_bazario.repositories;

import com.example.New_bazario.entities.CartItem;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findCartItemsWithProductsByCartId(Integer cartId);

    Optional<CartItem> findByCartIdAndProduct_ProductId(Integer cartId, Integer productId);
    @Modifying
    @Query("DELETE FROM CartItem c WHERE c.product.productId = :productId")
    void deleteCartItemsByProductId(@Param("productId") Integer productId);

}
