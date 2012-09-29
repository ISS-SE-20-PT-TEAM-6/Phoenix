package sg.edu.nus.iss.phoenix.core.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sg.edu.nus.iss.phoenix.core.delegate.PresenterProducerDelegate;
import sg.edu.nus.iss.phoenix.user.entity.User;



public class ReviewSelectPresenterProducerController extends HttpServlet {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
		
		try{
			if(action.equals("select")){
				//request.setAttribute("programList", getProgramBeanList());
				List<User> presenterList = new PresenterProducerDelegate().searchPresenterProducer(); 
				
				request.setAttribute("presenterList", presenterList);
			}
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/pages/SearchPresenterProducer.jsp");
			rd.forward(request, response);
		}catch(Exception exp){
			
		}
		
	}

}
