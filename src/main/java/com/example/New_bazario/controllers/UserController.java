package com.example.New_bazario.controllers;

import com.example.New_bazario.security.user.User;
import com.example.New_bazario.security.user.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String showProfilePage(@AuthenticationPrincipal User user, Model model) {
        if (user == null) {
            System.out.println("No user found in the SecurityContext. Redirecting to authenticate.");
            return "redirect:/api/v1/auth/authenticate";
        }
        System.out.println("Authenticated user: " + user.getEmail());
        model.addAttribute("user", user);
        return "profile";
    }

}
