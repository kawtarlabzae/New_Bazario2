package com.example.New_bazario.services;

import com.example.New_bazario.entities.Category;
import com.example.New_bazario.entities.Product;
import com.example.New_bazario.repositories.CategoryRepository;
import com.example.New_bazario.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    // Add a new product and assign it to a category
    public Product addProduct(Integer categoryId, Product product) {
        // Fetch category by ID
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + categoryId));
        
        // Assign category to the product
        product.setCategory(category);

        return productRepository.save(product);
    }

    // Get a product by ID
    public Product getProductById(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));
    }

    // Get all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Update a product and its category
    public Product updateProduct(Integer productId, Product updatedProduct, Integer categoryId) {
        Product product = getProductById(productId);

        // Update product details
        product.setName(updatedProduct.getName());
        product.setDescription(updatedProduct.getDescription());
        product.setPrice(updatedProduct.getPrice());
        product.setStockQuantity(updatedProduct.getStockQuantity());
        product.setImageUrl(updatedProduct.getImageUrl());
        product.setUpdatedAt(updatedProduct.getUpdatedAt());

        // Update category
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + categoryId));
        product.setCategory(category);

        return productRepository.save(product);
    }

    // Delete a product by ID
    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }
}
