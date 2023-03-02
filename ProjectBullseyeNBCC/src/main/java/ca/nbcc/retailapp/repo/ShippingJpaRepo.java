package ca.nbcc.retailapp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.nbcc.retailapp.model.Shipping;

public interface ShippingJpaRepo extends JpaRepository<Shipping, Long>{
	
	List<Shipping> findByOrderId(Long oId);
	
	List<Shipping> findByOrderById();
	
	List<Shipping> findAllByOrderByLeaveDateAsc();
	
	List<Shipping> findAllByOrderByArriveDateAsc();
}
