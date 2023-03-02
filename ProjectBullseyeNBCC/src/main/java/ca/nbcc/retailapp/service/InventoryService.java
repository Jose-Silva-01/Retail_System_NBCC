package ca.nbcc.retailapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.nbcc.retailapp.model.Inventory;
import ca.nbcc.retailapp.model.Store;
import ca.nbcc.retailapp.repo.InventoryJpaRepo;


@Service
public class InventoryService {

	private InventoryJpaRepo iRepo;
			
	@Autowired
	public InventoryService(InventoryJpaRepo iRepo) {
		super();
		this.iRepo = iRepo;
	}

	public Inventory addNewInventory(Inventory i) {
		return iRepo.save(i);
	}

	public List<Inventory> getAllInventoryProducts() {
		return iRepo.findAll();
	}
	
	public Inventory findInventoryById(Long iMID_LONG){
		
		if(iRepo.findById((long)iMID_LONG).isPresent()) {
			return iRepo.findById((long)iMID_LONG).get(); //getting the movie inside the repo
		}
		else if (iRepo.findById((long)iMID_LONG).isEmpty()){
		}
		return null;
	}

	public void updateInventory(Inventory iToEdit) {

		iRepo.save(iToEdit); 
	}
	
	public void deleteInventory(Long id) {
		
		iRepo.deleteById(id);
	}
	
	public List<Inventory> searchInventoryByProductName(Store s, String searchTerm) {
		
		return iRepo.findByProductNameContainingIgnoreCase(s, searchTerm);
	}
	
	public List<Inventory> findAllInventoryProductsByStore(Store s) {
		
		return iRepo.findByStore(s);
	}
	
	public List<Inventory> getInventoryLowerThanReorderLevel(Store s){
		
		return iRepo.getInventoryLowerThanReorderLevel(s);
	}

	public List<Inventory> orderByProductName(Store s) {
		return iRepo.findByOrderByProductName(s);
	}

	public List<Inventory> orderByQuantity(Store s) {
		return iRepo.findByOrderByQuantity(s);
	}

	public List<Inventory> orderById(Store s) {
		return iRepo.findByOrderById(s);
	}
}
