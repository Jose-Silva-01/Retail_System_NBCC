package ca.nbcc.retailapp.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.nbcc.retailapp.model.Employee;
import ca.nbcc.retailapp.model.employeeDetails;
import ca.nbcc.retailapp.repo.EmployeeJpaRepo;

@Service
public class UserService {

	private EmployeeJpaRepo er;
	
	@Autowired
	public UserService(EmployeeJpaRepo er) {
		super();
		this.er = er;
	}
	
	public Employee findEmployeeByUsername(String username) {
		
		return er.findByusername(username);
	}
	
	public Employee findEmployeeById(Long eMID_LONG) throws Exception{
		if(er.findById((long)eMID_LONG).isPresent()) {
			return er.findById((long)eMID_LONG).get();
		}
		else if(er.findById((long)eMID_LONG).isEmpty()) {
			throw new Exception("Employee not found: ID " + eMID_LONG);
		}
		return null;
	}
	
	
	public List<Employee> getAllEmployees(){
		return er.findAll();
	}
	
	public Employee addNewEmployee(Employee e) {
		return er.save(e);
	}

	public Employee updateEmployee(Employee e) {
		return er.save(e);
	}

	public List<Employee> searchEmployeeByName(String uName) {
		return er.findByLastNameContainingIgnoreCase(uName);
	}

	public void deleteEmployee(Long eMID_LONG) {
			
		er.deleteById(eMID_LONG);
	}
}
