package ca.nbcc.retailapp.model;

import java.util.Objects;
import java.time.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Nationalized;

@Entity
@Table(name="Report_Table")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "REP_CATEGORY", discriminatorType = DiscriminatorType.STRING)
public class Report {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="REP_ID")
	private Long id;
	
	@Column(name="REP_CATEGORY", insertable=false, updatable=false)
	protected String repCategory;
	
	@Column(name="REP_TITLE")
	private String title;
	
	@Column(name="REP_DATE")
	private LocalDate date;
	
	@Column(name="REP_DESCRIPTION", length=2000)
	@Nationalized
	private String description;
	
	@Column(name="REP_TEXT", length=2000)
	@Nationalized
	private String text;
	
	@Column(name="REP_ADDITIONAL_INFO", length=2000)
	@Nationalized
	private String additionalInfo;
	
	@ManyToOne()
	@JoinColumn(name="FK_EMPLOYEE_ID")
	private Employee employee;

	
	
	public Report() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Report(String title, LocalDate date, String description, String text,
			String additionalInfo, Employee employee) {
		super();
		this.title = title;
		this.date = date;
		this.description = description;
		this.text = text;
		this.additionalInfo = additionalInfo;
		this.employee = employee;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public Employee getEmployee() {
		return employee;
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
	
	
	public String getRepCategory() {
		return repCategory;
	}


	@Override
	public int hashCode() {
		return Objects.hash(additionalInfo, description, employee, id, text, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Report other = (Report) obj;
		return Objects.equals(additionalInfo, other.additionalInfo) 
				&& Objects.equals(description, other.description) && Objects.equals(employee, other.employee)
				&& id == other.id && Objects.equals(text, other.text) && Objects.equals(title, other.title);
	}

	@Override
	public String toString() {
		return "Report [id=" + id + ", title=" + title + ", description=" + description
				+ ", text=" + text + ", additionalInfo=" + additionalInfo + ", employee=" + employee + "]";
	}
}
