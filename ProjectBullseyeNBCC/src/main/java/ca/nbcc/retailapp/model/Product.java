package ca.nbcc.retailapp.model;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.lang.NonNull;

@Entity
@Table(name="Product_Table")
@Embeddable
public class Product{

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="PROD_ID")
	private Long id;
	
	@Column(name="PROD_NAME")
	private String name;
	
	@Column(name="PROD_CATEGORY")
	private ProductCategory category;
	
	@Column(name="PROD_DESCRIPTION")
	private String description;
	
	@Column(name="PROD_REODER_LEVEL")
	private int reorderLevel;
	
	@Column(name="PROD_SIZE")
	private double size;
	
	@Column(name="PROD_WEIGHT")
	private double weight;
	
	@Column(name="PROD_CASE_SIZE")
	private double caseSize;
	
	@Column(name="PROD_COLOR")
	private String color;

	@Column(name="PROD_PRICE")
	private double price;
	
	@NonNull
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="FK_SUPPLIER_ID")
	private Suplier suplier;
	
	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Product(String name, ProductCategory category, String description, int reorderLevel, double size,
			double weight, double caseSize, String color, double price, Suplier suplier) {
		super();
		this.name = name;
		this.category = category;
		this.description = description;
		this.reorderLevel = reorderLevel;
		this.size = size;
		this.weight = weight;
		this.caseSize = caseSize;
		this.color = color;
		this.price = price;
		this.suplier = suplier;
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

	public ProductCategory getCategory() {
		return category;
	}

	public void setCategory(ProductCategory category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getReorderLevel() {
		return reorderLevel;
	}

	public void setReorderLevel(int reorderLevel) {
		this.reorderLevel = reorderLevel;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getCaseSize() {
		return caseSize;
	}

	public void setCaseSize(double caseSize) {
		this.caseSize = caseSize;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Suplier getSuplier() {
		return suplier;
	}

	public void setSuplier(Suplier suplier) {
		this.suplier = suplier;
	}

	@Override
	public int hashCode() {
		return Objects.hash(caseSize, category, color, description, id, name, price, reorderLevel, size, suplier,
				weight);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return Double.doubleToLongBits(caseSize) == Double.doubleToLongBits(other.caseSize)
				&& category == other.category && Objects.equals(color, other.color)
				&& Objects.equals(description, other.description) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name)
				&& Double.doubleToLongBits(price) == Double.doubleToLongBits(other.price)
				&& reorderLevel == other.reorderLevel
				&& Double.doubleToLongBits(size) == Double.doubleToLongBits(other.size)
				&& Objects.equals(suplier, other.suplier)
				&& Double.doubleToLongBits(weight) == Double.doubleToLongBits(other.weight);
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", category=" + category + ", description=" + description
				+ ", reorderLevel=" + reorderLevel + ", size=" + size + ", weight=" + weight + ", caseSize=" + caseSize
				+ ", color=" + color + ", price=" + price + ", suplier=" + suplier + "]";
	}
	
	
}
