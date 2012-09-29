package sg.edu.nus.iss.phoenix.core.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PhoenixFrontController
 */
public class PhoenixFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PhoenixFrontController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	/**
	 * Process requests from clients.
	 */
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String pathInfo = request.getPathInfo();
		String action = FCUtilities.stripPath(pathInfo);
		System.out.println("PATH" + pathInfo);
		System.out.println("ACTION" + action);
		String result = chooseUseCase(action);
		RequestDispatcher rd = getServletContext().getRequestDispatcher(result);
		rd.forward(request, response);
	}

	private String chooseUseCase(String action) {
		if("login".equals(action)) {
			return "/LoginController/login";
		} else if("searchrp".equals(action)) {
			return "/ProcessController/search";
		} else if("setuprp".equals(action)) {
			return "/ProcessController/process";
		} else if("crudrp".equals(action)) {
			return "/CRUDRpController";
		} else if("loadrp".equals(action)) {
			return "/ProcessController/load";	
		} else if("deleterp".equals(action)) {
			return "/ProcessController/delete";
		} else if("createuser".equals(action)) {
			return "/UserController/create";
		} else if("modifyuser".equals(action)) {
			return "/UserController/modify";
		} else if("loaduser".equals(action)) {
			return "/UserController/load";
		} else if("inputuser".equals(action)) {
			return "/UserController/input";
		} else if("scheduledprogram".equals(action)) {
			return "/ReviewSelectScheduledProgramController/scheduled";
		}  else if("searchschedule".equals(action)) {
			return "/ReviewSelectScheduledProgramController/search";
		} else if("logout".equals(action)) {
			return "/LoginController/logout";
		} else {
			return "/welcome.jsp";
		}
	}

}
