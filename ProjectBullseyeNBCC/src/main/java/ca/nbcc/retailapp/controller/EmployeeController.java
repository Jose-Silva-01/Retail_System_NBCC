package ca.nbcc.retailapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ca.nbcc.retailapp.model.Employee;
import ca.nbcc.retailapp.service.DepartmentService;
import ca.nbcc.retailapp.service.UserService;

@Controller
public class EmployeeController {

	private UserService uS;
	
	private ApplicationContext ctx;
	
	private DepartmentService dS;
	
	@Autowired
	public EmployeeController(UserService uS, ApplicationContext ctx, DepartmentService dS) {
		super();
		this.uS = uS;
		this.ctx = ctx;
		this.dS = dS;
	}
	
	@GetMapping("/showUsers")
	public String usersPage(Model model, String lName, @CurrentSecurityContext(expression="authentication?.name")
    String username) {
		
		Employee currentEmployee = uS.findEmployeeByUsername(username);
		
		List<Employee> eList = new ArrayList<>();
		
		if(lName != null) {
			eList = uS.searchEmployeeByName(lName);
		}
		else {
			eList = uS.getAllEmployees();
		}

		model.addAttribute("employeeList", eList);
		model.addAttribute("currentEmployee", currentEmployee);
		return "userPage";
	}

	@GetMapping("addNewEmployee")
	public String toAddEmployee(Model model) throws Exception {
		
		model.addAttribute("employeeToAdd", new Employee());
		model.addAttribute("employeeList", uS.getAllEmployees());
		return "add-employee";
	}
	
	@PostMapping("processAddEmployee")
	public String processAddInventoryProduct(Employee employeeToAdd, RedirectAttributes ra) throws Exception {
		
		Employee addedEmployee = uS.addNewEmployee(employeeToAdd);
		
		return "redirect:/browse-employee/" + addedEmployee.getId();
	}
	
	@GetMapping("/browse-employee/{eId}")
	public String viewEmployee(@PathVariable("eId") long eMID, Model model) {
		Employee eToView = null;
		Long eMID_LONG = eMID;

		try {
			eToView = uS.findEmployeeById(eMID_LONG);
		} catch (Exception e) {
			e.printStackTrace();
		}

		model.addAttribute("employeeToView", eToView);
		return "browse-employee";
	}

	@GetMapping("/edit-employee/{eId}")
	public String toEditEmployee(@PathVariable("eId") long eMID, Model model) {

		Long eMID_LONG = eMID;
		Employee eToEdit = null;

		try {
			
			eToEdit = uS.findEmployeeById(eMID_LONG);
			model.addAttribute("eToEdit", eToEdit);
			model.addAttribute("departmentList", dS.getAllDepartmentsFromTheSameStore(eToEdit.getDept().getStore()));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "edit-employee";
	}

	@PostMapping("/updateEmployee")
	public String processUpdateEmployee(Employee eToEdit) {

		uS.updateEmployee(eToEdit);
		
		return "redirect:/browse-employee/" + eToEdit.getId();
	}
	
	@GetMapping("/deleteEmployee/{eId}")
	public String deleteEmployeeById(@PathVariable("eId") long eMID, Model model){
		
		Long eMID_LONG = eMID;

		try {
			uS.deleteEmployee(eMID_LONG);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "redirect:/showUsers";
	}
}
