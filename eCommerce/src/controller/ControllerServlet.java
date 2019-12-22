package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cart.ShoppingCart;
import entity.Category;
import entity.Customer;
import entity.Product;
import entity.ProductDetail;
import session_bean.CategorySessionBean;
import session_bean.CustomerOrderSessionBean;
import session_bean.CustomerSessionBean;
import session_bean.OrderManager;
import session_bean.ProductDetailSessionBean;
import session_bean.ProductSessionBean;
import valid.Validator;

@WebServlet(name = "ControllerServlet", loadOnStartup = 1, urlPatterns = { "/category", "/product", "/addToCart",
		"/viewCart", "/updateCart", "/checkout", "/purchase", "/chooseLanguage", "/logout"})
public class ControllerServlet extends HttpServlet {

	@EJB
	private ProductSessionBean productSessionBean;
	@EJB
	private CategorySessionBean categorySB;
	@EJB
	private ProductSessionBean productSB;
	@EJB
	private ProductDetailSessionBean productDetailSB;
	@EJB
	private OrderManager orderManager;
	@EJB
	private CustomerOrderSessionBean customerOrderSB;
	@EJB
	private CustomerSessionBean customerSB;
	
	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
		getServletContext().setAttribute("newProducts", productSB.findRange(new int[] { 0, 5 }));
		getServletContext().setAttribute("categories", categorySB.findAll());
//		getServletContext().setAttribute("customerOrderList", customerOrderSB.findAll());
//		getServletContext().setAttribute("customerList", customerSB.findAll());
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userPath = request.getRequestURI().substring(request.getContextPath().length());
		if (userPath.equals("/category")) {
			String categoryId = request.getQueryString();
			if (categoryId != null) {
				Category selectedCategory;
				List<Product> categoryProducts;
				selectedCategory = categorySB.find(Integer.parseInt(categoryId));
				session.setAttribute("selectedCategory", selectedCategory);
				System.out.println("log");
				categoryProducts = (List<Product>) selectedCategory.getProducts();
				session.setAttribute("categoryProducts", categoryProducts);
			}
		} else if (userPath.equals("/product")) {
			Product selectedProduct;
			ProductDetail selectedProductDetail;
			String productId = request.getQueryString();
			if (productId != null) {
				selectedProduct = productSB.find(Integer.parseInt(productId));
				selectedProductDetail = productDetailSB.find(Integer.parseInt(productId));
				session.setAttribute("selectedProduct", selectedProduct);
				session.setAttribute("selectedProductDetail", selectedProductDetail);
			}
		} else if (userPath.equals("/logout")) {
//			HttpSession session = request.getSession();
			session.invalidate();
			request.getRequestDispatcher("index.jsp").forward(request, response);
			return;
		} else if (userPath.equals("/viewCart")) {
			String clear = request.getParameter("clear");
			if ((clear != null) && clear.equals("true")) {
				ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
				cart.clear();
			}
		} else if (userPath.equals("/addToCart")) {
			// if user is adding item to cart for first time
			// create cart object and attach it to user session
			ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
			if (cart == null) {
				cart = new ShoppingCart();
				session.setAttribute("cart", cart);
			}
			// get user input from request
			String productId = request.getQueryString();
			if (!productId.isEmpty()) {
				Product product = productSB.find(Integer.parseInt(productId));
				cart.addItem(product);
			}
			String userView = (String) session.getAttribute("view");
			userPath = String.valueOf(userView);
		}

		String url = userPath.trim() + ".jsp";
		try {
			request.getRequestDispatcher(url).forward(request, response);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		String userPath = request.getRequestURI().substring(request.getContextPath().length());
		HttpSession session = request.getSession();
		ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
		Validator validator = new Validator();
		
		if (userPath.equals("/updateCart")) {
			String productId = request.getParameter("productId");
			String quantity = request.getParameter("quantity");
			if (validateQuantity(productId, quantity)) {
				Product product = productSB.find(Integer.parseInt(productId));
				cart.update(product, quantity);
			}
			userPath = "/viewCart";
		}
		else if (userPath.equals("/purchase")) {
			if (cart != null) {
				String username = request.getParameter("username");
				if (isUserExist(username)) {
					Customer user = (Customer) session.getAttribute("user");
					Customer orderUser = customerSB.findByUsername(username);
					String deliveryAddress = request.getParameter("deliveryAddress");
					String paymentMethod = request.getParameter("paymentMethod");
					String ccNumber = request.getParameter("creditcard");
					String acNumber = request.getParameter("atmcard");
					String orderState = "Ordered";
					
					boolean validationErrorFlag = false;
					validationErrorFlag = validator.validateForm(deliveryAddress, paymentMethod, ccNumber, acNumber, cart);
					if (!validationErrorFlag) {
						request.setAttribute("validationErrorFlag",	validationErrorFlag);
						userPath = "/checkout";
					}
					else {
						int orderId = orderManager.placeOrder(orderUser, deliveryAddress, paymentMethod, ccNumber, acNumber, orderState, cart);
						if (orderId != 0) {
							// in case language was set using toggle, get language choice before destroying session
							Locale locale = (Locale) session.getAttribute("javax.servlet.jsp.jstl.fmt.locale.session");
							String language = "";
							if (locale != null) {
								language = (String) locale.getLanguage();
							}
							if (!language.isEmpty()) { //if user changed language using the toggle,
							// reset the language attribute - otherwise
							request.setAttribute("language", language); //language will be switched on confirmation page!
							}
	
							// dissociate shopping cart from session
							cart = null;
							// end session
							session.removeAttribute("cart");
							// get order details
							Map orderMap = orderManager.getOrderDetails(orderId);
				
							// place order details in request scope
							request.setAttribute("customer", orderMap.get("customer"));
							request.setAttribute("products", orderMap.get("products"));
							request.setAttribute("orderRecord", orderMap.get("orderRecord"));
							request.setAttribute("orderedProducts",	orderMap.get("orderedProducts"));
							userPath = "/confirmation";
							
							// otherwise, send back to checkout page and display error
						} else {
							userPath = "/checkout";
							request.setAttribute("orderFailureFlag", true);
						}
					}
				} else {
					String errUsername = "errUsername";
					session.setAttribute("errUsername", errUsername);
					userPath = "checkout";
				}
			}
		}
		String url = userPath + ".jsp";
		try {
			request.getRequestDispatcher(url).forward(request, response);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public boolean validateQuantity(String productId, String quantity) {
		ProductDetail productDetail = productDetailSB.find(Integer.parseInt(productId));
		if (Integer.parseInt(quantity) > productDetail.getQuantity())
			return false;
		return true;
	}
	
	public boolean isUserExist(String username) {
		List<Customer> cus = customerSB.findAll();
		for (Customer c : cus) {
			if (c.getUsername().contentEquals(username)) {
				return true;
			}
		}
		return false;
	}
}
