package com.example.New_bazario.controllers;

import com.example.New_bazario.security.user.Role;
import com.example.New_bazario.services.ProductService;
import com.example.New_bazario.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductService productService;
    private final UserService userService;

    public AdminController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
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
}
