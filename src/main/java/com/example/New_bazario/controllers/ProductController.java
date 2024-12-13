package com.example.New_bazario.controllers;

import com.example.New_bazario.entities.Product;
import com.example.New_bazario.services.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Create a new product and assign it to a category
    @PostMapping
    public Product addProduct(
            @RequestParam Integer categoryId,
            @RequestBody Product product) {
        return productService.addProduct(categoryId, product);
    }

    // Get a product by ID
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Integer id) {
        return productService.getProductById(id);
    }

    // Get all products
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // Update a product and its category
    @PutMapping("/{id}")
    public Product updateProduct(
            @PathVariable Integer id,
            @RequestParam Integer categoryId,
            @RequestBody Product updatedProduct) {
        return productService.updateProduct(id, updatedProduct, categoryId);
    }

    // Delete a product
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
    }
}