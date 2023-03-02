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
@Table(name="Department_Table")
public class Department {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="DEP_ID")
	private Long id;
	
	@Column(name="DEP_NAME")
	private String name;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="FK_STORE_ID")
	private Store store;

	public Department() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Department(String name, Store store) {
		super();
		this.name = name;
		this.store = store;
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

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, store);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Department other = (Department) obj;
		return id == other.id && Objects.equals(name, other.name) && Objects.equals(store, other.store);
	}

	@Override
	public String toString() {
		return "Department [id=" + id + ", name=" + name + ", store=" + store + "]";
	}
}
