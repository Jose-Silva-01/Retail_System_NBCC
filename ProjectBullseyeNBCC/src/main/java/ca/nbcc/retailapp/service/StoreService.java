package ca.nbcc.retailapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import ca.nbcc.retailapp.model.Store;

import ca.nbcc.retailapp.repo.StoreJpaRepo;

@Service
public class StoreService {

	private StoreJpaRepo stoRepo;

	@Autowired
	public StoreService(StoreJpaRepo stoRepo) {
		super();
		this.stoRepo = stoRepo;
	}
	
	public Store addNewStore(Store sto) {
		return stoRepo.save(sto);
	}

	public List<Store> getAllStores() {
		return stoRepo.findAll();
	}
	
	public Store findStoreById(Long stoMID_LONG) {
		
		if(stoRepo.findById((long)stoMID_LONG).isPresent()) {
			return stoRepo.findById((long)stoMID_LONG).get();
		}
		else if (stoRepo.findById((long)stoMID_LONG).isEmpty()){
			
		}
		return null;
	}
	
	public List<Store> searchStoreByName(String searchTerm) {
		
		return stoRepo.findByCityContainingIgnoreCase(searchTerm);
	}

	public void updateStore(Store stoToEdit) {
		
		stoRepo.save(stoToEdit);
	}

	public List<Store> orderById() {

		return stoRepo.findByOrderById();
	}

	public List<Store> orderByCity() {

		return stoRepo.findByOrderByCity();
	}
}
