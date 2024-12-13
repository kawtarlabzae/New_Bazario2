package com.example.New_bazario.services;

import com.example.New_bazario.entities.Product;
import com.example.New_bazario.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product addProduct(Integer categoryId, Product product) {
        product.setCategoryId(categoryId);
        return productRepository.save(product);
    }

    public Product getProductById(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product updateProduct(Integer productId, Product updatedProduct, Integer categoryId) {
        Product product = getProductById(productId);

        // Update product details
        product.setName(updatedProduct.getName());
        product.setDescription(updatedProduct.getDescription());
        product.setPrice(updatedProduct.getPrice());
        product.setStockQuantity(updatedProduct.getStockQuantity());
        product.setImageUrl(updatedProduct.getImageUrl());
        product.setUpdatedAt(updatedProduct.getUpdatedAt());

        // Update category ID
        product.setCategoryId(categoryId);

        return productRepository.save(product);
    }

    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }

    public List<Product> getProductsByCategories(List<Integer> categoryIds) {
        return productRepository.findProductsByCategoryIds(categoryIds);
    }
    public List<Product> advancedFilterProducts(
            List<Integer> categoryIds,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Integer minStock,
            String name
    ) {
        List<Product> products = productRepository.findAll();

        return products.stream()
                .filter(product -> (categoryIds == null || categoryIds.contains(product.getCategoryId())))
                .filter(product -> (minPrice == null || product.getPrice().compareTo(minPrice) >= 0))
                .filter(product -> (maxPrice == null || product.getPrice().compareTo(maxPrice) <= 0))
                .filter(product -> (minStock == null || product.getStockQuantity() >= minStock))
                .filter(product -> (name == null || product.getName().toLowerCase().contains(name.toLowerCase())))
                .collect(Collectors.toList());
    }
}
