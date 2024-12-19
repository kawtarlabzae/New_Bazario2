package com.example.New_bazario.repositories;

import com.example.New_bazario.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    //fixing n+1 problem
    @Query("SELECT ci FROM CartItem ci JOIN FETCH ci.product WHERE ci.cart.cartId = :cartId")
    List<CartItem> findCartItemsWithProductsByCartId(@Param("cartId") Integer cartId);

    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.cartId = :cartId AND ci.product.productId = :productId")
    Optional<CartItem> findByCart_CartIdAndProduct_ProductId(
        @Param("cartId") Integer cartId, 
        @Param("productId") Integer productId
    );
}