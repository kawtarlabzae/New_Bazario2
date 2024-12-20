package com.example.New_bazario.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.New_bazario.entities.Product;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT p FROM Product p WHERE "
            + "(:categoryIds IS NULL OR p.categoryId IN :categoryIds) AND "
            + "(:minPrice IS NULL OR p.price >= :minPrice) AND "
            + "(:maxPrice IS NULL OR p.price <= :maxPrice) AND "
            + "(:minStock IS NULL OR p.stockQuantity >= :minStock) AND "
            + "(:name IS NULL OR LOWER(p.name) LIKE CONCAT('%', LOWER(:name), '%'))")
    List<Product> advancedFilter(
            @Param("categoryIds") List<Integer> categoryIds,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("minStock") Integer minStock,
            @Param("name") String name);

    @Modifying
    @Transactional
    default void deleteProductAndRelatedCartItems(Integer productId, CartItemRepository cartItemRepository) {
        // Step 1: Delete cart items associated with the product
        cartItemRepository.deleteCartItemsByProductId(productId);

        // Step 2: Delete the product itself
        this.deleteById(productId);
    }

}

