package za.tomvuma.lostandfoundhub.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.tomvuma.lostandfoundhub.dto.ItemRequest;
import za.tomvuma.lostandfoundhub.entity.Item;
import za.tomvuma.lostandfoundhub.repository.ItemRepository;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Items", description = "Operations related to lost and found items")
@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemRepository repo;

    public ItemController(ItemRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public ResponseEntity<Item> create(@Valid @RequestBody ItemRequest request) {
        Item item = new Item();
        item.setTitle(request.getTitle());
        item.setDescription(request.getDescription());
        item.setCategory(request.getCategory());
        item.setStatus(Item.Status.valueOf(request.getStatus())); // Convert string to enum
        item.setLocation(request.getLocation());
        item.setContactInfo(request.getContactInfo());
        item.setOwnerUsername(request.getOwnerUsername());

        return ResponseEntity.ok(repo.save(item));
    }

    @GetMapping
    public List<Item> findAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> findOne(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> update(@PathVariable Long id, @RequestBody ItemRequest request) {
        return repo.findById(id).map(existing -> {
            existing.setTitle(request.getTitle());
            existing.setDescription(request.getDescription());
            existing.setCategory(request.getCategory());
            existing.setStatus(Item.Status.valueOf(request.getStatus()));
            existing.setLocation(request.getLocation());
            existing.setContactInfo(request.getContactInfo());
            existing.setOwnerUsername(request.getOwnerUsername());
            return ResponseEntity.ok(repo.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}