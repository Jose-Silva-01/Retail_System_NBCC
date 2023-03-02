package ca.nbcc.retailapp.model;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="Inventory_Table")
@Embeddable
public class Inventory{

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="INV_ID")
	private Long id;
	
	@Column(name="INV_QUANTITY")
	private int quantity;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="FK_STORE_ID")
	private Store store;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="FK_PRODUCT_ID")
	private Product product;

	public Inventory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Inventory(int quantity, Store store, Product product) {
		super();
		this.quantity = quantity;
		this.store = store;
		this.product = product;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, product, quantity, store);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Inventory other = (Inventory) obj;
		return Objects.equals(id, other.id) && Objects.equals(product, other.product) && quantity == other.quantity
				&& Objects.equals(store, other.store);
	}

	@Override
	public String toString() {
		return "Inventory [id=" + id + ", quantity=" + quantity + ", product=" + product + "]";
	}
	
	
}
