package session_bean;

import entity.Customer;

import java.util.Random;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ThanDieu
 */
@Stateless
public class CustomerSessionBean extends AbstractSessionBean<Customer> {
	@PersistenceContext(unitName = "eMarketPU")
	private EntityManager em;

	protected EntityManager getEntityManager() {
		return em;
	}

	public CustomerSessionBean() {
		super(Customer.class);
	}
	
	public Customer addCustomer(String name, String username, String password, String email, 
			String phone, String address, String cityRegion, String ccNumber) {
		Random rd= new Random();
		Customer customer = new Customer();
		customer.setCustomerId(rd.nextInt(1000));
		customer.setName(name);
		customer.setUsername(username);
		customer.setPassword(password);
		customer.setEmail(email);
		customer.setPhone(phone);
		customer.setAddress(address);
		customer.setCityRegion(cityRegion);
		customer.setCcNumber(ccNumber);
		create(customer);
		return find(customer.getCustomerId());
	}
	
	
}
