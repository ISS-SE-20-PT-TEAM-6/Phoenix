package sg.edu.nus.iss.phoenix.maintainschedule.controller;

import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sg.edu.nus.iss.phoenix.maintainschedule.delegate.ScheduleDelegate;
import sg.edu.nus.iss.phoenix.maintainschedule.entity.Schedule;

public class ScheduleController extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	public ScheduleController(){
		super();
	}
	
	protected void doGet(HttpServletRequest request,
	HttpServletResponse response) throws ServletException, IOException {
			processRequest(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException,IOException{
		processRequest(request, response);
	}
	
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException,IOException{
		
		System.out.println("ScheduleController : Inside the processRequest method");
		String option=request.getParameter("option");
		
		try{
			if(option.equals("create") || option.equals("createByCopy") ){
				Schedule schedule = new Schedule();
				schedule.setScheduleID(request.getParameter("scheduleId"));
				schedule.setProgramName(request.getParameter("programName"));
				schedule.setProgramDate(new 
						SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("programDate")));
				schedule.setPresenter(request.getParameter("presenterName"));
				schedule.setProducer(request.getParameter("producerName"));
				schedule.setStartTime(Time.valueOf(request.getParameter("startTime")+":00"));
				schedule.setEndTime(Time.valueOf(request.getParameter("endTime")+":00"));
				new ScheduleDelegate(option).maintainSchedule(schedule);
			}else {
				Schedule schedule = new Schedule();
				schedule.setScheduleID(request.getParameter("scheduleId"));
				new ScheduleDelegate(option).maintainSchedule(schedule);
			}
			
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/pages/Success.jsp");
			
			rd.forward(request, response);
			
		}catch(Exception exp){
			throw new ServletException(exp);
		}
		
	}
}
