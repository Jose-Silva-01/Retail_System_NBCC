package ca.nbcc.retailapp.model;


import java.util.ArrayList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity 
@Table(name="Order_Table")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Order {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="ORD_ID")
	private Long id;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="FK_EMPLOYEE_ID")
	private Employee employee;
	
	@OneToMany(mappedBy = "order")
//	@JoinColumn(name = "ORD_DET_ID")
	private List<OrderDetails> ordDetails;
	
	@OneToOne(mappedBy="order")
	private Shipping shipping;
	
	@Column(name="ORD_DATE")
	private LocalDate date;

	@OneToOne(mappedBy="order")
	private OrderReport orderReport;
	
	@OneToOne(mappedBy="order")
	private ShippingReport shippingReport;
	
	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}

//	public Order(Employee employee, List<OrderDetails> ordDetails, Shipping shipping, LocalDate date,
//			OrderReport orderReport, ShippingReport shippingReport) {
//		super();
//		this.employee = employee;
//		this.shipping = shipping;
//		this.date = date;
//		this.orderReport = orderReport;
//		this.shippingReport = shippingReport;
//	}

	public Long getId() {
		return id;
	}
  
	public void setId(Long id) {
		this.id = id;
	}

	public Employee getEmployee() {
		return employee;
	}

  
	public List<OrderDetails> getOrdDetails() {
		return ordDetails;
	}

	public void setOrdDetails(List<OrderDetails> ordDetails) {
		this.ordDetails = ordDetails;
	}

	public Shipping getShipping() {
		return shipping;
	}

	public void setShipping(Shipping shipping) {
		this.shipping = shipping;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}	

	public OrderReport getOrderReport() {
		return orderReport;
	}

	public void setOrderReport(OrderReport orderReport) {
		this.orderReport = orderReport;
	}

	public ShippingReport getShippingReport() {
		return shippingReport;
	}

	public void setShippingReport(ShippingReport shippingReport) {
		this.shippingReport = shippingReport;
	}

	/*@Override
	public int hashCode() {
		return Objects.hash(date, employee, id, ordDetails, orderReport, shipping, shippingReport);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		return Objects.equals(date, other.date) && Objects.equals(employee, other.employee) && id == other.id
				&& Objects.equals(ordDetails, other.ordDetails) && Objects.equals(orderReport, other.orderReport)
				&& Objects.equals(shipping, other.shipping) && Objects.equals(shippingReport, other.shippingReport);
	}*/

	/*@Override
	public String toString() {
		return "Order [id=" + id + ", employee=" + employee + ", shipping="
				+ ", date=" + date + ", orderReport=" + orderReport + ", shippingReport=" + shippingReport + "]";
	}*/
}
