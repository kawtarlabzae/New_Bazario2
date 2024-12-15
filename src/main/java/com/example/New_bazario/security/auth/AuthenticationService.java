package com.example.New_bazario.security.auth;

import java.time.LocalDateTime;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.New_bazario.security.config.JwtService;
import com.example.New_bazario.security.user.Role;
import com.example.New_bazario.security.user.User;
import com.example.New_bazario.security.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
public class AuthenticationService {
	private final UserRepository repository ;
	private final PasswordEncoder passwordEncoder ;
	private final JwtService jwtService ;
	private final AuthenticationManager authenticationManager ;

	public AuthenticationService(AuthenticationManager authenticationManager, UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService) {
		this.authenticationManager = authenticationManager;
		this.repository = repository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
	}

	public AuthenticationResponse register(RegisterRequest request) {
		var user= User.builder()
				.firstname(request.getFirstname())
				.lastname(request.getLastname())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.role(Role.USER)
				.activity(request.getActivity())
				.phoneNumber(request.getPhoneNumber())
				.createdAt(LocalDateTime.now()) 
				.build();
		repository.save(user);
		var jwtToken=jwtService.generateToken(user);
		return AuthenticationResponse.builder().token(jwtToken).build();
	}
	
	 public User getUserByEmail(String email) {
	        return repository.findByEmail(email)
	            .orElseThrow(() -> new RuntimeException("User not found"));
	    }
	


	public AuthenticationResponse authenticate(AuthenticationRequest request) {
	    try {
	        System.out.println("Authenticating user: " + request.getEmail());
	        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
	                request.getEmail(),
	                request.getPassword()
	        ));
	        System.out.println("Authentication successful for: " + request.getEmail());
	        
	        var user = repository.findByEmail(request.getEmail()).orElseThrow(() -> 
	            new RuntimeException("User not found with email: " + request.getEmail())
	        );
	        System.out.println("User found: " + user.getEmail());

	        var jwtToken = jwtService.generateToken(user);
	        System.out.println("Generated JWT token: " + jwtToken);

	        return AuthenticationResponse.builder().token(jwtToken).build();
	    } catch (Exception e) {
	        System.err.println("Error during authentication: " + e.getMessage());
	        e.printStackTrace(); // Logs the full stack trace
	        throw e; // Re-throw the exception to propagate it
	    }
	}


}