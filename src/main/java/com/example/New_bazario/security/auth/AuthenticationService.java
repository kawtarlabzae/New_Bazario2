package com.example.New_bazario.security.auth;

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
@RequiredArgsConstructor
public class AuthenticationService {
	private final UserRepository repository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	
	public AuthenticationResponse register(RegisterRequest request) {
		var user= User.builder()
				.firstname(request.getFirstname())
				.lastname(request.getLastname())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.role(Role.USER)
				.build();
		repository.save(user);
		var jwtToken=jwtService.generateToken(user);
		return AuthenticationResponse.builder().token(jwtToken).build();
	}
	
	
	public AuthenticationService(UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService,
			AuthenticationManager authenticationManager) {
		super();
		this.repository = repository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
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
