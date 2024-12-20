package com.example.New_bazario.controllers;

import com.example.New_bazario.security.user.User;
import com.example.New_bazario.security.user.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/profile")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String showProfilePage(HttpSession session, Model model) {
        // Retrieve user ID from session
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            System.out.println("No user found in session. Redirecting to authenticate.");
            return "redirect:/api/v1/auth/authenticate";
        }

        // Retrieve user from database
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        System.out.println("HELLO USER");
        // Add user details to the model
        model.addAttribute("user", user);
        return "profile";
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // Invalidate the session to log out the user
        session.invalidate();
        System.out.println("User session invalidated. Logging out.");
        return "redirect:/"; // Redirect to the login page
    }
}
