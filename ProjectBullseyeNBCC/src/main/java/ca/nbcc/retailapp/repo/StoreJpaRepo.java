package ca.nbcc.retailapp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.nbcc.retailapp.model.Store;

public interface StoreJpaRepo extends JpaRepository<Store, Long>{

	List<Store> findByCityContainingIgnoreCase(String name);

	List<Store> findByOrderById();
	
	List<Store> findByOrderByCity();
}