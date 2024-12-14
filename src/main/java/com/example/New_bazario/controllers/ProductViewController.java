package com.example.New_bazario.controllers;

import com.example.New_bazario.entities.Product;
import com.example.New_bazario.services.CategoryService;
import com.example.New_bazario.services.ProductService;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductViewController {
    private final ProductService productService;
    private final CategoryService categoryService; // Add this

    @Autowired
    public ProductViewController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String listProducts(
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Integer minStock,
            Model model
    ) {
        List<Product> products;
        
        // Handle category filter
        if (categoryId != null) {
            List<Integer> categoryIds = Collections.singletonList(categoryId);
            products = productService.getProductsByCategories(categoryIds);
        } 
        // Handle search with filters
        else if (searchTerm != null || minPrice != null || maxPrice != null || minStock != null) {
            products = productService.advancedFilterProducts(null, minPrice, maxPrice, minStock, searchTerm);
        } 
        // Default - show all products
        else {
            products = productService.getAllProducts();
        }

        // Add all necessary attributes to the model
        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("selectedCategory", categoryId);
        model.addAttribute("searchTerm", searchTerm);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("minStock", minStock);

        return "products";
    }


}