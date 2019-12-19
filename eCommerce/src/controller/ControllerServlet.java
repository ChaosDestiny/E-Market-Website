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
import entity.Product;
import entity.ProductDetail;
import session_bean.CategorySessionBean;
import session_bean.OrderManager;
import session_bean.ProductDetailSessionBean;
import session_bean.ProductSessionBean;
import valid.Validator;

@WebServlet(name = "ControllerServlet", loadOnStartup = 1, urlPatterns = { "/category", "/product", "/addToCart",
		"/viewCart", "/updateCart", "/checkout", "/purchase", "/login", "/chooseLanguage", "/addproduct", "/logout",
		"/deleteproduct"})
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
	
	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
		getServletContext().setAttribute("newProducts", productSB.findRange(new int[] { 0, 5 }));
		getServletContext().setAttribute("categories", categorySB.findAll());
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
			HttpSession session1 = request.getSession();
			session1.removeAttribute("admin");
			session1.removeAttribute("check");
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
		
		if (request.getParameter("username") != null) {
			String name = request.getParameter("username");
			String password = request.getParameter("pass");
			String check = new String("Ok");
			if (name.equals("admin") && password.equals("admin")) {
				HttpSession session = request.getSession();
				session.setAttribute("admin", name);

				request.getRequestDispatcher("index.jsp").include(request, response);
				return;
			} else {
				HttpSession session = request.getSession();
				session.setAttribute("check", check);
				request.getRequestDispatcher("login.jsp").forward(request, response);
				;
			}
		}
		
		request.setCharacterEncoding("UTF-8");
		String userPath = request.getRequestURI().substring(request.getContextPath().length());
		HttpSession session = request.getSession();
		ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
		Validator validator = new Validator();
		
		if (userPath.equals("/updateCart")) {
			String productId = request.getParameter("productId");
			String quantity = request.getParameter("quantity");
			boolean validEntry = validator.validateQuantity(productId, quantity);
			if (validEntry) {
				Product product = productSB.find(Integer.parseInt(productId));
				cart.update(product, quantity);
			}
//			Product product = productSB.find(Integer.parseInt(productId));
//			cart.update(product, quantity);
			userPath = "/viewCart";
		}
		else if (userPath.equals("/purchase")) {
			if (cart != null) {
				String name = request.getParameter("name");
				String email = request.getParameter("email");
				String phone = request.getParameter("phone");
				String address = request.getParameter("address");
				String cityRegion = request.getParameter("cityRegion");
				String ccNumber = request.getParameter("creditcard");
				
				boolean validationErrorFlag = false;
				validationErrorFlag = validator.validateForm(name, email, phone, address, cityRegion, ccNumber);
				if (!validationErrorFlag) {
					request.setAttribute("validationErrorFlag",	validationErrorFlag);
					userPath = "/checkout";
				}
				else {
					int orderId = orderManager.placeOrder(name, email, phone, address, cityRegion, ccNumber, cart);
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
						// get order details
			
						// dissociate shopping cart from session
						cart = null;
						// end session
						session.invalidate();
						
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
}
