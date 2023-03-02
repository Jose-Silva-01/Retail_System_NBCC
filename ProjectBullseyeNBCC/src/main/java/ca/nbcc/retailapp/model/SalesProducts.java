package ca.nbcc.retailapp.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Sales_Product_Table")
public class SalesProducts implements Serializable{
	
	@EmbeddedId
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="FK_SALES_ID")
	private Sales sale;
	
	@EmbeddedId
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="FK_INVENTORY_ID")
	private Inventory inventory;
	
	@Column(name="ORD_DET_QUANTITY")
	private int quantity;
	
	@Column(name="ORD_PRODUCT_PRICE")
	private double productPrice;

	public SalesProducts() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SalesProducts(Sales sale, Inventory inventory, int quantity, double productPrice) {
		super();
		this.sale = sale;
		this.inventory = inventory;
		this.quantity = quantity;
		this.productPrice = productPrice;
	}

	public Sales getSale() {
		return sale;
	}

	public void setSale(Sales sale) {
		this.sale = sale;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	@Override
	public int hashCode() {
		return Objects.hash(inventory, productPrice, quantity, sale);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SalesProducts other = (SalesProducts) obj;
		return Objects.equals(inventory, other.inventory)
				&& Double.doubleToLongBits(productPrice) == Double.doubleToLongBits(other.productPrice)
				&& quantity == other.quantity && Objects.equals(sale, other.sale);
	}

	@Override
	public String toString() {
		return "SalesProducts [sale=" + sale + ", inventory=" + inventory + ", quantity=" + quantity + ", productPrice="
				+ productPrice + "]";
	}
	
}
