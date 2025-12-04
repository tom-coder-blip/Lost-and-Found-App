package za.tomvuma.lostandfoundhub.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.tomvuma.lostandfoundhub.entity.User;
import za.tomvuma.lostandfoundhub.service.UserService;
import java.util.HashMap;
import java.util.Map;

@RestController // Use RestController for JSON responses
@RequestMapping("/api/auth") // Base path for auth-related APIs
public class AuthRestController {

    private final UserService userService;

    public AuthRestController(UserService userService) {
        this.userService = userService;
    }

    // 🔹 Register new user via JSON
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody User user) {
        userService.register(user.getUsername(), user.getPassword());
        Map<String, Object> response = new HashMap<>();
        response.put("username", user.getUsername());
        response.put("message", "User registered");
        return ResponseEntity.ok(response);
    }

    // 🔹 Login user via JSON
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        userService.login(user.getUsername(), user.getPassword());
        return ResponseEntity.ok("Login successful!");
    }

    // 🔹 Delete user by username
    @DeleteMapping("/delete/{username}")
    public ResponseEntity<String> deleteAccount(@PathVariable String username) {
        userService.deleteAccount(username);
        return ResponseEntity.ok("Account deleted: " + username);
    }
}
