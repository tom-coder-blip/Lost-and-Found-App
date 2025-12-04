package za.tomvuma.lostandfoundhub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import za.tomvuma.lostandfoundhub.repository.ItemRepository;
import za.tomvuma.lostandfoundhub.dto.ItemRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.validation.BindingResult;
import za.tomvuma.lostandfoundhub.entity.Item;
import jakarta.validation.Valid;

@Controller // Marks this class as a web controller (returns HTML views)
public class ViewController {
    private final ItemRepository repo;

    public ViewController(ItemRepository repo) { this.repo = repo; }

    @GetMapping("/") // Home page showing all items
    public String home(Model model) {
        model.addAttribute("items", repo.findAll()); // Add items to the view
        return "index"; // Render index.html
    }

    @GetMapping("/report") // Show report form
    public String report(Model model) {
        model.addAttribute("itemRequest", new ItemRequest()); // Empty form object
        return "report"; // Render report.html
    }

    @GetMapping("/items/{id}") // Show item details
    public String details(@PathVariable Long id, Model model) {
        model.addAttribute("item", repo.findById(id).orElse(null)); // Add item to view
        return "details"; // Render details.html
    }

    @PostMapping("/report") // Submit new item report
    public String submitReport(@ModelAttribute @Valid ItemRequest itemRequest,
                               BindingResult result) {
        if (result.hasErrors()) {
            return "report"; // Show form again if validation fails
        }

        // Convert DTO to entity
        Item item = new Item();
        item.setTitle(itemRequest.getTitle());
        item.setDescription(itemRequest.getDescription());
        item.setCategory(itemRequest.getCategory());
        item.setStatus(Item.Status.valueOf(itemRequest.getStatus().toUpperCase()));
        item.setLocation(itemRequest.getLocation());
        item.setContactInfo(itemRequest.getContactInfo());
        item.setOwnerUsername(itemRequest.getOwnerUsername());

        repo.save(item); // Save item
        return "redirect:/"; // Go back to home page
    }

    @GetMapping("/items/edit/{id}") // Show edit form
    public String editForm(@PathVariable Long id, Model model) {
        Item item = repo.findById(id).orElse(null);
        if (item == null) return "redirect:/"; // Redirect if item not found

        model.addAttribute("item", item);
        return "edit"; // Render edit.html
    }

    @PostMapping("/items/update/{id}") // Submit item update
    public String updateItem(@PathVariable Long id, @ModelAttribute Item updated) {
        repo.findById(id).ifPresent(existing -> {
            // Update fields
            existing.setTitle(updated.getTitle());
            existing.setDescription(updated.getDescription());
            existing.setCategory(updated.getCategory());
            existing.setStatus(updated.getStatus());
            existing.setLocation(updated.getLocation());
            existing.setOwnerUsername(updated.getOwnerUsername());
            existing.setContactInfo(updated.getContactInfo());
            repo.save(existing); // Save updated item
        });
        return "redirect:/items/" + id; // Redirect to item details
    }

    @GetMapping("/items/confirm-delete/{id}") // Show delete confirmation
    public String confirmDelete(@PathVariable Long id, Model model) {
        Item item = repo.findById(id).orElse(null);
        if (item == null) return "redirect:/";
        model.addAttribute("item", item);
        return "confirm-delete"; // Render confirm-delete.html
    }

    @PostMapping("/items/delete/{id}") // Delete item
    public String deleteItem(@PathVariable Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id); // Delete item
        }
        return "redirect:/"; // Go back to home page
    }
}