package com.example.New_bazario.controllers;

import com.example.New_bazario.security.user.Role;
import com.example.New_bazario.services.CategoryService;
import com.example.New_bazario.services.ProductService;
import com.example.New_bazario.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductService productService;
    private final UserService userService;
    private final CategoryService categoryService;

    public AdminController(ProductService productService, UserService userService, CategoryService categoryService) {
        this.productService = productService;
        this.userService = userService;
        this.categoryService = categoryService;

    }

    @PostMapping("/update-stock")
    public String updateStock(@RequestParam Integer productId, @RequestParam Integer stockQuantity, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        Role userRole = userService.getUserRoleById(userId);
        if (userRole != Role.ADMIN) {
            return "redirect:/";
        }

        productService.updateStock(productId, stockQuantity);
        return "redirect:/products";
    }
    @PostMapping("/delete-product")
    public String deleteProduct(@RequestParam Integer productId) {
        productService.deleteProductAndCartItems(productId);
        return "redirect:/products"; // Redirect back to the product listing page
    }

    @GetMapping("/add-product")
    public String showAddProductForm(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "add-product";
    }

    @PostMapping("/add-product")
    public String addProduct(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam BigDecimal price,
            @RequestParam Integer stockQuantity,
            @RequestParam Integer categoryId,
            @RequestParam(required = false) String imageUrl
    ) {
        productService.addProduct(name, description, price, stockQuantity, categoryId, imageUrl);
        return "redirect:/products"; // Redirect to the product listing page
    }
    @PostMapping("/update-price")
    public String updatePrice(@RequestParam Integer productId, @RequestParam BigDecimal price, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        Role userRole = userService.getUserRoleById(userId);
        if (userRole != Role.ADMIN) {
            return "redirect:/";
        }

        productService.updatePrice(productId, price);
        return "redirect:/products";
    }
}
