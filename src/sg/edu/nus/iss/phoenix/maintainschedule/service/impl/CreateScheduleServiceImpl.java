package sg.edu.nus.iss.phoenix.maintainschedule.service.impl;
import java.sql.SQLException;
import java.util.logging.Logger;

import sg.edu.nus.iss.phoenix.core.dao.DAOFactoryImpl;
import sg.edu.nus.iss.phoenix.core.exceptions.SystemException;
import sg.edu.nus.iss.phoenix.maintainschedule.service.ScheduleService;
import sg.edu.nus.iss.phoenix.maintainschedule.entity.Schedule;
import sg.edu.nus.iss.phoenix.maintainschedule.dao.*;
//import sg.edu.nus.iss.phoenix.maintainschedule.dao.impl.*;


public class CreateScheduleServiceImpl implements ScheduleService{
	DAOFactoryImpl factory;
	ScheduleDao scheduleDao;
	Logger log = Logger.getLogger(getClass().getName());
	
	public CreateScheduleServiceImpl()
	{
		factory = new DAOFactoryImpl();
		scheduleDao = factory.getScheduleDAO();
	}
	public void maintainSchedule(Schedule schedule)
	{
		try 
		{
			scheduleDao.create(schedule);
		} catch (SQLException e) {
			log.severe("Error occured while create new schedule :"+ e.getMessage());
			throw new SystemException(e);
		}
	}
}
