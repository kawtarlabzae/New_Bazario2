package com.example.New_bazario.services;

import com.example.New_bazario.entities.Product;
import com.example.New_bazario.entities.ProductFactory;
import com.example.New_bazario.repositories.CartItemRepository;
import com.example.New_bazario.repositories.ProductRepository;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    public ProductService(ProductRepository productRepository, CartItemRepository cartItemRepository) {
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public Product getProductById(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product updateProduct(Integer productId, Product updatedProduct) {
        Product product = getProductById(productId);
        // Update product details
        product.setName(updatedProduct.getName());
        product.setDescription(updatedProduct.getDescription());
        product.setPrice(updatedProduct.getPrice());
        product.setStockQuantity(updatedProduct.getStockQuantity());
        product.setImageUrl(updatedProduct.getImageUrl());
        product.setUpdatedAt(updatedProduct.getUpdatedAt());
        product.setCategoryId(updatedProduct.getCategoryId());

        return productRepository.save(product);
    }

    public void deleteProduct(Integer productId) {
        if (!productRepository.existsById(productId)) {
            throw new RuntimeException("Product not found with ID: " + productId);
        }
        productRepository.deleteById(productId);
    }

    public List<Product> searchProducts(String searchTerm) {
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            return productRepository.findAll().stream()
                    .filter(product -> product.getName().toLowerCase().contains(searchTerm.toLowerCase()))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
    public List<Product> advancedFilterProducts(
            List<Integer> categoryIds,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Integer minStock,
            String name) {
        // Delegate filtering to the database query
        return productRepository.advancedFilter(categoryIds, minPrice, maxPrice, minStock, name);
    }


    public void updateStock(Integer productId, Integer stockQuantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));
        product.setStockQuantity(stockQuantity);
        productRepository.save(product);
    }
    @Transactional
    public void deleteProductAndCartItems(Integer productId) {
        cartItemRepository.deleteCartItemsByProductId(productId);

        if (!productRepository.existsById(productId)) {
            throw new RuntimeException("Product not found with ID: " + productId);
        }
        productRepository.deleteById(productId);
    }

    public Product addProduct(String name, String description, BigDecimal price, Integer stockQuantity, Integer categoryId, String imageUrl) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setStockQuantity(stockQuantity);
        product.setCategoryId(categoryId);
        product.setImageUrl(imageUrl);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        return productRepository.save(product);
    }
    public void updatePrice(Integer productId, BigDecimal price) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));
        product.setPrice(price);
        productRepository.save(product);
    }

}
