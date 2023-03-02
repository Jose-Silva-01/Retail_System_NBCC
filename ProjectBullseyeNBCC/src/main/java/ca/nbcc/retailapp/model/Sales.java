package ca.nbcc.retailapp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="Sales_Table")
@Embeddable
public class Sales implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="SAL_ID")
	private int id;
	
	@Column(name="SAL_DATE")
	private Date date;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="FK_CUSTOMER_ID")
	private Customer customer;
	
	@OneToMany(mappedBy = "sale")
	private List<SalesProducts> salesProd;

	public Sales() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Sales(Date date, Customer customer) {
		super();
		this.date = date;
		this.customer = customer;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public int hashCode() {
		return Objects.hash(customer, date, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sales other = (Sales) obj;
		return Objects.equals(customer, other.customer) && Objects.equals(date, other.date) && id == other.id;
	}

	@Override
	public String toString() {
		return "Sales [id=" + id + ", date=" + date + ", customer=" + customer + "]";
	}
}
