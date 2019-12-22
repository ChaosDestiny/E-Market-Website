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

import entity.AddressBook;
import entity.Customer;
import session_bean.AddressBookSessionBean;
import session_bean.CustomerSessionBean;

/**
 * Servlet implementation class SignUpServlet
 */
@WebServlet(name = "/SignUpServlet", urlPatterns = { "/signUp", "/login"})
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@EJB
	private CustomerSessionBean customerSB;
	@EJB
	private AddressBookSessionBean addressBookSB;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUpServlet() {
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
		
		if (userPath.equals("/signUp")) {
			PrintWriter out = response.getWriter();
			
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String phone = request.getParameter("phone");
			String address = request.getParameter("address");
			String cityRegion = request.getParameter("cityRegion");
			String ccNumber = request.getParameter("creditcard");
			String acNumber = request.getParameter("atmcard");

			String userExist = null;
			session.setAttribute("userExist", userExist);
			if (isUserExist(username)) {
				userExist = "exist";
				session.setAttribute("userExist", userExist);
				userPath = "/signUp";
			}
			else {
				Customer newCtm = customerSB.addCustomer(name, username, password, email, phone, 
						address, cityRegion, ccNumber, acNumber);
				AddressBook addressBook = addressBookSB.addAddressBook(newCtm, address, cityRegion, phone);
				newCtm.addAddressBook(addressBook);
				customerSB.edit(newCtm);
				out.print("<script type=\"text/javascript\">\r\n" + "		alert('Sign Up successfully!');\r\n"
						+ "	</script>");
				
				userPath = "/index";
			}
		}
		else if (userPath.equals("/login")) {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String errorAccount = new String("errorAccount");
			String errorPass = new String("errorPass");
			session = request.getSession();
			
			if (username == null) {
				session = request.getSession();
				session.setAttribute("errorAccount", errorAccount);
				userPath = "login";
			} else if (!isUserExist(username)) {
				session = request.getSession();
				session.setAttribute("errorAccount", errorAccount);
				userPath = "login";
			} else if (!isUser(username, password)) {
				session = request.getSession();
				session.setAttribute("errorPass", errorPass);
				userPath = "login";
			} else {
				Customer user = customerSB.findByUsername(username);
				session = request.getSession();
				session.setAttribute("user", user);
				if (username.equals("admin") && password.equals("admin")) {
					session.setAttribute("admin", username);
				} else {
					session.setAttribute("customer", username);
				}
				userPath = "index";
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
	
	public boolean isUserExist(String username) {
		List<Customer> cus = customerSB.findAll();
		for (Customer c : cus) {
			if (c.getUsername().contentEquals(username)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isUser(String user, String pass) {
		List<Customer> cus = customerSB.findAll();
		for (Customer c : cus) {
			if (c.getUsername().contentEquals(user) && c.getPassword().contentEquals(pass)) {
				return true;
			}
		}
		return false;
	}
}
