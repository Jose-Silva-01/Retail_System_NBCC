package ca.nbcc.retailapp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.nbcc.retailapp.model.Product;
import ca.nbcc.retailapp.model.Suplier;

public interface SupplierJpaRepo extends JpaRepository<Suplier, Long>{

	List<Suplier> findByNameContainingIgnoreCase(String name);

	List<Suplier> findByOrderByName();
	
	List<Suplier> findByOrderById();
}
