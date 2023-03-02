package ca.nbcc.retailapp.controller;

import java.util.ArrayList;
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

import ca.nbcc.retailapp.model.Employee;
import ca.nbcc.retailapp.model.Inventory;
import ca.nbcc.retailapp.model.Order;
import ca.nbcc.retailapp.model.Shipping;
import ca.nbcc.retailapp.model.ShippingStatus;
import ca.nbcc.retailapp.model.Store;
import ca.nbcc.retailapp.service.InventoryService;
import ca.nbcc.retailapp.service.OrderService;
import ca.nbcc.retailapp.service.ShippingService;
import ca.nbcc.retailapp.service.UserService;

@Controller
public class ShippingController {

	private ApplicationContext ctx;

	private ShippingService shiS;
	private InventoryService iS;
	private UserService uS;
	private OrderService oS;

	public ShippingController() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Autowired
	public ShippingController(ApplicationContext ctx, ShippingService shiS, 
			OrderService oS, InventoryService iS, UserService uS) {
		super();
		this.ctx = ctx;
		this.shiS = shiS;
		this.oS = oS;
		this.iS = iS;
		this.uS = uS;
	}

	public ShippingController(ShippingService shiS) {
		super();
		this.shiS = shiS;
	}

	public ShippingService getShiS() {
		return shiS;
	}

	public void setShiS(ShippingService shiS) {
		this.shiS = shiS;
	}

	@PostMapping("/filterShipping")
	public String orderInventory(Model model, @RequestParam("orderBy") String orderBy) {
		
		List<Shipping> sList = null;
		
		if(orderBy.equals("arriveDate")) {
			sList = shiS.orderByArriveDate();
		} else if (orderBy.equals("orderDate")) {
			sList = shiS.orderByOrderDate();
		} else if (orderBy.equals("id")){
			sList = shiS.orderById();
		}
		
		model.addAttribute("shippingList", sList);
		model.addAttribute("orderBySelected", orderBy);
		
		return "shipping-display";
	}

	@GetMapping("/showShipping")
	public String showShippings(Model model, Long oId) {

		List<Shipping> sList = new ArrayList<>();

		if (oId != null) {
			sList = shiS.searchShippingByOrderId(oId);
		} else {
			sList = shiS.getAllShippings();
		}

		model.addAttribute("shippingList", sList);
		return "shipping-display";
	}

	@GetMapping("/browse-shipping/{sId}")
	public String viewStore(@PathVariable("sId") long sMID, Model model) {
		Shipping sToView = null;
		Long sMID_LONG = sMID;
		
		ShippingStatus closedStatus = null;

		try {
			sToView = shiS.findShippingById(sMID_LONG);
			
			closedStatus = ShippingStatus.CLOSED;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		model.addAttribute("shippingToView", sToView);
		model.addAttribute("closedStatus", closedStatus);
		
		return "browse-shipping";
	}

	@GetMapping("/edit-shipping/{sId}")
	public String toEditShippinh(@PathVariable("sId") long sMID, Model model, Order sToEditOrder) {

		Long sMID_LONG = sMID;
		Shipping sToEdit = null;

		try {
			sToEdit = shiS.findShippingById(sMID_LONG);
			model.addAttribute("sToEdit", sToEdit);
			model.addAttribute("ordersList", oS.getAllOrders());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "edit-shipping";
	}

	@PostMapping("/updateShipping")
	public String processUpdateShipping(Shipping sToEdit) {
		
		shiS.updateShipping(sToEdit);

		return "redirect:/browse-shipping/" + sToEdit.getId();
	}
	
	@GetMapping("/updateInvThroughShipping/{sId}")
	public String updateInventoryThroughShipping(@PathVariable("sId") long sMID, Model model, @CurrentSecurityContext(expression="authentication?.name")
    String username) {
		
		Employee loggedEmployee = uS.findEmployeeByUsername(username);
		Store currentStore = loggedEmployee.getDept().getStore();

		List<Inventory> iList = new ArrayList<>();
		
		Long sMID_LONG = sMID;
		Shipping closedShipping = null;

		try {
			closedShipping = shiS.findShippingById(sMID_LONG);
			
			for(var sInv : currentStore.getInventoyProducts()) {
				
				for(var oD : closedShipping.getOrder().getOrdDetails()) {
					
					Inventory currentInv = iS.findInventoryById(sInv.getId());
					
					if(oD.getProduct().equals(currentInv.getProduct())) {
						currentInv.setQuantity(currentInv.getQuantity() + oD.getQuantity());

						iS.updateInventory(currentInv);
					}
				}
			}
			
			iList = iS.findAllInventoryProductsByStore(currentStore);
			System.out.println("HI" + iList);
			model.addAttribute("currentStore", currentStore);
			model.addAttribute("inventoryList", iList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/showInventory";
	}

	@GetMapping("/deleteShipping/{sId}")
	public String deleteShippingById(@PathVariable("sId") long sMID, Model model){
		
		Long sMID_LONG = sMID;

		try {
			shiS.deleteShipping(sMID_LONG);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "redirect:/showShipping";
	}
}
