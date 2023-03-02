package ca.nbcc.retailapp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.nbcc.retailapp.model.Product;

public interface ProductJpaRepo extends JpaRepository<Product, Long>{
	
	List<Product> findByNameContainingIgnoreCase(String name);
	
	List<Product> findByOrderById();
	
	List<Product> findByOrderByPrice();
	
	List<Product> findByOrderByName();
}
