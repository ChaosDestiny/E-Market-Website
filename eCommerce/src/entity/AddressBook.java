package entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the address_book database table.
 * 
 */
@Entity
@Table(name="address_book")
@NamedQueries({
	@NamedQuery(name="AddressBook.findAll", query="SELECT a FROM AddressBook a"),
    @NamedQuery(name = "AddressBook.findByCustomer", query = "SELECT a FROM AddressBook a WHERE a.customer = :customer")})
public class AddressBook implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="address_id")
	private int addressId;

	private String address;

	private String city;

	private String phone;

	//bi-directional many-to-one association to Customer
	@ManyToOne
	@JoinColumn(name="customer_id")
	private Customer customer;
	
	public AddressBook() {
	}

	public int getAddressId() {
		return this.addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}