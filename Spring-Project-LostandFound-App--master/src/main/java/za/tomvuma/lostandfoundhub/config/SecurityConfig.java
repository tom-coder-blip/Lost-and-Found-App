package za.tomvuma.lostandfoundhub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import za.tomvuma.lostandfoundhub.repository.UserRepository;

@Configuration // Marks this class as a configuration class
public class SecurityConfig {

    @Bean // Registers a custom user details service
    public UserDetailsService userDetailsService(UserRepository repo) {
        return username -> repo.findByUsername(username) // Look up user by username
                .map(user -> User.builder() // Build a Spring Security user object
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .roles(user.getRole()) // Assign role (e.g., USER or ADMIN)
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("User not found")); // Handle missing user
    }

    @Bean // Registers a password encoder bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Use BCrypt for secure password hashing
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/style.css",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/report",
                                "/register",
                                "/login",
                                "/api/auth/**",
                                "/api/items/**",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/docs",         // Swagger UI custom path
                                "/api-docs"      // OpenAPI JSON custom path
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(
                                "/api/**",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/docs",        // Allow Swagger UI
                                "/api-docs"     // Allow OpenAPI JSON
                        )
                )
                .formLogin(form -> form
                        .loginPage("/login").defaultSuccessUrl("/", true).permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout").logoutSuccessUrl("/logout-success").permitAll()
                );

        return http.build();
    }
}
