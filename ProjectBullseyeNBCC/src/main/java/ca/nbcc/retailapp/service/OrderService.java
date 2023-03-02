package ca.nbcc.retailapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ca.nbcc.retailapp.model.Order;
import ca.nbcc.retailapp.repo.EmployeeJpaRepo;
import ca.nbcc.retailapp.repo.OrderJpaRepo;

@Service
public class OrderService {
	OrderJpaRepo oRepo;
	EmployeeJpaRepo er;
	
	@Autowired
	public OrderService(OrderJpaRepo oRepo) {
		super();
		this.oRepo = oRepo;
	}

	
	public Order addNewOrder(Order o) {
		return oRepo.save(o);
	}
	
	public void updateOrder(Order o) {
		oRepo.save(o);
	}
	
	public void deleteOrder(Order o) {
		oRepo.delete(o);
	}
	

	public Order getLastOrder() {
		List<Order> allOrders = oRepo.findAll(); 
		return allOrders.get(allOrders.size()-1);
	}
	
	public List<Order> getAllOrders(){
		return oRepo.findAll();
	}
	
 public List<Order> sortOrderByIdAsc(){
		return oRepo.findByOrderByIdAsc();
	}
	
	public List<Order> sortOrderByIdDesc(){
		return oRepo.findByOrderByIdDesc();
	}
	
	
	
	public List<Order> sortOrderByParam(String param){
		List<Order> orderListSorted = oRepo.findAll(Sort.by(Sort.Direction.ASC, param));
		return orderListSorted;
	}
	
	
	public Order findOrderById(Long iOID_LONG) throws Exception {
		if(oRepo.findById((long)iOID_LONG).isPresent()) {
			return oRepo.findById((long)iOID_LONG).get();
		}
		else if(oRepo.findById((long)iOID_LONG).isEmpty()) {
			
		}
		return null;
	}
	
	public List<Order> orderById(){
		return oRepo.findByOrderById();
	}
	
	public List<Order> searchOrderByOrderId(Long oId){
		return null;
	}
	
	
	/**public Order findOrderById(Long id) throws Exception{
		if(oRepo.findById(id).isPresent()) {
			System.out.println("Order is present. id=" + id);
			return oRepo.findById(id).get();
		}
		else if(oRepo.findById(id).isEmpty()) {
			throw new Exception("Order not found" + id);
		}
		return null;
	}
	
*/	

}
