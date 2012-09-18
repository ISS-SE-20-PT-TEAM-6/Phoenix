package sg.edu.nus.iss.phoenix.user.controller;

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

import sg.edu.nus.iss.phoenix.core.controller.FCUtilities;
import sg.edu.nus.iss.phoenix.core.ui.AppError;
import sg.edu.nus.iss.phoenix.user.delegate.UserDelegate;
import sg.edu.nus.iss.phoenix.user.entity.Role;
import sg.edu.nus.iss.phoenix.user.entity.User;
import sg.edu.nus.iss.phoenix.user.exceptions.UserAlreadyExistsException;
import sg.edu.nus.iss.phoenix.user.exceptions.UserNotFoundException;

/**
 * Servlet implementation class UserController
 */
@WebServlet("/UserController/*")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDelegate userDelegate;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserController() {
        super();
        userDelegate = new UserDelegate();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String selection = FCUtilities.stripPath(request.getPathInfo())
				.toLowerCase();
		List<AppError> errors = new ArrayList<AppError> ();
		String page = "/pages/home.jsp";
		
		/**
		 * First ensure that the user has privileges to access this screen
		 */
		HttpSession session = request.getSession(false);
		boolean notAuthorized = true;
		if((session == null) ||
					(session.getAttribute("user") == null)) {
			notAuthorized = true;
		} else {
			User currentUser = (User)session.getAttribute("user");
			List<Role> roles = currentUser.getRoles();
			if(roles != null) {
				for(Role role:roles) {
					if("admin".equals(role.getRole())) {
						notAuthorized = false;
						break;
					}
				}
			} else {
				notAuthorized = true;			
			}
		}
		
		if(notAuthorized) {
			AppError error = new AppError("error.noPrivilege","You are not logged in or you do not have privileges");
			errors.add(error);
			request.setAttribute("errors", errors);
			RequestDispatcher rd = request
						.getRequestDispatcher(page);
			rd.forward(request, response);		
			return;
		}
		
		/**
		 * Now based on individual flow
		 */
		if("create".equals(selection)) {
			User user = new User();
			user.setId(request.getParameter("userid"));
			user.setName(request.getParameter("username"));
			user.setPassword(request.getParameter("password"));
			
			try {
				userDelegate.createUser(user);
			} catch (UserAlreadyExistsException e) {
				AppError error = new AppError(AppError.ERROR, "error.nouser", "User already exists in the system");
				errors.add(error);
			}
			page = "/pages/user/user.jsp";
		} else if("modify".equals(selection)) {
			User user = new User();
			user.setId(request.getParameter("userid"));
			user.setName(request.getParameter("username"));
			user.setPassword(request.getParameter("password"));
			
			try {
				userDelegate.modifyUser(user);
			} catch (UserAlreadyExistsException e) {
				AppError error = new AppError(AppError.ERROR, "error.duplicateuser", "User already exists in the system");
				errors.add(error);
			} catch (UserNotFoundException e) {
				AppError error = new AppError(AppError.WARNING,"error.nouser", "User not found in the system");
				errors.add(error);
			}
			page = "/pages/user/user.jsp";
		}  else if("input".equals(selection)) {
			boolean insertUser = Boolean.valueOf(request.getParameter("insert"));
			
			if(!insertUser) {
				User user;
				try {
					user = userDelegate.getUser(request.getParameter("userid"));
					request.setAttribute("selecteduser", user);
				} catch (UserNotFoundException e) {
					AppError error = new AppError(AppError.WARNING,"error.nouser", "User not found in the system");
					errors.add(error);
				}
			}
			
			page = "/pages/user/user.jsp";
		} else if("load".equals(selection)) {
			List<User> users = userDelegate.getAllUsers();
			request.setAttribute("users", users);
			
			page = "/pages/user/userlist.jsp";
		}
		
		request.setAttribute("errors", errors);
		RequestDispatcher rd = request
					.getRequestDispatcher(page);
		rd.forward(request, response);		
	}	

}
