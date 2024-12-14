package com.example.New_bazario.controllers;

import com.example.New_bazario.entities.Product;
import com.example.New_bazario.services.ProductService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public Product addProduct(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam BigDecimal price,
            @RequestParam Integer stockQuantity,
            @RequestParam Integer categoryId,
            @RequestParam String imageUrl
    ) {
        return productService.addProduct(name, description, price, stockQuantity, categoryId, imageUrl);
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Integer id) {
        return productService.getProductById(id);
    }
    @PutMapping("/{productId}")
    public Product updateProduct(@PathVariable Integer productId, @RequestBody Product updatedProduct) {
        return productService.updateProduct(productId, updatedProduct);
    }

    @DeleteMapping("/{productId}")
    public String deleteProduct(@PathVariable Integer productId) {
        productService.deleteProduct(productId);
        return "Product with ID " + productId + " deleted successfully.";
    }
    @GetMapping
    public List<Product> getFilteredProducts(
            @RequestParam(required = false) List<Integer> categoryIds,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Integer minStock,
            @RequestParam(required = false) String name
    ) {
        if(minStock==null){
            minStock=1;
        }
        return productService.advancedFilterProducts(categoryIds, minPrice, maxPrice, minStock, name);
    }


}
