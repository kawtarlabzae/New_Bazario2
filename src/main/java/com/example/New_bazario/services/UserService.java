package com.example.New_bazario.services;

import com.example.New_bazario.security.user.Role;
import com.example.New_bazario.security.user.User;
import com.example.New_bazario.security.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Role getUserRoleById(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        return user.getRole();
    }
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    /**
     * Get a user by their ID.
     *
     * @param id The ID of the user.
     * @return The user if found.
     * @throws RuntimeException if the user is not found.
     */
    public User getUserById(Integer id) {
        return userRepository.findUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }
}

