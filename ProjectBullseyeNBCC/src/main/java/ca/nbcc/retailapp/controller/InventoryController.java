package ca.nbcc.retailapp.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ca.nbcc.retailapp.model.Employee;
import ca.nbcc.retailapp.model.Department;
import ca.nbcc.retailapp.model.Inventory;
import ca.nbcc.retailapp.model.InventoryReport;
import ca.nbcc.retailapp.model.Order;
import ca.nbcc.retailapp.model.OrderDetails;
import ca.nbcc.retailapp.model.OrderReport;
import ca.nbcc.retailapp.model.Product;
import ca.nbcc.retailapp.model.Report;
import ca.nbcc.retailapp.model.Shipping;
import ca.nbcc.retailapp.model.ShippingStatus;
import ca.nbcc.retailapp.model.Store;
import ca.nbcc.retailapp.model.Suplier;
import ca.nbcc.retailapp.service.DepartmentService;
import ca.nbcc.retailapp.service.InventoryService;
import ca.nbcc.retailapp.service.OrderDetailsService;
import ca.nbcc.retailapp.service.OrderService;
import ca.nbcc.retailapp.service.ProductService;
import ca.nbcc.retailapp.service.ReportService;
import ca.nbcc.retailapp.service.ShippingService;
import ca.nbcc.retailapp.service.StoreService;
import ca.nbcc.retailapp.service.SupplierService;
import ca.nbcc.retailapp.service.UserService;

@Controller
public class InventoryController {

	ApplicationContext ctx;

	private ProductService pS;
	private SupplierService sS;
	private InventoryService iS;
	private StoreService stoS;
	private ShippingService shiS;
	private ReportService rS;
	private OrderService oS;
	private OrderDetailsService odS;
	private UserService uS;

	public InventoryController() {
		super();
	}

	@Autowired
	public InventoryController(ApplicationContext ctx, ProductService pS, SupplierService sS, InventoryService iS,
			StoreService stoS, ShippingService shiS, ReportService rS, OrderService oS, OrderDetailsService odS, UserService uS, DepartmentService dS) {
		super();
		this.ctx = ctx;
		this.pS = pS;
		this.sS = sS;
		this.iS = iS;
		this.stoS = stoS;
		this.shiS = shiS;
		this.rS = rS;
		this.oS = oS;
		this.odS = odS;
		this.uS = uS;
		this.dS = dS;
	}

	public InventoryController(ProductService pS, SupplierService sS, InventoryService iS, StoreService stoS, DepartmentService dS) {
		super();
		this.pS = pS;
		this.sS = sS;
		this.iS = iS;
		this.stoS = stoS;
		this.dS = dS;
	}

	public ProductService getpS() {
		return pS;
	}

	public void setpS(ProductService pS) {
		this.pS = pS;
	}

	public SupplierService getsS() {
		return sS;
	}

	public void setsS(SupplierService sS) {
		this.sS = sS;
	}

	public InventoryService getiS() {
		return iS;
	}

	public void setiS(InventoryService iS) {
		this.iS = iS;
	}

	public StoreService getStoS() {
		return stoS;
	}

	public void setStoS(StoreService stoS) {
		this.stoS = stoS;
	}
	
	@GetMapping("/") 
	public String toIndex() {
		return "redirect:/showInventory";
	}

	@GetMapping("addNewSupplier")
	public String toAddSupplier(Model model) {

		model.addAttribute("supplierToAdd", new Suplier());
		return "add-supplier";
	}

	@GetMapping("addNewProduct")
	public String toAddProduct(Model model) {

		model.addAttribute("productToAdd", new Product());
		model.addAttribute("suppliersList", sS.getAllSuppliers());

		return "add-product";
	}

	@GetMapping("addNewInventory")
	public String toAddInventory(Model model) throws Exception {
		
		model.addAttribute("inventoryToAdd", new Inventory());
		model.addAttribute("productsList", pS.getAllProducts());
		return "add-inventory";
	}

	@PostMapping("addProduct")
	public Product addProductTest(Product p) {

		return pS.addNewProduct(p);
	}
	
	
	public Department addDepartmentTest(Department department) {
		return dS.addNewDepartment(department);
	}

	@PostMapping("addSupplier")
	public Suplier addSupplierTest(Suplier p) {

		return sS.addNewSupplier(p);
	}

	@PostMapping("addInventory")
	public void addInventoryTest(Inventory i) {

		iS.addNewInventory(i);
	}


	@PostMapping("addStoreTesting")
	public Store addStoreTest(Store s) {
		Store store = stoS.addNewStore(s);
		
		return store;
	}
  
	@PostMapping("processAddProduct")
	public String processAddProduct(Product productToAdd, RedirectAttributes ra) {

		Product addedProduct = pS.addNewProduct(productToAdd); // MANAGED REFERENCE ---> DB

		return "redirect:/browse-product/" + addedProduct.getId();
	}
	
	@PostMapping("processAddSupplier")
	public String processAddSupplier(Suplier supplierToAdd, RedirectAttributes ra) {

		Suplier addedSuplier = sS.addNewSupplier(supplierToAdd);

		return "redirect:/browse-supplier/" + addedSuplier.getId();
	}

	@PostMapping("processAddInventoryProduct")
	public String processAddInventoryProduct(Inventory inventoryToAdd, RedirectAttributes ra, @CurrentSecurityContext(expression="authentication?.name")
    String username) throws Exception {
		
		Employee loggedEmployee = uS.findEmployeeByUsername(username);
		Store currentStore = loggedEmployee.getDept().getStore(); 
		
		inventoryToAdd.setStore(currentStore);
		
		for(var inv : iS.findAllInventoryProductsByStore(currentStore)) {
			if (inventoryToAdd.getProduct().equals(inv.getProduct())) {
				ra.addFlashAttribute("errorMessage", "Product already present on Inventory");
				return "redirect:/addNewInventory";
			}
		}
		
		Inventory addedInventory = iS.addNewInventory(inventoryToAdd);
		
		System.out.println(addedInventory);
		
		return "redirect:/browse-inventory/" + addedInventory.getId();
	}
	
	@PostMapping("/filterProducts")
	public String orderProducts(Model model, @RequestParam("orderBy") String orderBy) {
		
		List<Product> pList = null;
		
		if(orderBy.equals("name")) {
			pList = pS.orderByName();

		} else if (orderBy.equals("price")) {
			pList = pS.orderByPrice();

		} else if (orderBy.equals("id")){
			pList = pS.orderById();
		}
		
		model.addAttribute("productList", pList);
		model.addAttribute("orderBySelected", orderBy);
		
		return "products-display";
	}

	@GetMapping("/showProducts")
	public String showProducts(Model model, String pName) {
		
		List<Product> pList = new ArrayList<>();
		
		if(pName != null) {
			pList = pS.searchProductByName(pName);
		}
		else {
			pList = pS.getAllProducts();
		}

		model.addAttribute("productList", pList);
		return "products-display";
	}
	
	@PostMapping("/filterSuppliers")
	public String orderSuppliers(Model model, @RequestParam("orderBy") String orderBy) {
		
		List<Suplier> sList = null;
		
		if(orderBy.equals("name")) {
			sList = sS.orderByName();
		} else if (orderBy.equals("id")){
			sList = sS.orderById();
		}
		
		model.addAttribute("supplierList", sList);
		model.addAttribute("orderBySelected", orderBy);
		
		return "suppliers-display";
	}

	@GetMapping("showSuppliers")
	public String showSupplier(Model model, String sName) {

		List<Suplier> sList = new ArrayList<>();
		
		if(sName != null) {
			sList = sS.searchSupplierByName(sName);
		}
		else {
		sList = sS.getAllSuppliers();
		}
		
		model.addAttribute("supplierList", sList);
		return "suppliers-display";
	}

	@PostMapping("/filterInventory")
	public String orderInventory(Model model, @RequestParam("orderBy") String orderBy, @CurrentSecurityContext(expression="authentication?.name")
    String username) {
		
		Employee loggedEmployee = uS.findEmployeeByUsername(username);
		Store currentStore = loggedEmployee.getDept().getStore();
		
		List<Inventory> iList = iS.findAllInventoryProductsByStore(currentStore);
		
		if(orderBy.equals("name")) {
			iList = iS.orderByProductName(currentStore);
		} else if (orderBy.equals("quantity")) {
			iList = iS.orderByQuantity(currentStore);
		} else if (orderBy.equals("id")){
			iList = iS.orderById(currentStore);
		}
		
		model.addAttribute("currentStore", currentStore);
		model.addAttribute("inventoryList", iList);
		model.addAttribute("orderBySelected", orderBy);
		
		return "inventory-display";
	}
	
	@GetMapping("/showInventory")
	public String showInventory(Model model, String pName, @CurrentSecurityContext(expression="authentication?.name")
    String username) {
		
		Employee loggedEmployee = uS.findEmployeeByUsername(username);
		Store currentStore = loggedEmployee.getDept().getStore();
		
		List<Inventory> iList = new ArrayList<>();
		
		if(pName != null) {
			iList = iS.searchInventoryByProductName(currentStore, pName);
		}
		else {
		 iList = iS.findAllInventoryProductsByStore(currentStore);
		}
		List<Store> stoList = new ArrayList<>();
		
		for (var i : iList) {
			stoList.add(i.getStore());
		}

		model.addAttribute("currentStore", currentStore);
		model.addAttribute("inventoryList", iList);
		return "inventory-display";
	}

	@GetMapping("/browse-product/{pId}")
	public String viewProduct(@PathVariable("pId") long pMID, Model model) {
		Product pToView = null;
		Long pMID_LONG = pMID;

		try {
			pToView = pS.findProductById(pMID_LONG);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// add to model
		model.addAttribute("productToView", pToView);
		// go to view page
		return "browse-product";
	}

	@GetMapping("/edit-product/{pId}")
	public String toEditProduct(@PathVariable("pId") long pMID, Model model) {

		Long pMID_LONG = pMID;
		Product pToEdit = null;

		try {
			pToEdit = pS.findProductById(pMID_LONG);
			model.addAttribute("pToEdit", pToEdit);
			model.addAttribute("suppliersList", sS.getAllSuppliers());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "edit-product";
	}

	@PostMapping("/updateProduct")
	public String processUpdateProduct(Product pToEdit) {

		pS.updateProduct(pToEdit);
		
		return "redirect:/browse-product/" + pToEdit.getId();
	}
	
	@GetMapping("/browse-inventory/{iId}")
	public String viewInventoryProduct(@PathVariable("iId") long iMID, Model model) {
		Inventory iToView = null;
		Long iMID_LONG = iMID;

		try {
			iToView = iS.findInventoryById(iMID_LONG);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// add to model
		model.addAttribute("inventoryToView", iToView);
		// go to view page
		return "browse-inventory";
	}

	@GetMapping("/edit-inventory/{iId}")
	public String toEditInventoryProduct(@PathVariable("iId") long iMID, Model model) {

		Long iMID_LONG = iMID;
		Inventory iToEdit = null;

		try {
			
			iToEdit = iS.findInventoryById(iMID_LONG);
			model.addAttribute("iToEdit", iToEdit);
			model.addAttribute("productList", pS.getAllProducts());
			model.addAttribute("storesList", stoS.getAllStores());
			System.out.println("Hello: " + stoS.getAllStores());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "edit-inventory";
	}

	@PostMapping("/updateInventory")
	public String processUpdateInventory(Inventory iToEdit) {

		iS.updateInventory(iToEdit);
		
		return "redirect:/browse-inventory/" + iToEdit.getId();
	}
	
	@GetMapping("/browse-supplier/{sId}")
	public String viewSupplier(@PathVariable("sId") long sMID, Model model) {
		Suplier sToView = null;
		Long sMID_LONG = sMID;

		try {
			sToView = sS.findSuplierById(sMID_LONG);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// add to model
		model.addAttribute("supplierToView", sToView);
		// go to view page
		return "browse-supplier";
	}

	@GetMapping("/edit-supplier/{sId}")
	public String toEditSupplier(@PathVariable("sId") long sMID, Model model) {

		Long sMID_LONG = sMID;
		Suplier sToEdit = null;

		try {
			sToEdit = sS.findSuplierById(sMID_LONG);
			model.addAttribute("sToEdit", sToEdit);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "edit-supplier";
	}

	@PostMapping("/updateSupplier")
	public String processUpdateSupplier(Suplier sToEdit) {

		sS.updateSupplier(sToEdit);
		
		return "redirect:/browse-supplier/" + sToEdit.getId();
	}
	
	@GetMapping("/deleteProduct/{pId}")
	public String deleteProductById(@PathVariable("pId") long pMID, Model model){
		
		Long pMID_LONG = pMID;

		try {
			pS.deleteProduct(pMID_LONG);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "redirect:/showProducts";
	}
	
	@GetMapping("/deleteInventory/{iId}")
	public String deleteInventoryById(@PathVariable("iId") long iMID, Model model){
		
		Long iMID_LONG = iMID;

		try {
			iS.deleteInventory(iMID_LONG);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "redirect:/showInventory";
	}
	
	@GetMapping("/deleteSupplier/{sId}")
	public String deleteSupplierById(@PathVariable("sId") long sMID, Model model){
		
		Long sMID_LONG = sMID;

		try {
			sS.deleteSupplier(sMID_LONG);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "redirect:/showSuppliers";
	}
	
	@GetMapping("/createInventoryReport")
	public String createInventoryReport(@CurrentSecurityContext(expression="authentication?.name")
    String username) throws ParseException {
		
		Employee loggedEmployee = uS.findEmployeeByUsername(username);
		Store currentStore = loggedEmployee.getDept().getStore();
		
		List<Inventory> currentInventory = iS.findAllInventoryProductsByStore(currentStore);
		List<Inventory> bellowReordelLevel = iS.getInventoryLowerThanReorderLevel(currentStore);
		
		LocalDate date = LocalDate.now();
		
		String title = "Inventory Report for " + date + " at " + currentStore.getCity();
		
		String description = "Current Inventory Products: \n\n";
		
		double totalAmount = 0;
		
		for (var inv : currentInventory) {
			description += "Name: " + inv.getProduct().getName() + 
					", Price: $" + inv.getProduct().getPrice() +
					", Quantity: " + inv.getQuantity() +
					" \n ";
			
			totalAmount += (inv.getProduct().getPrice() * inv.getQuantity());
		}
		
		String text = "Total Current Amount In Store: $" + totalAmount +
				"\nDate: " + date;
		
		String additionalInfo = "Products Lower Than Reorder Level: \n\n";
		
		for (var invB : bellowReordelLevel) {
			additionalInfo += "Inventory Product: " + invB.getProduct().getName() + 
					", Price: $" + invB.getProduct().getPrice() +
					", Quantity: " + invB.getQuantity() +
					" \n ";
		}
		
		InventoryReport inventoryReport = new InventoryReport(
				title,
				date,
				description,
				text,
				additionalInfo,
				null
				);
		
		rS.addNewReport(inventoryReport);
		
		return "redirect:/reportDetails/" + inventoryReport.getId();
	}
	
	@GetMapping("/createAutomaticOrder")
	public String createAutomaticOrder(@CurrentSecurityContext(expression="authentication?.name")
    String username) throws Exception {
		
		Employee loggedEmployee = uS.findEmployeeByUsername(username);
		Store currentStore = loggedEmployee.getDept().getStore();
		
		List<Inventory> bellowReordelLevel = iS.getInventoryLowerThanReorderLevel(currentStore);
		
		Order automaticOrder = new Order(null, null, null, LocalDate.now(), null, null);
		oS.addNewOrder(automaticOrder);
		
		List<OrderDetails> oDetailsList = new ArrayList<>();
		
		for(var inv : bellowReordelLevel) {
			OrderDetails oDetailToAdd = new OrderDetails(automaticOrder, inv.getProduct(), inv.getProduct().getReorderLevel());
			
			oDetailsList.add(oDetailToAdd);
			odS.addNewOrderDetails(oDetailToAdd);
		}
		
		automaticOrder.setOrdDetails(oDetailsList);
		oS.updateOrder(automaticOrder);
		
		Order oReport = oS.findOrderById(automaticOrder.getId());
		
		Report orderReportToCreate = new OrderReport(oReport);	

		orderReportToCreate.setDate(LocalDate.now());

		orderReportToCreate.setTitle("Order Report for Order #" + automaticOrder.getId());

		rS.addNewReport(orderReportToCreate);
		
		LocalDate leaveDate = LocalDate.now(); // suppose the leave date is the order date
		LocalDate arriveDate = leaveDate.plusDays((long) 7); //arrive date would be the current date plus one week
		Shipping orderShipping = new Shipping(leaveDate, arriveDate, ShippingStatus.PENDING, oReport);
		
		shiS.addNewShipping(orderShipping);
		
		return "redirect:/toOrderDetails/" + automaticOrder.getId();
	}

	
	
	
}
