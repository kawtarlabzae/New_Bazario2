package com.example.New_bazario.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.New_bazario.entities.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT p FROM Product p WHERE p.categoryId IN :categoryIds")
    List<Product> findProductsByCategoryIds(@Param("categoryIds") List<Integer> categoryIds);
}
