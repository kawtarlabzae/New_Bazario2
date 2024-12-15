package com.example.New_bazario.security.auth;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.New_bazario.security.auth.AuthenticationResponse;
import com.example.New_bazario.security.user.User;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final AuthenticationService service;

    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @GetMapping("/authenticate")
    public String showAuthenticatePage() {
        return "authenticate";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute RegisterRequest request) {
        service.register(request);
        return "redirect:/api/v1/auth/authenticate";
    }

    @PostMapping("/authenticate")
    public String authenticate(
            @ModelAttribute AuthenticationRequest request, 
            Model model,
            HttpSession session) {
        try {
            AuthenticationResponse response = service.authenticate(request);
            
            if (response != null && response.getToken() != null && !response.getToken().isEmpty()) {
                // Get user details and store in session
                User user = service.getUserByEmail(request.getEmail());
                session.setAttribute("userId", user.getId());
                session.setAttribute("userEmail", user.getEmail());
                session.setAttribute("userName", user.getFirstname() + " " + user.getLastname());
                session.setAttribute("token", response.getToken());
                
                return "redirect:/products";
            } else {
                model.addAttribute("error", "Invalid credentials. Please try again.");
                return "authenticate";
            }
        } catch (Exception e) {
            model.addAttribute("error", "Invalid credentials. Please try again.");
            return "authenticate";
        }
    }

    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {
        // Check if user is logged in
        if (session.getAttribute("userId") == null) {
            return "redirect:/api/v1/auth/authenticate";
        }
        
        // Add user information to model
        model.addAttribute("userName", session.getAttribute("userName"));
        return "dashboard";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/api/v1/auth/authenticate";
    }
}