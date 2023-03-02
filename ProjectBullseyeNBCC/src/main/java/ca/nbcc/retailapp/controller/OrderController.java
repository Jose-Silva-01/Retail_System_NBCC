package ca.nbcc.retailapp.controller;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import ca.nbcc.retailapp.model.Order;
import ca.nbcc.retailapp.model.OrderDetails;
import ca.nbcc.retailapp.model.OrderReport;
import ca.nbcc.retailapp.model.Report;
import ca.nbcc.retailapp.model.Shipping;
import ca.nbcc.retailapp.model.ShippingStatus;
import ca.nbcc.retailapp.model.Product;
import ca.nbcc.retailapp.service.LoginService;
import ca.nbcc.retailapp.service.OrderDetailsService;
import ca.nbcc.retailapp.service.OrderService;
import ca.nbcc.retailapp.service.ProductService;
import ca.nbcc.retailapp.service.ReportService;
import ca.nbcc.retailapp.service.ShippingService;


@Controller
public class OrderController {
	
	private ProductService ps;
	private OrderDetailsService ods;
	private OrderService os;
	private ReportService rs;
	private ShippingService sS;
	private LoginService ls;
	ApplicationContext ctx;
	
	
	public OrderController() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	public OrderController(ProductService ps, OrderDetailsService ods, OrderService os, ReportService rs,
			ShippingService sS, LoginService ls, ApplicationContext ctx) {
		super();
		this.ps = ps;
		this.ods = ods;
		this.os = os;
		this.rs = rs;
		this.sS = sS;
		this.ctx = ctx;
		this.ls=ls;
	}

	public OrderController(ReportService rs, ProductService ps, OrderDetailsService ods , 
			OrderService os, LoginService ls) {
		super();
		this.rs = rs;
		this.ps = ps;
		this.ods = ods;
		this.os = os;
		this.ls=ls;
		
	}
	
	
	public ProductService getps() {
		return ps;
	}

	public void setps(ProductService ps) {
		this.ps = ps;
	}

	public OrderDetailsService getods() {
		return ods;
	}
	public void setods(OrderDetailsService ods) {
		this.ods=ods;
	}
	
	public OrderService getos() {
		return os;
	}
	
	public void setos(OrderService os) {
		this.os=os;
	}
	
	public ReportService getrs() {
		return rs;
	}
	public void setrs(ReportService rs) {
		this.rs=rs;
	}
	public LoginService getls() {
		return ls;
	}
	public void setls(LoginService ls) {
		this.ls=ls;
	}
	
	
	
	@GetMapping("createOrder")
	public String toCreateOrder(Model model) {
		// TO DO: 
		// 1 - Generate Order with employee and date
		Order orderToCreate = new Order();
		orderToCreate.setDate(LocalDate.now());
		os.addNewOrder(orderToCreate); // Not the best tatic, but I hope it'll work
		
		model.addAttribute("orderToCreate", orderToCreate);
		generateProductListAndOrderDetails(model);		
		return "create-order";
	}
	

	@PostMapping("addOrderDetailsToOrder")
	public String processAddProduct(Model model, 
								OrderDetails oDetailToAdd, 
								RedirectAttributes ra) {			
		// get order created
		Order orderToCreate = os.getLastOrder();
		System.out.println(orderToCreate.toString());
		
		List<OrderDetails> oDetailsList;
		
		
		if(orderToCreate.getOrdDetails() == null) {
			System.out.println("First IF");
			oDetailsList = new ArrayList<>();			
			oDetailsList.add(oDetailToAdd);
			orderToCreate.setOrdDetails(oDetailsList);
		}else {
			System.out.println("First ELSE");
			oDetailsList = orderToCreate.getOrdDetails();
			oDetailsList.add(oDetailToAdd);
			orderToCreate.setOrdDetails(oDetailsList);
		}
				
		System.out.println(orderToCreate.getOrdDetails().toString());
		
		if(orderToCreate.getOrdDetails().size() > 0 ) {			
			// Binding orderDetails to order (maybe) it's terrible
			oDetailToAdd.setOrder(orderToCreate);
			ods.addNewOrderDetails(oDetailToAdd);
			os.updateOrder(orderToCreate);
			
			ra.addFlashAttribute("oDetailsList", oDetailsList);
			ra.addFlashAttribute("orderToCreate", orderToCreate);
		}
		System.out.println("passou");
		return "redirect:/calculateOrderTotal";
	}
	
	@GetMapping("/deleteOrderDetail/{pIndex}")
	public String processDeleteOrderDetail(Model model, 
								@PathVariable("pIndex") int pIndex,
								RedirectAttributes ra) throws Exception {		
		Order orderToCreate = os.getLastOrder();
		List<OrderDetails> oDetailsList = orderToCreate.getOrdDetails();
		OrderDetails oDetailToDelete = oDetailsList.get(pIndex);
		
		oDetailsList.remove(pIndex);
		orderToCreate.setOrdDetails(oDetailsList);
		
		ods.deleteOrderDetails(oDetailToDelete);
		os.updateOrder(orderToCreate);
		
		ra.addFlashAttribute("oDetailsList", oDetailsList);
		ra.addFlashAttribute("orderToCreate", orderToCreate);
		
		return "redirect:/calculateOrderTotal";
	}
	
	@GetMapping("cancelOrder")
	public String processCancelOrder(Model model) {
		// Need to delete all records from DB (Order and OrderDetails)
		// Redirect to OrderDisplay page
		Order orderToDelete = os.getLastOrder();
		List<OrderDetails> oDetailsList = orderToDelete.getOrdDetails();
		
		for (OrderDetails orderDetails : oDetailsList) {
			ods.deleteOrderDetails(orderDetails);
		}
		os.deleteOrder(orderToDelete);
		
		return "ordersPage";
	}
	
	@GetMapping("placeOrder")
	public String processPlaceOrder(Model model) {
		// TO DO:
		// 1 - Create report based on orders 
		Order orderCreated = os.getLastOrder();
		Report orderReportToCreate = new OrderReport(orderCreated);	
		

		orderReportToCreate.setDate( LocalDate.now() );

		orderReportToCreate.setTitle("Order Report for Order #" + orderCreated.getId());
		// Need to get the logged user from session
		// orderReportToCreate.setEmployee();
		rs.addNewReport(orderReportToCreate);
		
		//2 - Creating shipping for the orderCreated
		LocalDate leaveDate = LocalDate.now(); // suppose the leave date is the order date
		LocalDate arriveDate = leaveDate.plusDays((long) 7); //arrive date would be the current date plus one week
		Shipping orderShipping = new Shipping(leaveDate, arriveDate, ShippingStatus.PENDING, orderCreated);
		
		sS.addNewShipping(orderShipping);
		
		return "redirect:/showOrders";
	}
	
	@GetMapping("/toOrderDetails/{oId}")	
	public String toOrderDetails(Model model, 
					@PathVariable("oId") Long oId) {
		Optional<Order> orderToDisplay = os.getOrderById(oId);
		
		if(orderToDisplay.isPresent()) {
			model.addAttribute("orderToDisplay", orderToDisplay.get());
			sendTotalValueToFront(model, orderToDisplay.get().getOrdDetails());
		}
		return "orderDetails";
	}
	
	@GetMapping("calculateOrderTotal")
	public String calculateODTotal(Model model, 
			@ModelAttribute("oDetailsList") ArrayList<OrderDetails> oDetailsList,
			@ModelAttribute("orderToCreate") Order orderToCreate) {
		
		sendTotalValueToFront(model, oDetailsList);
		model.addAttribute("oDetailsList", oDetailsList);
		model.addAttribute("orderToCreate", orderToCreate);
		generateProductListAndOrderDetails(model);
		
		return "create-order";
	}
	
	private void sendTotalValueToFront(Model model, List<OrderDetails> oDetailsList) {
		model.addAttribute("orderSubTotal", ods.getOrderSubTotalToDisplay(oDetailsList));
		model.addAttribute("orderTaxes", ods.getOrderTaxesToDisplay(oDetailsList));
		model.addAttribute("orderTotal", ods.getOrderTotalToDisplay(oDetailsList));
		model.addAttribute("totalList", ods.getTotalListToDisplay(oDetailsList));
	}
  
	@GetMapping("/showOrders")
	public String showOrders(Model model, Long oId) {

		List<Order> oList = new ArrayList<>();
		
		
		if(oId != null) {
			oList = os.searchOrderByOrderId(oId);
		}
		else {
			oList = os.getAllOrders();
		}
		
		sendOrdersTotalsToFront(model, oList);
		//oList = os.getAllOrders();
		
		model.addAttribute("orderList", oList);
		
		return "ordersPage";
	}
	

	
	@GetMapping("/showOrderDetails")
	public String showOrderDetails(Model model, Long oId) {

		List<OrderDetails> odList = new ArrayList<>();
		
		if(oId != null) {
			odList = ods.searchOrderByOrderId(oId);
		}
		else {
			odList = ods.getAllOrders();
		}
		//oList = os.getAllOrders();
		
		model.addAttribute("orderDList", odList);
		return "ordersPage";
	}
	
	
	
	@PostMapping("/searchOrders")
	public String oSearch(Model model, @RequestParam("searchInput") String searchInput) {
		System.out.println("test");
		try {
			if(searchInput.isBlank() || searchInput.isEmpty()) {
				return "redirect:/showOrders";
			}else {
				Long inputId = Long.parseLong(searchInput);
				List<Order> oSearchResult = new ArrayList<>();
				
				if(os.findOrderById(inputId) != null) {
					oSearchResult.add(os.findOrderById(inputId));
				}else {
					model.addAttribute("errorMessageSearch", "No Order found with ID # " + searchInput);
				}
				sendOrdersTotalsToFront(model, oSearchResult);
				model.addAttribute("orderList", oSearchResult);
			}			
		}catch (Exception e) {
			model.addAttribute("errorMessageSearch", "No Report found with ID " + searchInput);
			model.addAttribute("orderList", new ArrayList<>());			
		}
		//defaultPageDisplay(model);
		return "ordersPage";
	}
	
	@PostMapping("addOrder")
	public void addOrderTest(Order o) {
		os.addNewOrder(o);
	}

	private void generateProductListAndOrderDetails(Model model) {
		OrderDetails oDetailToAdd = new OrderDetails();		
		List<Product> allProducts = ps.getAllProducts();
				
		
		model.addAttribute("oDetailToAdd", oDetailToAdd);
		model.addAttribute("productList", allProducts);		
	}
	
	@GetMapping("/edit-order/{oId}")
	public String toEditOrder(@PathVariable("oId")long oMID, Model model, Order oToEditOrder) {
		Long oMID_LONG = oMID;
		Order oToEdit = null;
		
		try {
			oToEdit = os.findOrderById(oMID_LONG);
			model.addAttribute("oToEdit",oToEdit);
			model.addAttribute("ordersList", os.getAllOrders());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return "edit-order";
	}
	
	@PostMapping("/filterOrders")
	public String toShowOrdersFiltered(Model model, @RequestParam("orderBy") String orderBy) {
		List<Order> orderListFiltered = null;
		
		switch(orderBy) {
		case "idAsc":
			orderListFiltered = os.sortOrderByIdAsc();
			break;
		case "idDesc":
			orderListFiltered = os.sortOrderByIdDesc();
			break;
		}
		
		sendOrdersTotalsToFront(model, orderListFiltered);
		model.addAttribute("orderList",orderListFiltered);
		model.addAttribute("orderBySelected",orderBy);
		return "ordersPage";
	}
	
	private void sendOrdersTotalsToFront(Model model, List<Order> oList) {
		List<String> totals = new ArrayList<>();
		for (Order o : oList) {
			totals.add(ods.getOrderTotalToDisplay(o.getOrdDetails()));
			oList.indexOf(o);
		}
		model.addAttribute("orderTotalList", totals);
	}
	
}

