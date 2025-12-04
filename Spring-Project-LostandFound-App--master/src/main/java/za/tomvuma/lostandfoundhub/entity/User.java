package za.tomvuma.lostandfoundhub.entity;

// JPA annotations for database mapping
import jakarta.persistence.*;
// Lombok annotations to auto-generate getters and setters
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity // Marks this class as a JPA entity (maps to a database table)
@Table(name = "users") // Table name in the database
public class User {

    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment ID
    private Long id;

    @Column(unique = true) // Username must be unique
    private String username;

    private String password; // Encrypted password

    private String role = "USER"; // Default role assigned to new users
}