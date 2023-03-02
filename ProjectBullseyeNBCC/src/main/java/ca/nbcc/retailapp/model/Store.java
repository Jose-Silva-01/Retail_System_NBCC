package ca.nbcc.retailapp.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="Store_Table")
public class Store {

	@Id
	@SequenceGenerator(name = "STO_SEQ_GEN", sequenceName = "STO_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STO_SEQ_GEN")
	@Column(name="STORE_ID")
	private Long id;
	
	@Column(name="STORE_NAME")
	private String name;
	
	@Column(name="STORE_LOCATION")
	private String location;
	
	@Column(name="STORE_CITY")
	private String city;
	
	@Column(name="STORE_PHONE")
	private String phone;
	
	@Column(name="STORE_ISWAREHOUSE")
	private boolean isWarehouse;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy="store")
	private List<Inventory> inventoryProducts;

	public Store() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Store(String name, String location, String city, String phone, boolean isWarehouse) {
		super();
		this.name = name;
		this.location = location;
		this.city = city;
		this.phone = phone;
		this.isWarehouse = isWarehouse;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean getIsWarehouse() {
		return isWarehouse;
	}

	public void setIsWarehouse(boolean isWarehouse) {
		this.isWarehouse = isWarehouse;
	}

	public List<Inventory> getInventoyProducts() {
		return inventoryProducts;
	}

	public void setInventoyProducts(List<Inventory> inventoyProducts) {
		this.inventoryProducts = inventoyProducts;
	}

	@Override
	public int hashCode() {
		return Objects.hash(city, id, inventoryProducts, isWarehouse, location, name, phone);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Store other = (Store) obj;
		return Objects.equals(city, other.city) && Objects.equals(id, other.id)
				&& Objects.equals(inventoryProducts, other.inventoryProducts) && isWarehouse == other.isWarehouse
				&& Objects.equals(location, other.location) && Objects.equals(name, other.name)
				&& Objects.equals(phone, other.phone);
	}

	@Override
	public String toString() {
		return "Store [id=" + id + ", name=" + name + ", location=" + location + ", city=" + city + ", phone=" + phone
				+ ", isWarehouse=" + isWarehouse + ", inventoyProducts=" + inventoryProducts + "]";
	}
}
