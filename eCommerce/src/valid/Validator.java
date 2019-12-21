package valid;

import javax.ejb.EJB;

import cart.ShoppingCart;
import entity.Product;
import entity.ProductDetail;
import session_bean.CustomerSessionBean;
import session_bean.ProductDetailSessionBean;
import session_bean.ProductSessionBean;

public class Validator {
	@EJB
	private ProductSessionBean productSB;
	@EJB
	private ProductDetailSessionBean productDetailSB;
	@EJB
	private CustomerSessionBean customerSB;
	
	public Validator() {
		
	}
	public boolean isNumberic(String str) { 
		  try {  
		    Double.parseDouble(str);  
		    return true;
		  } catch(NumberFormatException e){  
		    return false;  
		  }  
		}
	
	public boolean validateForm(String deliveryAddress, String paymentMethod, String ccNumber, String acNumber,
			ShoppingCart cart) {
		// TODO Auto-generated method stub
		return true;
	}
}
