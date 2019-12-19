package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entity.Customer;
import session_bean.CustomerSessionBean;
import session_bean.ProductSessionBean;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet(name = "/RegisterServlet", urlPatterns = { "/register"})
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@EJB
	private CustomerSessionBean customerSB;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @param userExist 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String userPath = request.getRequestURI().substring(request.getContextPath().length());
		HttpSession session = request.getSession();
		
		if (userPath.equals("/register")) {
			PrintWriter out = response.getWriter();
			String name = request.getParameter("name");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String email = request.getParameter("email");
			String phone = request.getParameter("phone");
			String address = request.getParameter("address");
			String cityRegion = request.getParameter("cityRegion");
			String ccNumber = request.getParameter("creditcard");

			if (isUserExist(username)) {
				String userExist = null;
				session.setAttribute("userExist", userExist);
				userPath = "/register";
			}
			else {
				Customer customer = customerSB.addCustomer(name, username, 
						password, email, phone, address, cityRegion, ccNumber);
				
				out.print("<script type=\"text/javascript\">\r\n" + "		alert('Register successfully!');\r\n"
						+ "	</script>");
				
				userPath = "/index";
			}
//			out.print("<script type=\"text/javascript\">\r\n" + "		alert('Register successfully!');\r\n"
//					+ "	</script>");
//			userPath = "/index";
		}
		String url = userPath.trim() + ".jsp";
		try {
			request.getRequestDispatcher(url).forward(request, response);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
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
