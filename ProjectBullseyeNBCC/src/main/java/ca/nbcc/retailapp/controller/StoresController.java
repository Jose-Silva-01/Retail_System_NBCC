package ca.nbcc.retailapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.nbcc.retailapp.model.Department;
import ca.nbcc.retailapp.model.Product;
import ca.nbcc.retailapp.model.Store;
import ca.nbcc.retailapp.model.Suplier;
import ca.nbcc.retailapp.service.DepartmentService;
import ca.nbcc.retailapp.service.StoreService;

@Controller
public class StoresController {

	ApplicationContext ctx;
	
	StoreService stoS;
	DepartmentService dS;
	
	public StoresController() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Autowired
	public StoresController(ApplicationContext ctx, StoreService stoS, DepartmentService dS) {
		super();
		this.ctx = ctx;
		this.stoS = stoS;
		this.dS = dS;
	}
	
	public StoresController(StoreService stoS, DepartmentService dS) {
		super();
		this.stoS = stoS;
		this.dS = dS;
	}

	public StoreService getStoS() {
		return stoS;
	}

	public void setStoS(StoreService stoS) {
		this.stoS = stoS;
	}

	@PostMapping("addStore")
	public Store addStoreTest(Store s) {

		return stoS.addNewStore(s);
	}
	
	@PostMapping("addDepartment")
	public Department addDepartmentTest(Department d) {

		return dS.addNewDepartment(d);
	}
	
	@GetMapping("findStore")
	public Store findStoreById(Long id) {
		return stoS.findStoreById(id);
	}
	
	@GetMapping("findStores")
	public List<Store> getAllStores() {
		return stoS.getAllStores();
	}
	
	@PostMapping("/filterStores")
	public String orderStores(Model model, @RequestParam("orderBy") String orderBy) {
		
		List<Store> sList = null;
		
		if(orderBy.equals("city")) {
			sList = stoS.orderByCity();
		} else if (orderBy.equals("id")){
			sList = stoS.orderById();
		}
		
		model.addAttribute("storeList", sList);
		model.addAttribute("orderBySelected", orderBy);
		
		return "stores-display";
	}
	
	@GetMapping("showStores")
	public String showStores(Model model, String sName) {

		List<Store> sList = new ArrayList<>();
		
		if(sName != null) {
			sList = stoS.searchStoreByName(sName);
		}
		else {
			sList = stoS.getAllStores();
		}
		
		model.addAttribute("storeList", sList);
		return "stores-display";
	}
	
	@GetMapping("/browse-store/{sId}")
	public String viewStore(@PathVariable("sId") long sMID, Model model) {
		Store sToView = null;
		Long sMID_LONG = sMID;

		try {
			sToView = stoS.findStoreById(sMID_LONG);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// add to model
		model.addAttribute("storeToView", sToView);
		// go to view page
		return "browse-store";
	}

	@GetMapping("/edit-store/{sId}")
	public String toEditStore(@PathVariable("sId") long sMID, Model model) {

		Long sMID_LONG = sMID;
		Store sToEdit = null;

		try {
			sToEdit = stoS.findStoreById(sMID_LONG);
			model.addAttribute("sToEdit", sToEdit);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "edit-store";
	}

	@PostMapping("/updateStore")
	public String processUpdateStore(Store sToEdit) {

		stoS.updateStore(sToEdit);
		
		return "redirect:/browse-store/" + sToEdit.getId();
	}
}
