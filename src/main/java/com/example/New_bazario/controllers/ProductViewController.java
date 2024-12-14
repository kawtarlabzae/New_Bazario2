package com.example.New_bazario.controllers;

import com.example.New_bazario.entities.Category;
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
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductViewController {
    private final ProductService productService;
    private final CategoryService categoryService;

    @Autowired
    public ProductViewController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String listProducts(
            @RequestParam(required = false) List<Integer> categoryIds,
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Integer minStock,
            Model model) {

        // Initialize categoryIds if null
        if (categoryIds == null) {
            categoryIds = new ArrayList<>();
        }

        try {
            // Fetch categories for UI
            List<Category> categories = categoryService.getAllCategories();
            model.addAttribute("categories", categories);

            // Apply all filters together
            List<Product> products = productService.advancedFilterProducts(
                categoryIds, minPrice, maxPrice, minStock, searchTerm);

            // Add filtered products and filter state to the model
            model.addAttribute("products", products != null ? products : new ArrayList<>());
            model.addAttribute("selectedCategories", categoryIds);
            model.addAttribute("searchTerm", searchTerm);
            model.addAttribute("minPrice", minPrice);
            model.addAttribute("maxPrice", maxPrice);
            model.addAttribute("minStock", minStock);

            return "products";
        } catch (Exception e) {
            e.printStackTrace(); // This will help with debugging
            throw e;
        }
    }
}