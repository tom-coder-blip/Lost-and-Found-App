package za.tomvuma.lostandfoundhub.service;

import org.springframework.stereotype.Service;
import za.tomvuma.lostandfoundhub.dto.ItemRequest;
import za.tomvuma.lostandfoundhub.entity.Item;
import za.tomvuma.lostandfoundhub.repository.ItemRepository;

import java.util.List;
import java.util.Optional;

@Service // Marks this class as a Spring service
public class ItemService {

    private final ItemRepository repo; // Repository for item database access

    // Constructor injection
    public ItemService(ItemRepository repo) {
        this.repo = repo;
    }

    // 🔹 Create new item from DTO (form data)
    public Item create(ItemRequest request) {
        Item item = new Item();
        item.setTitle(request.getTitle());
        item.setDescription(request.getDescription());
        item.setCategory(request.getCategory());
        // Convert status string (e.g., "lost") to enum (LOST)
        item.setStatus(Item.Status.valueOf(request.getStatus().toUpperCase()));
        item.setLocation(request.getLocation());
        item.setContactInfo(request.getContactInfo());
        item.setOwnerUsername(request.getOwnerUsername());
        return repo.save(item); // Save item in database
    }

    // 🔹 Get all items
    public List<Item> findAll() {
        return repo.findAll();
    }

    // 🔹 Get one item by ID
    public Optional<Item> findById(Long id) {
        return repo.findById(id);
    }

    // 🔹 Update existing item
    public Optional<Item> update(Long id, ItemRequest request) {
        return repo.findById(id).map(existing -> {
            existing.setTitle(request.getTitle());
            existing.setDescription(request.getDescription());
            existing.setCategory(request.getCategory());
            existing.setStatus(Item.Status.valueOf(request.getStatus().toUpperCase()));
            existing.setLocation(request.getLocation());
            existing.setContactInfo(request.getContactInfo());
            existing.setOwnerUsername(request.getOwnerUsername());
            return repo.save(existing); // Save updated item
        });
    }

    // 🔹 Delete item by ID
    public boolean delete(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true; // Successfully deleted
        }
        return false; // Item not found
    }
}