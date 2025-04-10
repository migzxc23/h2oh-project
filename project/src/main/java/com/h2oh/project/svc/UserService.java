package com.h2oh.project.svc;

import com.h2oh.project.entity.User;
import com.h2oh.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User signUp(User user) {
        try {
            // Hash password manually before saving
            String hashedPassword = hashPassword(user.getPassword());
            user.setPassword(hashedPassword);
            user.setRole("USER");
            return userRepository.save(user);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error while hashing password", e);
        }
    }

    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && verifyPassword(password, user.getPassword())) {
            return user;  // Successful login
        }
        return null;  // Failed login
    }

    // Method to hash the password using SHA-256
    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = digest.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hashedBytes); // Encode hashed password in Base64
    }

    // Method to verify password by comparing the hashed password
    private boolean verifyPassword(String password, String storedHashedPassword) {
        try {
            String hashedPassword = hashPassword(password);
            return hashedPassword.equals(storedHashedPassword);  // Check if the hashes match
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error while verifying password", e);
        }
    }
}