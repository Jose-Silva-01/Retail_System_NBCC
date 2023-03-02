package ca.nbcc.retailapp.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.nbcc.retailapp.model.Department;
import ca.nbcc.retailapp.model.Store;
import ca.nbcc.retailapp.repo.DepartmentJpaRepo;

@Service
public class DepartmentService {

	private DepartmentJpaRepo dRepo;

	@Autowired
	public DepartmentService(DepartmentJpaRepo dRepo) {
		super();
		this.dRepo = dRepo;
	}
	
	public Department addNewDepartment(Department dept) {
		return dRepo.save(dept);
	}

	public List<Department> getAllDepartments() {
		return dRepo.findAll();
	}
	
	public List<Department> getAllDepartmentsFromTheSameStore(Store s) {
		return dRepo.findAllByStore(s);
	}
	
	public Department findDepartmentById(Long dMID_LONG) {
		
		if(dRepo.findById((long)dMID_LONG).isPresent()) {
			return dRepo.findById((long)dMID_LONG).get();
		}
		else if (dRepo.findById((long)dMID_LONG).isEmpty()){
			
		}
		return null;
	}
	
	public List<Department> searchDepartmentByName(String searchTerm) {
		
		return dRepo.findByNameContainingIgnoreCase(searchTerm);
	}

	public void updateDepartment(Department dToEdit) {
		
		dRepo.save(dToEdit);
	}
}
