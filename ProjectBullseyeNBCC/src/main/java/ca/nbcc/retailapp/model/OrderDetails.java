package ca.nbcc.retailapp.model;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="Order_Details_Table")
public class OrderDetails{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ORD_DET_ID")
	private int id;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="FK_ORDER_ID")
	private Order order;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="FK_PRODUCT_ID")
	private Product product;
	
	@Column(name="ORD_DET_QUANTITY")
	private int quantity;

	public OrderDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderDetails(Order order, Product product, int quantity) {
		super();
		this.order = order;
		this.product = product;
		this.quantity = quantity;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public int hashCode() {
		return Objects.hash(order, product, quantity);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderDetails other = (OrderDetails) obj;
		return Objects.equals(order, other.order) && Objects.equals(product, other.product)
				&& quantity == other.quantity;
	}

	@Override
	public String toString() {
		return "OrderDetails [order=" + order + ", product=" + product + ", quantity=" + quantity + "]";
	}
}
