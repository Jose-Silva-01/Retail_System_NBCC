package ca.nbcc.retailapp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import ca.nbcc.retailapp.model.Employee;
import ca.nbcc.retailapp.model.Suplier;

public interface EmployeeJpaRepo extends JpaRepository<Employee, Long> {
	
	public Employee findByusername(@Param("username") String username);

	public List<Employee> findByLastNameContainingIgnoreCase(String lName);
}
