package ca.nbcc.retailapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.nbcc.retailapp.model.Product;
import ca.nbcc.retailapp.repo.ProductJpaRepo;

@Service
public class ProductService {

	private ProductJpaRepo pRepo;
			
	@Autowired
	public ProductService(ProductJpaRepo pRepo) {
		super();
		this.pRepo = pRepo;
	}

	public Product addNewProduct(Product p) {
		return pRepo.save(p);
	}

	public List<Product> getAllProducts() {
		return pRepo.findAll();
	}
	
	public Product findProductById(Long pMID_LONG) throws Exception {
		
		if(pRepo.findById((long)pMID_LONG).isPresent()) {
			return pRepo.findById((long)pMID_LONG).get();
		}
		else if (pRepo.findById((long)pMID_LONG).isEmpty()){
			throw new Exception(" Product not found: ID" + pMID_LONG);
		}
		return null;
	}

	public void updateProduct(Product pToEdit) {

		pRepo.save(pToEdit); 
		
	}
	
	public void deleteProduct(Long id) {
		
		pRepo.deleteById(id);
	}
	
	public List<Product> searchProductByName(String searchTerm) {
		
		return pRepo.findByNameContainingIgnoreCase(searchTerm);
	}
	
	public List<Product> orderById(){
		
		return pRepo.findByOrderById();
	}
	
	public List<Product> orderByPrice(){
			
			return pRepo.findByOrderByPrice();
		}
	
	public List<Product> orderByName(){
		
		return pRepo.findByOrderByName();
	}
}
