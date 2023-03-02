package ca.nbcc.retailapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import ca.nbcc.retailapp.model.Suplier;
import ca.nbcc.retailapp.repo.SupplierJpaRepo;

@Service
public class SupplierService {

	private SupplierJpaRepo sRepo;
			
	@Autowired
	public SupplierService(SupplierJpaRepo sRepo) {
		super();
		this.sRepo = sRepo;
	}

	public Suplier addNewSupplier(Suplier s) {
		return sRepo.save(s);
	}

	public List<Suplier> getAllSuppliers() {
		return sRepo.findAll();
	}
	
	public Suplier findSuplierById(Long sMID_LONG) throws Exception {
		
		if(sRepo.findById(sMID_LONG).isPresent()) {
			return sRepo.findById(sMID_LONG).get();
		}
		else if (sRepo.findById((long)sMID_LONG).isEmpty()){
			throw new Exception(" Supplier not found: ID" + sMID_LONG);
		}
		return null;
	}

	public void updateSupplier(Suplier sToEdit) {

		sRepo.save(sToEdit);
		
	}
	
	public void deleteSupplier(Long id) {
		
		sRepo.deleteById(id);
	}
	
	public List<Suplier> searchSupplierByName(String searchTerm) {
		
		return sRepo.findByNameContainingIgnoreCase(searchTerm);
	}

	public List<Suplier> orderById() {
		return sRepo.findByOrderById();
	}

	public List<Suplier> orderByName() {
		return sRepo.findByOrderByName();
	}
}
