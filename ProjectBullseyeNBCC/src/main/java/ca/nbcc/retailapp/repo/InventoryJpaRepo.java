package ca.nbcc.retailapp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ca.nbcc.retailapp.model.Inventory;
import ca.nbcc.retailapp.model.Store;

public interface InventoryJpaRepo extends JpaRepository<Inventory, Long>{

	@Query(value="SELECT i FROM Inventory i JOIN i.product p WHERE i.store = :#{#store} AND lower(p.name) LIKE lower(concat('%', :pName, '%'))")
	List<Inventory> findByProductNameContainingIgnoreCase(@Param("store") Store store, @Param("pName") String pName);
	
	@Query(value="SELECT i FROM Inventory i WHERE i.store = :#{#store} ORDER BY i.id")
	List<Inventory> findByOrderById(@Param("store") Store store);
	
	@Query(value="SELECT i FROM Inventory i WHERE i.store = :#{#store} ORDER BY i.quantity")
	List<Inventory> findByOrderByQuantity(@Param("store") Store store);
	
	@Query(value="SELECT i FROM Inventory i JOIN i.product p WHERE i.store = :#{#store} ORDER BY p.name")
	List<Inventory> findByOrderByProductName(@Param("store") Store store);
	
	@Query(value="SELECT i FROM Inventory i JOIN i.product p WHERE i.quantity <= p.reorderLevel AND i.store = :#{#store}")
	List<Inventory> getInventoryLowerThanReorderLevel(@Param("store") Store store);

	List<Inventory> findByStore(Store s);
}
