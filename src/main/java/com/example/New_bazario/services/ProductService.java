package com.example.New_bazario.services;

import com.example.New_bazario.entities.Product;
import com.example.New_bazario.entities.ProductFactory;
import com.example.New_bazario.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product addProduct(String name, String description, BigDecimal price, Integer stockQuantity, Integer categoryId, String imageUrl) {
        Product product = ProductFactory.createProduct(name, description, price, stockQuantity, categoryId, imageUrl);
        return productRepository.save(product);
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

    public List<Product> getProductsByCategories(List<Integer> categoryIds) {
    	if (categoryIds == null || categoryIds.isEmpty()) {
            return getAllProducts();
        }
        return productRepository.findProductsByCategoryIds(categoryIds);
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

        // Fetch all products from the database
        List<Product> products = productRepository.findAll();

        // Apply all filters
        return products.stream()
                .filter(product -> (categoryIds == null || categoryIds.isEmpty() || categoryIds.contains(product.getCategoryId())))
                .filter(product -> (minPrice == null || product.getPrice().compareTo(minPrice) >= 0))
                .filter(product -> (maxPrice == null || product.getPrice().compareTo(maxPrice) <= 0))
                .filter(product -> (minStock == null || product.getStockQuantity() >= minStock))
                .filter(product -> (name == null || product.getName().toLowerCase().contains(name.toLowerCase())))
                .collect(Collectors.toList());
    }

}
