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

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Validated
@Component
@Scope("prototype") // default is SINGLETON
@Entity
@Table(name = "Employee_Table")
public class Employee {

	@Id
	@SequenceGenerator(name = "EMP_SEQ_GEN", sequenceName = "EMP_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMP_SEQ_GEN")
	@Column(name = "EMP_ID", unique = true)
	private Long id;

	@Column(name = "EMP_USERNAME")
	private String username;

	@Column(name = "EMP_PASSWORD")
	private String password;

	@Column(name = "EMP_ROLE")
	private JobRole jobRole; // enum?

	@Column(name = "EMP_FIRSTNAME")
	private String firstName;

	@Column(name = "EMP_LASTNAME")
	private String lastName;

	@Column(name = "EMP_PHONE")
	private String phone;

	@Column(name = "EMP_EMAIL")
	private String email;

	@Column(name = "EMP_ADDRESS")
	private String address;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "FK_DEPARTMENT_ID")
	private Department dept;

	private String imgURI;

	public Employee() {
		super();
	}

	public Employee(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public Employee(String username, String password, JobRole jobRole, String firstName, String lastName, String phone,
			String email, String address, Department dept, String imgURI) {
		super();
		this.username = username;
		this.password = password;
		this.jobRole = jobRole;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.dept = dept;
		this.imgURI = imgURI;
	}

	public String getImgURI() {
		return imgURI;
	}

	public void setImgURI(String imgURI) {
		this.imgURI = imgURI;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public JobRole getJobRole() {
		return jobRole;
	}

	public void setJobRole(JobRole jobRole) {
		this.jobRole = jobRole;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Department getDept() {
		return dept;
	}

	public void setDept(Department dept) {
		this.dept = dept;
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, dept, email, firstName, id, jobRole, lastName, password, phone, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return Objects.equals(address, other.address) && Objects.equals(dept, other.dept)
				&& Objects.equals(email, other.email) && Objects.equals(firstName, other.firstName) && id == other.id
				&& Objects.equals(jobRole, other.jobRole) && Objects.equals(lastName, other.lastName)
				&& Objects.equals(password, other.password) && Objects.equals(phone, other.phone)
				&& Objects.equals(username, other.username);
	}

	/**
	 * @Override public String toString() { return "Employee [id=" + id + ",
	 *           username=" + username + ", password=" + password + ", jobRole=" +
	 *           jobRole + ", firstName=" + firstName + ", lastName=" + lastName +
	 *           ", phone=" + phone + ", email=" + email + ", address=" + address +
	 *           ", dept=" + dept + "]"; }
	 **/
	
	@Override
	public String toString() {
		return "Employee [id=" + id + ", username=" + username + ", password=";
	}
}
