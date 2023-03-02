package ca.nbcc.retailapp.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "Shipping_Table")
public class Shipping {

	@Id
	@SequenceGenerator(name = "SHI_SEQ_GEN", sequenceName = "SHI_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SHI_SEQ_GEN")
	@Column(name = "SHI_ID")
	private Long id;

	@Column(name = "SHI_LEAVE_DATE")
	private LocalDate leaveDate;

	@Column(name = "SHI_ARRIVE_DATE")
	private LocalDate arriveDate;

	@Column(name = "SHI_STATUS")
	private ShippingStatus status;

	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "FK_ORDER_ID")
	private Order order;


	public Shipping() {
		super();
	}

	public Shipping(LocalDate leaveDate, LocalDate arriveDate, ShippingStatus status, Order order) {
		super();
		this.leaveDate = leaveDate;
		this.arriveDate = arriveDate;
		this.status = status;
		this.order = order;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(LocalDate leaveDate) {
		this.leaveDate = leaveDate;
	}

	public LocalDate getArriveDate() {
		return arriveDate;
	}

	public void setArriveDate(LocalDate arriveDate) {
		this.arriveDate = arriveDate;
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
	public int hashCode() {
		return Objects.hash(arriveDate, id, leaveDate, order, status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Shipping other = (Shipping) obj;
		return Objects.equals(arriveDate, other.arriveDate) && id == other.id
				&& Objects.equals(leaveDate, other.leaveDate) && Objects.equals(order, other.order)
				&& status == other.status;
	}

	@Override
	public String toString() {
		return "Shipping [id=" + id + ", leaveDate=" + leaveDate + ", arriveDate=" + arriveDate + ", status=" + status + ".";
	}

}
