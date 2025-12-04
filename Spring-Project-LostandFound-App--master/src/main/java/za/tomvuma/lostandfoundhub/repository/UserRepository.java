package za.tomvuma.lostandfoundhub.repository;

// Import Spring Data JPA base repository
import org.springframework.data.jpa.repository.JpaRepository;
import za.tomvuma.lostandfoundhub.entity.User;

import java.util.Optional;

// Repository interface for User entity
public interface UserRepository extends JpaRepository<User, Long> {

    // Custom query method to find a user by username
    // Returns Optional<User> to handle case where user may not exist
    Optional<User> findByUsername(String username);
}
