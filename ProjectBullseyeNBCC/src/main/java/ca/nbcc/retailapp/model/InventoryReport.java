package ca.nbcc.retailapp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@DiscriminatorValue(value = "INVENTORY")
public class InventoryReport extends Report{

    /*@ElementCollection 
    @CollectionTable(name = "report_inventory_list", joinColumns = @JoinColumn(name = "id")) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "list") 
	private List<Inventory> inventories = new ArrayList<>();*/

	public InventoryReport() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InventoryReport( String title, LocalDate date, String description, String text, String additionalInfo,
			Employee employee /*List<Inventory> inventories*/) {
		super(title, date, description, text, additionalInfo, employee);
		/*this.inventories = inventories;*/
	}
	

	/*public List<Inventory> getInventories() {
		return inventories;
	}

	public void setInventories(List<Inventory> inventories) {
		this.inventories = inventories;
	}*/

	@Override
	public String toString() {
		return "InventoryReport [ getId()=" + getId() + ", getTitle()=" + getTitle()
				+ ", getDescription()=" + getDescription() + ", getText()=" + getText() + ", getAdditionalInfo()="
				+ getAdditionalInfo() + ", getEmployee()=" + getEmployee() + ", getDate()=" + getDate()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + ", getClass()=" + getClass()
				+ "]";
	}
	
	
}
