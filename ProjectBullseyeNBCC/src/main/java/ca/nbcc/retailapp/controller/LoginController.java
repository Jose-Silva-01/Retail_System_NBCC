package ca.nbcc.retailapp.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ca.nbcc.retailapp.model.Employee;

import ca.nbcc.retailapp.service.LoginService;

@Controller
public class LoginController {

	@Autowired
	private LoginService lc;
	
	@Autowired
	public LoginController(LoginService lc) {
		super();
		this.lc=lc;
	}
	
	@RequestMapping(value="/")
	public String showIndexPage() {
		return "login";
	}
	
	
	@PostMapping("addEmployee")
	public void addEmployeeTest(Employee e) {
		lc.addNewEmployee(e);
		
	}
	
	@RequestMapping(value="/login", method={RequestMethod.GET, RequestMethod.POST})
	public String showLogInPage(Model model, @RequestParam(value="error", required = false) String error,
			@RequestParam(value="logout", required = false) String logout
			){
		
		if(error != null){
			model.addAttribute("error", "Invalid Credentials provided.");
		}
		if(logout != null){
			model.addAttribute("message", "Logged out successfully.");
		}
		System.out.println("Hi");
		model.addAttribute("user", new Employee());
		return "login";
	}
	
	@RequestMapping(value="/register")
	public String showRegisterPage(Model model, @Valid Employee user, BindingResult br, HttpSession session){
		model.addAttribute("user", new Employee());
		return "register";
	}
	
	@RequestMapping(value="/home")
	public String showHome(Model model, @Valid Employee user) {
		Employee currentUser = lc.getEmployeeByUsername(user.getUsername().trim());
		int i = 1;
		for(Employee e : lc.getAllEmployees()) {
			System.out.println(i + " - " + e);
			i++;
		}
		if(currentUser.getImgURI() !=null) {
			model.addAttribute("img","/images" + currentUser.getImgURI());
		}
		return "home";
	}
	
	@RequestMapping(value="/processRegister", method=RequestMethod.POST)
	public String processRegister(
			HttpSession session,
			Model model,
			@Valid Employee user,
			BindingResult br) {
		if(br!=null) {
			if(br.hasErrors()) {
				return "login";
			}
		}
		

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		Employee foundUser = lc.getEmployeeByUsername(user.getUsername().trim());
		if(foundUser != null) {
			model.addAttribute("errorMsg", "Username already exists");
			return "register";
		}
		else {
			user.setPassword(encoder.encode(user.getPassword().trim()));
			lc.saveEmployee(user);
			
			return "redirect:/login";
		}
	}

	
	
	
}
