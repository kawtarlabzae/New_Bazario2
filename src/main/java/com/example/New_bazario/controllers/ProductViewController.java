package com.example.New_bazario.controllers;

import com.example.New_bazario.entities.Product;
import com.example.New_bazario.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;
@Controller
@RequestMapping("/products")
public class ProductViewController {

    private final ProductService productService;

    public ProductViewController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String showFilteredProducts(
            Model model,
            @RequestParam(required = false) List<Integer> categoryIds,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Integer minStock,
            @RequestParam(required = false) String name
    ) {
        List<Product> products = productService.advancedFilterProducts(categoryIds, minPrice, maxPrice, minStock, name);
        model.addAttribute("products", products);
        return "products"; // Renders the Thymeleaf view
    }
}
