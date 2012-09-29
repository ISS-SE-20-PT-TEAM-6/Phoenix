package sg.edu.nus.iss.phoenix.maintainschedule.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sg.edu.nus.iss.phoenix.core.controller.FCUtilities;
import sg.edu.nus.iss.phoenix.maintainschedule.delegate.ScheduleDelegate;
import sg.edu.nus.iss.phoenix.maintainschedule.entity.Schedule;
import sg.edu.nus.iss.phoenix.maintainschedule.entity.ScheduleSearchObject;
import sg.edu.nus.iss.phoenix.user.exceptions.DAOException;

/**
 * Servlet implementation class ReviewSelectScheduledProgramController
 */
@WebServlet("/ReviewSelectScheduledProgramController/*")
public class ReviewSelectScheduledProgramController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ScheduleDelegate scheduleDelegate;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReviewSelectScheduledProgramController() {
        super();
        initializeDelegate();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			doProcess(request, response);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			doProcess(request, response);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void doProcess(HttpServletRequest request,
			HttpServletResponse response) throws DAOException {
		String searchSchedule = "/pages/schedule/scheduledprogram.jsp";
		String maintain = "/pages/home.jsp";
		String selection = FCUtilities.stripPath(request.getPathInfo()).toLowerCase();
		RequestDispatcher rd = request.getRequestDispatcher(searchSchedule);
		
		try 
		{
			if(selection.equals("scheduled")){
				request.getSession().removeAttribute("searchrpslist");
				request.getSession().removeAttribute("selectedschedule");
				rd.forward(request, response);
			}else if(selection.equals("search")){
				
				ScheduleSearchObject sso = new ScheduleSearchObject();
				sso.setProgramName(request.getParameter("programName"));
				sso.setStartDate(request.getParameter("startdate"));
				sso.setEndDate(request.getParameter("enddate"));
				
				try {
					List<Schedule> results = scheduleDelegate.searchProgramSlots(sso);
					request.getSession().setAttribute("searchrpslist", results);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				rd.forward(request, response);
			}else if(selection.equals("scheduleselected")){
				String scheduleID = request.getParameter("scheduleID");
				System.out.println(scheduleID);
				if(!scheduleID.isEmpty()){
					Schedule schedule = scheduleDelegate.searchProgram(scheduleID);
					request.getSession().setAttribute("selectedschedule", schedule);
				}
				RequestDispatcher rdmaintain = request.getRequestDispatcher(maintain);
				rdmaintain.forward(request, response);
			}
		} 
		catch (ServletException e) 
		{
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void initializeDelegate(){
		scheduleDelegate = new ScheduleDelegate();
	}

}
