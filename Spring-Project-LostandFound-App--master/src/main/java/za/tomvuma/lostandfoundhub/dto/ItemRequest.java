package za.tomvuma.lostandfoundhub.dto;

// Import validation annotations
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

// DTO used to safely transfer item data from form to backend
public class ItemRequest {

    // Title field must not be blank and must be under 100 characters
    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title must be under 100 characters")
    private String title;

    // Description field must not be blank and must be under 2000 characters
    @NotBlank(message = "Description is required")
    @Size(max = 2000, message = "Description must be under 2000 characters")
    private String description;

    // Category field must not be blank (e.g., Electronics, Clothing)
    @Schema(example = "wallet")
    private String category;

    // Status field must not be blank (e.g., LOST, FOUND, CLAIMED)
    @NotBlank(message = "Status is required")
    private String status; // Validated in controller or service

    // Location field must not be blank (e.g., Johannesburg Mall)
    @NotBlank(message = "Location is required")
    private String location;

    // Contact info field must not be blank (e.g., phone or email)
    @NotBlank(message = "Contact info is required")
    private String contactInfo;

    // Optional: Username of the person who submitted the item
    private String ownerUsername;

    // Getters and Setters — used by Spring to access and populate fields
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getContactInfo() { return contactInfo; }
    public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }

    public String getOwnerUsername() { return ownerUsername; }
    public void setOwnerUsername(String ownerUsername) { this.ownerUsername = ownerUsername; }
}
