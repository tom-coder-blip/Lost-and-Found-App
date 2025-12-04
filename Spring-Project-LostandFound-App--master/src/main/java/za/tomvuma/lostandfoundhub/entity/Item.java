package za.tomvuma.lostandfoundhub.entity;

// JPA annotations for database mapping
import jakarta.persistence.*;
import java.time.LocalDateTime;
// Lombok annotations to auto-generate getters and setters
import lombok.Getter;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Entity // Marks this class as a JPA entity
public class Item {

    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment ID
    private Long id;

    private String title; // Short title of the item

    @Column(length = 2000) // Allow long descriptions
    private String description;

    @Schema(description = "Item category", example = "wallet")
    private String category; // Item category (e.g., Electronics, Clothing)

    @Enumerated(EnumType.STRING) // Store enum as string in DB
    private Status status; // LOST, FOUND, or CLAIMED

    private String location; // Where the item was lost/found

    private LocalDateTime dateReported = LocalDateTime.now(); // Timestamp when item was reported

    private String contactInfo; // How to reach the person who reported it

    private String ownerUsername; // Username of the person who submitted the item

    // Enum to represent item status
    public enum Status { LOST, FOUND, CLAIMED }
}