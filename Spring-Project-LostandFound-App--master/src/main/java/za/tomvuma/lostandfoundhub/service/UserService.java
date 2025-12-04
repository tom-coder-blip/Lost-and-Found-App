package za.tomvuma.lostandfoundhub.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import za.tomvuma.lostandfoundhub.entity.User;
import za.tomvuma.lostandfoundhub.repository.UserRepository;

@Service // Marks this class as a Spring service (business logic layer)
public class UserService {

    private final UserRepository repo; // Repository for database access
    private final PasswordEncoder encoder; // Used to securely hash passwords

    // Constructor injection of dependencies
    public UserService(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    // Register a new user
    public void register(String username, String rawPassword) {
        User user = new User();
        user.setUsername(username);
        // Encode password before saving (never store plain text passwords!)
        user.setPassword(encoder.encode(rawPassword));
        repo.save(user); // Save user in database
    }

    // Simple login check (prints success if password matches)
    public void login(String username, String rawPassword) {
        repo.findByUsername(username).ifPresent(user -> {
            // Compare raw password with encoded password
            if (encoder.matches(rawPassword, user.getPassword())) {
                System.out.println("Login successful!");
            }
        });
    }

    // Delete user account by username
    public void deleteAccount(String username) {
        repo.findByUsername(username).ifPresent(repo::delete);
    }
}