package entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the customer_order database table.
 * 
 */
@Entity
@Table(name="customer_order")
@NamedQueries({
	@NamedQuery(name="CustomerOrder.findAll", query="SELECT c FROM CustomerOrder c"),
    @NamedQuery(name = "CustomerOrder.findByCustomer", query = "SELECT c FROM CustomerOrder c WHERE c.customer = :customer")})
public class CustomerOrder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="order_id")
	private int orderId;

	private BigDecimal amount;

	@Column(name="confirmation_number")
	private int confirmationNumber;

	@Temporal(TemporalType.DATE)
	@Column(name="date_created")
	private Date dateCreated;

	@Column(name="delivery_address")
	private String deliveryAddress;

	@Column(name="order_state")
	private String orderState;

	@Column(name="payment_method")
	private String paymentMethod;

	//bi-directional many-to-one association to Customer
	@ManyToOne
	@JoinColumn(name="customer_id")
	private Customer customer;

	//bi-directional many-to-one association to OrderedProduct
	@OneToMany(mappedBy="customerOrder")
	private List<OrderedProduct> orderedProducts;

	public CustomerOrder() {
	}

	public int getOrderId() {
		return this.orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public int getConfirmationNumber() {
		return this.confirmationNumber;
	}

	public void setConfirmationNumber(int confirmationNumber) {
		this.confirmationNumber = confirmationNumber;
	}

	public Date getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getDeliveryAddress() {
		return this.deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getOrderState() {
		return this.orderState;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}

	public String getPaymentMethod() {
		return this.paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<OrderedProduct> getOrderedProducts() {
		return this.orderedProducts;
	}

	public void setOrderedProducts(List<OrderedProduct> orderedProducts) {
		this.orderedProducts = orderedProducts;
	}

	public OrderedProduct addOrderedProduct(OrderedProduct orderedProduct) {
		getOrderedProducts().add(orderedProduct);
		orderedProduct.setCustomerOrder(this);

		return orderedProduct;
	}

	public OrderedProduct removeOrderedProduct(OrderedProduct orderedProduct) {
		getOrderedProducts().remove(orderedProduct);
		orderedProduct.setCustomerOrder(null);

		return orderedProduct;
	}

}