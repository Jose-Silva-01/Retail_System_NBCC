package ca.nbcc.retailapp.service;


import java.text.NumberFormat;
import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import ca.nbcc.retailapp.model.OrderDetails;
import ca.nbcc.retailapp.repo.OrderDetailsJpaRepo;

@Service
public class OrderDetailsService {
	
	OrderDetailsJpaRepo odRepo;
	
	
	@Autowired
	public OrderDetailsService(OrderDetailsJpaRepo odRepo) {
		super();
		this.odRepo = odRepo;
	}
	
	public List<String> getTotalListToDisplay(List<OrderDetails> oDetailsList){
		List<String> totalListDisplay = new ArrayList<>();
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		
		for (OrderDetails od : oDetailsList) {
			totalListDisplay.add(formatter.format(this.calculateTotal(od)));
		}
		
		return totalListDisplay;
	}
	
	public String getOrderTotalToDisplay(List<OrderDetails> oDetailsList) {
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		return formatter.format(this.getOrderTotal(oDetailsList));
	}
	
	public String getOrderTaxesToDisplay(List<OrderDetails> oDetailsList) {
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		return formatter.format(this.getOrderTaxes(oDetailsList));
	}
	
	public String getOrderSubTotalToDisplay(List<OrderDetails> oDetailsList) {
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		return formatter.format(this.getOrderSubTotal(oDetailsList));
	}
	
	public double getOrderTotal(List<OrderDetails> oDetailsList) {
		double orderTotal = 0; 
		
		orderTotal = this.getOrderTaxes(oDetailsList)+ this.getOrderSubTotal(oDetailsList);
		return orderTotal;		
	}
	
	public double getOrderTaxes(List<OrderDetails> oDetailsList) {
		final double PROVINCIAL_TAX_RATE = 0.15;
		double orderTaxes = 0;
		
		orderTaxes = this.getOrderSubTotal(oDetailsList) * PROVINCIAL_TAX_RATE;
		return orderTaxes;
	}
	
	public double getOrderSubTotal(List<OrderDetails> oDetailsList) {
		double orderSubTotal = 0;
		List<Double> totalList = this.getTotalList(oDetailsList);
		
		for (Double t : totalList) {
			orderSubTotal += t;
		}
		
		return orderSubTotal;
	}
	
	public List<Double> getTotalList(List<OrderDetails> oDetailsList){		
		List<Double> totalList = new ArrayList<>();
		
		for (OrderDetails od : oDetailsList) {
			totalList.add(this.calculateTotal(od));
		}
		
		return totalList;
	}

	public double calculateTotal(OrderDetails od) {
		double total = od.getQuantity() * od.getProduct().getPrice();
		return total;
	}
	
	public OrderDetails addNewOrderDetails(OrderDetails od) {
		return odRepo.save(od);
	}
	
	public void updateOrderDetails(OrderDetails od) {
		odRepo.save(od);
	}
	
	public void deleteOrderDetails(OrderDetails od) {
		odRepo.delete(od);
	}
	
	public List<OrderDetails> getAllOrders(){
		return odRepo.findAll();
		
	}
	
	public List<OrderDetails> searchOrderByOrderId(Long oId){
		return null;
	}
}
