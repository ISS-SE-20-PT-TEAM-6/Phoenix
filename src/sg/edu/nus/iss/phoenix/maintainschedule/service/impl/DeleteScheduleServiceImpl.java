package sg.edu.nus.iss.phoenix.maintainschedule.service.impl;
import java.sql.SQLException;
import java.util.logging.Logger;

import sg.edu.nus.iss.phoenix.core.dao.DAOFactoryImpl;
import sg.edu.nus.iss.phoenix.core.exceptions.SystemException;
import sg.edu.nus.iss.phoenix.maintainschedule.service.ScheduleService;
import sg.edu.nus.iss.phoenix.maintainschedule.entity.Schedule;
import sg.edu.nus.iss.phoenix.maintainschedule.dao.*;
//import sg.edu.nus.iss.phoenix.maintainschedule.dao.impl.*;
import sg.edu.nus.iss.phoenix.core.exceptions.*;


public class DeleteScheduleServiceImpl implements ScheduleService{
	DAOFactoryImpl factory;
	ScheduleDao scheduleDao;
	Logger log = Logger.getLogger(getClass().getName());
	
	public DeleteScheduleServiceImpl()
	{
		factory = new DAOFactoryImpl();
		scheduleDao = factory.getScheduleDAO();
	}
	public void maintainSchedule(Schedule schedule)
	{
		try 
		{
			scheduleDao.delete(schedule);
		} catch (NotFoundException e){
			log.severe("Schedule not existing while delete schedule :"+ e.getMessage());
			throw new SystemException(e);
		} catch (SQLException e) {
			log.severe("Error occured while delete schedule :"+ e.getMessage());
			throw new SystemException(e);
		} 
	}
}
