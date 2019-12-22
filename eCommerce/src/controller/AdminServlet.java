package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entity.Customer;
import entity.CustomerOrder;
import session_bean.CustomerSessionBean;
import session_bean.CustomerOrderSessionBean;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet(name = "/AdminServlet", urlPatterns = { "/customerList", "/orderList", "/viewPrf", "/updateOrder"})
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@EJB
	private CustomerSessionBean customerSB;
	@EJB
	private CustomerOrderSessionBean customerOrderSB;
    /**
     * @see HttpServlet#HttpServlet()
     */
	public AdminServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		String userPath = request.getRequestURI().substring(request.getContextPath().length());
		HttpSession session = request.getSession();
		
		if (userPath.equals("/customerList") || userPath.equals("/customerList.jsp")) {
			Customer user  = (Customer) session.getAttribute("user");
			if (user != null && user.getUsername().contentEquals("admin")) {
				List<Customer> cus = customerSB.findAll();
				request.setAttribute("customerList", cus);
				session.setAttribute("ctmList", cus);
				userPath = "customerList";
			} else {
				userPath = "requestNA";
			}
		}
		else if (userPath.equals("/orderList") || userPath.equals("/orderList.jsp")) {
			Customer user  = (Customer) session.getAttribute("user");
			if (user != null && user.getUsername().contentEquals("admin")) {
				List<CustomerOrder> ctmOrders = customerOrderSB.findAll();
				session.setAttribute("ctmOrders", ctmOrders);
				request.setAttribute("customerOrderList", ctmOrders);
				userPath = "orderList";
			} else {
				userPath = "requestNA";
			}
		}
		else if (userPath.equals("/viewPrf") || userPath.equals("/viewPrf.jsp")) {
			Customer user  = (Customer) session.getAttribute("user");
			if (user != null && user.getUsername().contentEquals("admin")) {
				String viewUsername = request.getQueryString();
				Customer viewUser = customerSB.findByUsername(viewUsername);
				session.setAttribute("viewUser", viewUser);
			} else {
				userPath = "requestNA";
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		String userPath = request.getRequestURI().substring(request.getContextPath().length());
		HttpSession session = request.getSession();
		
		if (userPath.equals("/updateOrder")) {
			Customer user  = (Customer) session.getAttribute("user");
			if (user != null && user.getUsername().contentEquals("admin")) {
				int orderId = Integer.parseInt(request.getQueryString());
				CustomerOrder ctmOrder = new CustomerOrder();
				String orderState = request.getParameter("orderState");
				
				ctmOrder = customerOrderSB.find(orderId);
				ctmOrder.setOrderState(orderState);
				
				customerOrderSB.edit(ctmOrder);
				List<CustomerOrder> ctmOrders = customerOrderSB.findAll();
				session.setAttribute("ctmOrders", ctmOrders);

				userPath = "orderList";
			} else {
				userPath = "requestNA";
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
