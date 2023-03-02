package ca.nbcc.retailapp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.nbcc.retailapp.model.Order;


public interface OrderJpaRepo extends JpaRepository<Order, Long>{
	
	public List<Order> findByOrderByIdAsc();
	public List<Order> findByOrderByIdDesc();
	public List<Order> findByIdOrderByIdAsc(String id);
	public List<Order> findByIdOrderByIdDesc(String id);
	
	public List<Order> findByOrderById();
	
}