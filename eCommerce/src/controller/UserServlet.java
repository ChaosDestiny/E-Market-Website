package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entity.AddressBook;
import entity.Category;
import entity.Customer;
import entity.CustomerOrder;
import entity.Product;
import session_bean.AddressBookSessionBean;
import session_bean.CustomerOrderSessionBean;
import session_bean.CustomerSessionBean;
import session_bean.OrderManager;
import session_bean.ProductSessionBean;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet(name = "/UserServlet", urlPatterns = { "/editProfile", "/changePass", "/profile", "/purchaseHistory", 
		"/setDefault", "/addAddress", "/delAddress"})
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@EJB
	private CustomerSessionBean customerSB;
	@EJB
	private CustomerOrderSessionBean customerOrderSB;
	@EJB
	private OrderManager orderManager;
	@EJB
	private ProductSessionBean productSB;
	@EJB
	private AddressBookSessionBean addressBookSB;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userPath = request.getRequestURI().substring(request.getContextPath().length());
		if (userPath.equals("/profile")) {
			userPath = "profile";
		}
		else if (userPath.equals("/purchaseHistory")) {
			response.setContentType("text/html;charset=UTF-8");
			Customer customer = (Customer) session.getAttribute("user");
			
			if (customer != null) {
				int orderId = Integer.parseInt(request.getQueryString());
				Map orderMap = orderManager.getOrderDetails(orderId);
				
				// place order details in request scope
				request.setAttribute("customer", orderMap.get("customer"));
				request.setAttribute("products", orderMap.get("products"));
				request.setAttribute("orderRecord", orderMap.get("orderRecord"));
				request.setAttribute("orderedProducts",	orderMap.get("orderedProducts"));
				userPath = "/purchaseHistory";
			} else {
				userPath = "login";
			}
		}
		else if (userPath.equals("/setDefault")) {
			response.setContentType("text/html;charset=UTF-8");
			Customer customer = (Customer) session.getAttribute("user");
			
			if (customer != null) {
				int addressId = Integer.parseInt(request.getQueryString());
				AddressBook addressBook = addressBookSB.find(addressId);
				
				Customer c = new Customer();
				c = customer;
				c.setAddress(addressBook.getAddress());
				c.setCityRegion(addressBook.getCity());
				c.setPhone(addressBook.getPhone());
				customerSB.edit(c);
				session.setAttribute("user", c);
				userPath = "profile";
			} else {
				userPath = "login";
			}
		}
		else if (userPath.equals("/delAddress")) {
			response.setContentType("text/html;charset=UTF-8");
			Customer customer = (Customer) session.getAttribute("user");
			
			if (customer != null) {
				int addressId = Integer.parseInt(request.getQueryString());
				AddressBook addressBook = addressBookSB.find(addressId);
				addressBookSB.remove(addressBook);
				customer.removeAddressBook(addressBook);
				session.setAttribute("user", customer);

				userPath = "/profile";
				return;
			} else {
				userPath = "login";
			}
		}
		String url = userPath.trim() + ".jsp";
		try {
			request.getRequestDispatcher(url).forward(request, response);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String userPath = request.getRequestURI().substring(request.getContextPath().length());
		HttpSession session = request.getSession();
		
		if (userPath.equals("/editProfile")) {
			response.setContentType("text/html;charset=UTF-8");
			Customer customer = (Customer) session.getAttribute("user");
			
			//get info from request
			
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String phone = request.getParameter("phone");
			String address = request.getParameter("address");
			String cityRegion = request.getParameter("cityRegion");
			String ccNumber = request.getParameter("creditcard");
			String acNumber = request.getParameter("atmcard");
			int customerId = customer.getCustomerId();
			String username = customer.getUsername();
			String password = customer.getPassword();
			List<CustomerOrder> customerOrders = customer.getCustomerOrders();
			
			Customer c = new Customer();
			//set for user
			c.setCustomerId(customerId);
			c.setUsername(username);
			c.setPassword(password);
			c.setName(name);
			c.setEmail(email);
			c.setPhone(phone);
			c.setAddress(address);
			c.setCityRegion(cityRegion);
			c.setCcNumber(ccNumber);
			c.setAcNumber(acNumber);
			c.setCustomerOrders(customerOrders);

			customerSB.edit(c);
			session.setAttribute("user", c);
				
			userPath = "/profile";
		}
		else if (userPath.equals("/changePass")) {
			response.setContentType("text/html;charset=UTF-8");
			Customer customer = (Customer) session.getAttribute("user");
			
			String curPass = (String) request.getParameter("curPass");
			String newPass = (String) request.getParameter("newPass");
			String checkPass = (String) request.getParameter("checkPass");
			
			if (!customer.getPassword().contentEquals(curPass)) {
				session.setAttribute("errPass", "curPass");
				request.getRequestDispatcher("profile.jsp").forward(request, response);
			} else if (!checkPass.equals(newPass)) {
				session.setAttribute("errPass", "newPass");
				request.getRequestDispatcher("profile.jsp").forward(request, response);
			} else {
				session.setAttribute("errPass", "success");
				Customer c = new Customer();
				c = customer;
				c.setPassword(newPass);
				customerSB.edit(c);
				request.getRequestDispatcher("profile.jsp").forward(request, response);
			}
			userPath = "/profile";
		}
		
		else if (userPath.equals("/addAddress")) {
			response.setContentType("text/html;charset=UTF-8");
			Customer customer = (Customer) session.getAttribute("user");
			
			if (customer != null) {
				String address = (String) request.getParameter("address");
				String city = (String) request.getParameter("city");
				String phone = (String) request.getParameter("phone");
				
				AddressBook addressBook = addressBookSB.addAddressBook(customer, address, city, phone);
				customer.addAddressBook(addressBook);
				userPath = "profile";
			} else {
				userPath = "login";
			}
		}
		String url = userPath.trim() + ".jsp";
		try {
			request.getRequestDispatcher(url).forward(request, response);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
