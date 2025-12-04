package za.tomvuma.lostandfoundhub.repository;

// Import Spring Data JPA base repository
import org.springframework.data.jpa.repository.JpaRepository;
import za.tomvuma.lostandfoundhub.entity.Item;

// Repository interface for Item entity
// JpaRepository provides built-in CRUD methods (save, findAll, findById, deleteById, etc.)
public interface ItemRepository extends JpaRepository<Item, Long> {
}
// No extra methods yet — uses default JPA methods