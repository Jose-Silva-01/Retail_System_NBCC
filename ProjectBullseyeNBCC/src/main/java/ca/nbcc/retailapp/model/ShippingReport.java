package ca.nbcc.retailapp.model;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue(value = "SHIPPING")
public class ShippingReport extends Report {
	
	@Column(name="REP_SHIPPING_STATUS")
	private ShippingStatus status;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ORDER_ID", referencedColumnName = "ORD_ID")
	private Order order;
	
	public ShippingReport() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ShippingReport(String title, LocalDate date, String description, String text, String additionalInfo,
			Employee employee) {
		super(title, date, description, text, additionalInfo, employee);
	}	
	
	public ShippingReport(ShippingStatus status, Order order) {
		super();
		this.status = status;
		this.order = order;
	}
	public ShippingStatus getStatus() {
		return status;
	}
	public void setStatus(ShippingStatus status) {
		this.status = status;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	@Override
	public String toString() {
		return "ShippingReport [status=" + status + ", order=" + order + ", getId()=" + getId() + ", getTitle()="
				+ getTitle() + ", getDescription()=" + getDescription() + ", getText()=" + getText()
				+ ", getAdditionalInfo()=" + getAdditionalInfo() + ", getEmployee()=" + getEmployee() + ", getDate()="
				+ getDate() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + ", getClass()="
				+ getClass() + "]";
	}	
}
