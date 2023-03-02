package ca.nbcc.retailapp.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import ca.nbcc.retailapp.model.Employee;
import ca.nbcc.retailapp.model.employeeDetails;
import ca.nbcc.retailapp.repo.EmployeeJpaRepo;

@Service
public class LoginService {
	
	private EmployeeJpaRepo er;
	
	@Autowired
	public LoginService(EmployeeJpaRepo er) {
		super();
		this.er=er;
	}
	
	public Employee getEmployeeByUsername(String username) {
		Employee emp = er.findByusername(username);
		if(emp==null) {
			System.out.println("Username not found");
		}
		return emp;
	}
	
	public employeeDetails getEmployeeByUserName(String username) {
		Employee emp = er.findByusername(username);
		if(emp == null) {
			System.out.println("Username not found");
		}
		return new employeeDetails(emp);
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
	
	
	public void saveEmployee(@Valid Employee user) {
		er.save(user);
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
}
