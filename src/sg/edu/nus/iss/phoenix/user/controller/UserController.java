package sg.edu.nus.iss.phoenix.user.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

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
		request.setAttribute("errors", errors);

		String page = "/pages/home.jsp";
		
		/**
		 * First ensure that the user has privileges to access this screen
		 */
		if(!isUserAuthorized(request)) {
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
			page = "/pages/user/user.jsp";
			if(validateUserInput(request)) {
				
				User user = new User();
				user.setId(request.getParameter("userid"));
				user.setName(request.getParameter("username"));
				user.setPassword(request.getParameter("password"));
				user.setAddress1(request.getParameter("address1"));
				user.setAddress2(request.getParameter("address2"));
				user.setAddress3(request.getParameter("address3"));
				user.setCity(request.getParameter("city"));
				user.setState(request.getParameter("state"));
				user.setCountry(request.getParameter("country"));
				user.setEmailAddress(request.getParameter("email"));
				user.setHomePhone(request.getParameter("homephone"));
				user.setMobile(request.getParameter("mobile"));
				
				String[] roles = request.getParameterValues("role");

				List<Role> systemRoles = userDelegate.getAllRoles();
				List<Role> userRoles = new ArrayList<Role> ();
				for(String role:roles) {
					for(Role systemRole:systemRoles) {
						if(systemRole.getRole().equals(role)) {
							userRoles.add(systemRole);
						}
					}
				}
				
				user.setRoles(userRoles);

				
				try {
					userDelegate.createUser(user);
					
					AppError success = new AppError(AppError.SUCCESS, "success.usersaved", "User created successfully");
					errors.add(success);		
					
					page = "/controller/loaduser";
				} catch (UserAlreadyExistsException e) {
					AppError error = new AppError(AppError.ERROR, "error.nouser", "User already exists in the system");
					errors.add(error);
				}
				catch (Exception e) {
					AppError error = new AppError(AppError.ERROR, "error.nouser", "Failed to create user");
					errors.add(error);
				}
			}
		} else if("modify".equals(selection)) {
			if(validateUserInput(request)) {
				
				User user = new User();
				user.setId(request.getParameter("userid"));
				user.setName(request.getParameter("username"));
				user.setPassword(request.getParameter("password"));
				user.setAddress1(request.getParameter("address1"));
				user.setAddress2(request.getParameter("address2"));
				user.setAddress3(request.getParameter("address3"));
				user.setCity(request.getParameter("city"));
				user.setState(request.getParameter("state"));
				user.setCountry(request.getParameter("country"));
				user.setEmailAddress(request.getParameter("email"));
				user.setHomePhone(request.getParameter("homephone"));
				user.setMobile(request.getParameter("mobile"));
				
				String[] roles = request.getParameterValues("role");

				List<Role> systemRoles = userDelegate.getAllRoles();
				List<Role> userRoles = new ArrayList<Role> ();
				for(String role:roles) {
					for(Role systemRole:systemRoles) {
						if(systemRole.getRole().equals(role)) {
							userRoles.add(systemRole);
						}
					}
				}
				
				user.setRoles(userRoles);
			
				try {
					userDelegate.modifyUser(user);
					
					AppError success = new AppError(AppError.SUCCESS, "success.usersaved", "User modified successfully");
					errors.add(success);		
					
					page = "/controller/loaduser";					
				} catch (UserAlreadyExistsException e) {
					AppError error = new AppError(AppError.ERROR, "error.duplicateuser", "User already exists in the system");
					errors.add(error);
				} catch (UserNotFoundException e) {
					AppError error = new AppError(AppError.WARNING,"error.nouser", "User not found in the system");
					errors.add(error);
				} catch (Exception e) {
					AppError error = new AppError(AppError.ERROR,"error.nouser", "Failed to modify User");
					errors.add(error);
				}
			}
		}  else if("input".equals(selection)) {
			boolean insertUser = Boolean.valueOf(request.getParameter("insert"));
			String action = request.getParameter("action");
			
			List<Role> roles = userDelegate.getAllRoles();
			request.setAttribute("systemroles", roles);	
			request.setAttribute("countries", getCountryList());

			if(!insertUser) {
				User user;
				try {
					user = userDelegate.getUser(request.getParameter("userid"));
					request.setAttribute("selecteduser", user);				
					
					/**
					 * TODO: If delete, check if the user is assigned to any program
					 */
					
					if("delete".equals(action)) {
						AppError error = new AppError(AppError.WARNING,"warning.deleteuser", "You are about to delete the user");
						errors.add(error);
					}					
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
		} else if("delete".equals(selection)) {
			User user;
			boolean insertUser = Boolean.valueOf(request.getParameter("insert"));
			String action = request.getParameter("action");
			
			List<Role> roles = userDelegate.getAllRoles();
			request.setAttribute("systemroles", roles);	
			request.setAttribute("countries", getCountryList());

			try {
				user = userDelegate.getUser(request.getParameter("userid"));
				request.setAttribute("selecteduser", user);				
				
				/**
				 * TODO: If delete, check if the user is assigned to any program
				 */
				user = new User();
				user.setId(request.getParameter("userid"));
				userDelegate.deleteUser(user);
				
				AppError success = new AppError(AppError.SUCCESS, "success.usersaved", "User Deleted successfully");
				errors.add(success);		
				
				page = "/controller/loaduser";
			
			} catch (UserNotFoundException e) {
				AppError error = new AppError(AppError.WARNING,"error.nouser", "User not found in the system");
				errors.add(error);
			} catch (Exception e) {
				AppError error = new AppError(AppError.ERROR,"error.nouser", "Failed to delete user");
				errors.add(error);
			}
		}
		RequestDispatcher rd = request
					.getRequestDispatcher(page);
		rd.forward(request, response);		
	}	
	
	/**
	 * Check if the user has access to the given function
	 * 
	 * @param httpRequest
	 * @return
	 */
	private boolean isUserAuthorized(HttpServletRequest httpRequest) {
		
		/**
		 * First ensure that the user has privileges to access this screen
		 */
		HttpSession session = httpRequest.getSession(false);
		if((session == null) ||
					(session.getAttribute("user") == null)) {
			return false;
		} else {
			User currentUser = (User)session.getAttribute("user");
			List<Role> roles = currentUser.getRoles();
			if(roles != null) {
				for(Role role:roles) {
					if("admin".equals(role.getRole())) {
						return true;
					}
				}
			} else {
				return false;	
			}
		}
			
		return false;
	}
	
	/**
	 * Validate the user inputs
	 * 
	 * @param httpRequest
	 * @return
	 */
	private boolean validateUserInput(HttpServletRequest httpRequest) {
		List<AppError> errors = (List<AppError>)httpRequest.getAttribute("errors");
		if(errors == null) {
			errors = new ArrayList<AppError>();
		}
		boolean status = true;
		
		if(httpRequest.getParameter("userid") == null || httpRequest.getParameter("userid").trim() == "") {
			AppError error = new AppError(AppError.WARNING,"error.nouserid", "User id is missing");
			errors.add(error);			
			status = false;
		}
		
		if(httpRequest.getParameter("username") == null || httpRequest.getParameter("username").trim() == "") {
			AppError error = new AppError(AppError.WARNING,"error.nousername", "User name is missing");
			errors.add(error);			
			status = false;
		}
		
		if(httpRequest.getParameterValues("role") == null) {
			AppError error = new AppError(AppError.WARNING,"error.norole", "Role is missing");
			errors.add(error);			
			status = false;
		}		
		httpRequest.setAttribute("errors", errors);
		
		return status;
	}
	
	/**
	 * Return the list of countries
	 * 
	 * @return	list of countries
	 */
	private String[][] getCountryList() {
		String[] countryCodes = Locale.getISOCountries();
        Arrays.sort(countryCodes);
        
        String[][] resultList = new String[countryCodes.length][countryCodes.length];
        
        int idx = 0;
        for (String cc : countryCodes) {
            resultList[idx][0] = cc;
            resultList[idx++][1] = (new Locale("",cc)).getDisplayCountry();		
        }
        return resultList;
	}
}
