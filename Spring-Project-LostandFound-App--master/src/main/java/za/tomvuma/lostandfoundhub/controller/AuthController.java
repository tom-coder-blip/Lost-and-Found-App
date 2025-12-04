package za.tomvuma.lostandfoundhub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import za.tomvuma.lostandfoundhub.service.UserService;
import java.security.Principal;

@Controller // Handles user registration, login, logout, and account deletion
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register") // Show registration form
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping("/register") // Handle registration
    public String register(@RequestParam String username, @RequestParam String password) {
        userService.register(username, password); // Save user
        return "redirect:/login"; // Redirect to login
    }

    @GetMapping("/login") // Show login form
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login") // Handle login
    public String login(@RequestParam String username, @RequestParam String password) {
        userService.login(username, password); // Authenticate user
        return "redirect:/"; // Redirect to home
    }

    @GetMapping("/confirm-delete-account") // Show account deletion confirmation
    public String confirmDeleteAccount() {
        return "confirm-delete-account";
    }

    @GetMapping("/logout-success") // Show logout success page
    public String logoutSuccess() {
        return "logout-success";
    }

    @PostMapping("/logout") // Handle logout
    public String logout() {
        return "redirect:/logout-success";
    }

    @PostMapping("/delete-account") // Delete user account
    public String deleteAccount(Principal principal) {
        if (principal != null) {
            userService.deleteAccount(principal.getName()); // Delete by username
        }
        return "redirect:/register"; // Redirect to register after deletion
    }
}
