package com.example.New_bazario.entities;

import com.example.New_bazario.entities.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductFactory {

    public static Product createProduct(String name, String description, BigDecimal price, Integer stockQuantity, Integer categoryId, String imageUrl) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setStockQuantity(stockQuantity);
        product.setCategoryId(categoryId);
        product.setImageUrl(imageUrl);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        return product;
    }
}
