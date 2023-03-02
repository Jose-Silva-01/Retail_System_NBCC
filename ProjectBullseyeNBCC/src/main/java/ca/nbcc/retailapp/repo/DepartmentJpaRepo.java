package ca.nbcc.retailapp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.nbcc.retailapp.model.Department;
import ca.nbcc.retailapp.model.Store;

public interface DepartmentJpaRepo extends JpaRepository<Department, Long>{

	List<Department> findByNameContainingIgnoreCase(String searchTerm);

	List<Department> findAllByStore(Store s);

}
