package session_bean;

import entity.Customer;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

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
			String phone, String address, String cityRegion, String ccNumber, String acNumber) {
		Customer customer = new Customer();
		int id = findAll().size() + 1;
		customer.setCustomerId(id);
		customer.setName(name);
		customer.setUsername(username);
		customer.setPassword(password);
		customer.setEmail(email);
		customer.setPhone(phone);
		customer.setAddress(address);
		customer.setCityRegion(cityRegion);
		customer.setCcNumber(ccNumber);
		customer.setAcNumber(acNumber);
		create(customer);
		return find(customer.getCustomerId());
	}
	
	public Customer findByUsername(String username) {
		return (Customer) em.createNamedQuery("Customer.findByUsername").setParameter("username", 
				username).getSingleResult();
	}
	
	public void updateAddress(String username, String address) {
		Query query = em.createNamedQuery("Customer.updateAddress");
		query.setParameter("username", username);
		query.setParameter("address", address);
		query.getSingleResult();
		return;
	}
}
