package valid;

import javax.ejb.EJB;

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
	public boolean validateForm(String name, String email, String phone, String address, String cityRegion, String ccNumber) {
		if (name == null)
			return false;
		if (email == null)
			return false;
		if (phone == null)
			return false;
		if (address == null)
			return false;
		if (cityRegion == null)
			return false;
		if (ccNumber == null)
			return false;

		return true;
	}
	
	public boolean validateQuantity(String productId, String quantity) {
		
		if (Integer.parseInt(quantity) > 100)
			return false;
		return true;
	}
	
}
