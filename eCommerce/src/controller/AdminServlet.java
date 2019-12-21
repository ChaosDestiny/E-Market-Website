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
@WebServlet(name = "/AdminServlet", urlPatterns = { "/customerList", "/orderList", "/viewPrf"})
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
		
		if (userPath.equals("/customerList")) {
			List<Customer> cus = customerSB.findAll();
			session.setAttribute("ctmList", cus);
		}
		else if (userPath.equals("/orderList")) {
			List<CustomerOrder> ctmOrders = customerOrderSB.findAll();
			session.setAttribute("ctmOrders", ctmOrders);
			userPath = "orderList";
		}
		else if (userPath.equals("/viewPrf")) {
			String viewUsername = request.getQueryString();
			Customer viewUser = customerSB.findByUsername(viewUsername);
			session.setAttribute("viewUser", viewUser);
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
	}

}
