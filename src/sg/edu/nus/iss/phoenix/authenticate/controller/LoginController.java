package sg.edu.nus.iss.phoenix.authenticate.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sg.edu.nus.iss.phoenix.authenticate.delegate.AuthenticateDelegate;
import sg.edu.nus.iss.phoenix.core.controller.FCUtilities;
import sg.edu.nus.iss.phoenix.core.ui.AppError;
import sg.edu.nus.iss.phoenix.user.entity.User;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/LoginController/*")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public LoginController() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}
	 /**
     * Process requests from front controller. 
     */
	 protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (FCUtilities.stripPath(request.getPathInfo()).equalsIgnoreCase("login") ) {
			AuthenticateDelegate ad = new AuthenticateDelegate();
			User user = new User();
			user.setId(request.getParameter("id"));
			user.setPassword(request.getParameter("password"));
			user = ad.validateUserIdPassword(user);
			String page = "/pages/home.jsp";
			if(user != null) {
				request.getSession().setAttribute("user", user);
			} else {
				List<AppError> errors = new ArrayList<AppError>();
				AppError error = new AppError(AppError.ERROR, "error.invalidlogin", "Invalid username or password");
				errors.add(error);				
				request.setAttribute("errors", errors);
			}
			RequestDispatcher rd = getServletContext().getRequestDispatcher(page);
		    rd.forward(request, response);
		} else {
			 HttpSession session = request.getSession();
		     session.invalidate();
		     RequestDispatcher rd = getServletContext().getRequestDispatcher("/pages/logout.jsp");;
			 rd.forward(request, response);
		}
		
	 }

}
