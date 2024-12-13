package com.example.New_bazario.security.auth;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.New_bazario.security.auth.AuthenticationResponse;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api/v1/auth")

public class AuthenticationController {

    public AuthenticationController(AuthenticationService service) {
		super();
		this.service = service;
	}

	private final AuthenticationService service;

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register"; // Render register.html
    }

    @GetMapping("/authenticate")
    public String showAuthenticatePage() {
        return "authenticate"; // Render authenticate.html
    }

    @PostMapping("/register")
    public String register(@ModelAttribute RegisterRequest request) {
        service.register(request); // Register the user
        return "redirect:/api/v1/auth/authenticate"; // Redirect to authentication page
    }

    @PostMapping("/authenticate")
    public String authenticate(@ModelAttribute AuthenticationRequest request, Model model) {
        try {
            AuthenticationResponse response = service.authenticate(request);

            // Check if the response contains a valid token
            if (response != null && response.getToken() != null && !response.getToken().isEmpty()) {
            	
                return "redirect:/api/v1/auth/dashboard"; // Redirect to dashboard if authenticated
            } else {
                // Add error message to model if the token is null/empty
                model.addAttribute("error", "Invalid credentials. Please try again.");
                return "authenticate"; // Stay on the authentication page
            }
        } catch (Exception e) {
            // Handle authentication exceptions gracefully
            model.addAttribute("error", "Invalid credentials. Please try again.");
            return "authenticate"; // Stay on the authentication page
        }
    }

    @GetMapping("/dashboard")
    public String showDashboard() {
        return "dashboard"; // Render dashboard.html
    }
}
