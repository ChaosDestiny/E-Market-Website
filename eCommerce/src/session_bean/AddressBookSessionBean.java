package session_bean;

import entity.AddressBook;
import entity.Customer;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ThanDieu
 */
@Stateless
public class AddressBookSessionBean extends AbstractSessionBean<AddressBook> {
	@PersistenceContext(unitName = "eMarketPU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public AddressBookSessionBean() {
		super(AddressBook.class);
	}

	public AddressBook findByCustomer(Object customer) {
		return (AddressBook) em.createNamedQuery("AddressBook.findByCustomer").setParameter("customer", customer)
				.getSingleResult();
	}

	public AddressBook addAddressBook(Customer newCtm, String address, String cityRegion, String phone) {
		AddressBook addressBook = new AddressBook();
		int id = findAll().size() + 1;
		for (AddressBook a : findAll()) {
			if (a.getAddressId() >= id) {
				id = a.getAddressId() + 1;
			}
		}
		addressBook.setAddressId(id);
		addressBook.setCustomer(newCtm);
		addressBook.setAddress(address);
		addressBook.setCity(cityRegion);
		addressBook.setPhone(phone);
		create(addressBook);
		return find(addressBook.getAddressId());
	}
}