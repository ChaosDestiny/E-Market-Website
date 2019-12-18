package session_bean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entity.Category;
import entity.Product;

/**
 *
 * @author ThanDieu
 */
@Stateless
public class ProductSessionBean extends AbstractSessionBean<Product> {
	@PersistenceContext(unitName = "eMarketPU")
	private EntityManager em;
	@EJB
	private CategorySessionBean categorySB;
	public EntityManager getEntityManager() {
		return em;
	}

	public ProductSessionBean() {
		super(Product.class);
	}

	public void deleteProduct(int prodID) {
		em.createNativeQuery("update product set status = 0 where productId = ?")
				.setParameter(1, prodID).executeUpdate();
		em.createQuery("update Product c set c.status = :status where c.productId = :productId")
				.setParameter("status", false).setParameter("prodID", prodID).executeUpdate();
	}

	@Override
	public void remove(Product p) {
		p = getEntityManager().merge(p);
		Category c = p.getCategory();
		super.remove(p);
		categorySB.getEntityManager().refresh(c);
	}
	
	@Override 
	public void create(Product p) {
		super.create(p);
		Category c = categorySB.find(p.getCategory().getCategoryId());
//		c.addProduct(p);
		categorySB.getEntityManager().refresh(c);
	}
}