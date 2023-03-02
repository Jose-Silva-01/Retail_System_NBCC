package ca.nbcc.retailapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.nbcc.retailapp.model.OrderDetails;

public interface OrderDetailsJpaRepo extends JpaRepository<OrderDetails, Long>{

}
