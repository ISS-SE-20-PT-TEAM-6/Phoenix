package sg.edu.nus.iss.phoenix.maintainschedule.service;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import sg.edu.nus.iss.phoenix.core.dao.DAOFactoryImpl;
import sg.edu.nus.iss.phoenix.maintainschedule.dao.ScheduleDao;
import sg.edu.nus.iss.phoenix.maintainschedule.entity.Schedule;
import sg.edu.nus.iss.phoenix.maintainschedule.entity.ScheduleSearchObject;
import sg.edu.nus.iss.phoenix.radioprogram.dao.RadioProgramDAO;
import sg.edu.nus.iss.phoenix.user.dao.UserDao;

public class ScheduleSerivce {
	
	private DAOFactoryImpl factory;
	private ScheduleDao scheduleDAO;
	private UserDao userdao;
	private RadioProgramDAO rdao;
	
	public ScheduleSerivce(){
		factory = new DAOFactoryImpl();
		scheduleDAO = factory.getScheduleDAO();
		userdao = factory.getUserDAO();
		rdao = factory.getRadioProgramDAO();
	}

	public List<Schedule> searchProgram(ScheduleSearchObject searchObj) throws ParseException, SQLException {
		if(searchObj.getEndDate().isEmpty() && !searchObj.getStartDate().isEmpty()){
			
			DateFormat df = new SimpleDateFormat("mm/dd/yyyy");
			Date d = df.parse(searchObj.getStartDate()); 
			return scheduleDAO.loadWeekly(d);
		}else{
			return scheduleDAO.loadAll();
		}
	}

}
