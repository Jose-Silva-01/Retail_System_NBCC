package ca.nbcc.retailapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.nbcc.retailapp.model.Shipping;
import ca.nbcc.retailapp.repo.ShippingJpaRepo;

@Service
public class ShippingService {

	private ShippingJpaRepo sRepo;
	
	@Autowired
	public ShippingService(ShippingJpaRepo sRepo) {
		super();
		this.sRepo = sRepo;
	}

	public Shipping addNewShipping(Shipping s) {
		return sRepo.save(s);
	}

	public List<Shipping> getAllShippings() {
		return sRepo.findAll();
	}
	
	public Shipping findShippingById(Long sMID_LONG) throws Exception {
		
		if(sRepo.findById((long)sMID_LONG).isPresent()) {
			return sRepo.findById((long)sMID_LONG).get(); //getting the movie inside the repo
		}
		else if (sRepo.findById((long)sMID_LONG).isEmpty()){
			throw new Exception(" Shipping not found: ID" + sMID_LONG);
		}
		return null;
	}

	public void updateShipping(Shipping sToEdit) {

		sRepo.save(sToEdit); 
		
	}
	
	public void deleteShipping(Long id) {
		
		sRepo.deleteById(id);
	}
	
	public List<Shipping> orderById(){
		
		return sRepo.findByOrderById();
	}

	public List<Shipping> searchShippingByOrderId(Long oId) {
		
		return sRepo.findByOrderId(oId);
	}

	public List<Shipping> orderByOrderDate() {
		
		return sRepo.findAllByOrderByLeaveDateAsc();
	}

	public List<Shipping> orderByArriveDate() {

		return sRepo.findAllByOrderByArriveDateAsc();
	}
}
