package ca.nbcc.retailapp.model;


import java.time.LocalDate;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue(value = "ORDER")
public class OrderReport extends Report {
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ORDER_ID", referencedColumnName = "ORD_ID")
	private Order order;

	public OrderReport() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public OrderReport(String title, LocalDate date, String description, String text, String additionalInfo,
			Employee employee) {
		super(title, date, description, text, additionalInfo, employee);
		// TODO Auto-generated constructor stub
	}

	public OrderReport(Order order) {
		super();
		this.order = order;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	/*@Override
	public String toString() {
		return "OrderReport [order=" + order + ", getId()=" + getId() + ", getTitle()=" + getTitle()
				+ ", getDescription()=" + getDescription() + ", getText()=" + getText() + ", getAdditionalInfo()="
				+ getAdditionalInfo() + ", getEmployee()=" + getEmployee() + ", getDate()=" + getDate()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + ", getClass()=" + getClass()
				+ "]";
	}*/

	
	
	
}
