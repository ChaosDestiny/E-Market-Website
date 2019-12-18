package session_bean;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entity.OrderedProduct;

/**
 *
 * @author ThanDieu
 */
@Stateless
public class OrderedProductSessionBean extends AbstractSessionBean<OrderedProduct> {
	@PersistenceContext(unitName = "eMarketPU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public OrderedProductSessionBean() {
		super(OrderedProduct.class);
	}

	public List<OrderedProduct> findByOrderId(Object id) {
		Query q =  em.createNamedQuery("OrderedProduct.findByOrderId").setParameter("orderId", id);
		return q.getResultList();
	}
}