package ca.nbcc.retailapp.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="Suplier_Table")
public class Suplier {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="SUP_ID")
	private Long id;
	
	@Column(name="SUP_NAME")
	private String name;
	
	@Column(name="SUP_ADDRESS")
	private String address;

	public Suplier() {
		super();
	}

	public Suplier(String name, String address) {
		super();
		this.name = name;
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Supplier [id=" + id + ", name=" + name + ", address=" + address + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Suplier other = (Suplier) obj;
		return Objects.equals(address, other.address) && id == other.id && Objects.equals(name, other.name);
	}
}
