package sg.edu.nus.iss.phoenix.maintainschedule.service.impl;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import sg.edu.nus.iss.phoenix.core.dao.DAOFactoryImpl;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;
import sg.edu.nus.iss.phoenix.core.exceptions.OverlapException;
import sg.edu.nus.iss.phoenix.core.exceptions.SystemException;
import sg.edu.nus.iss.phoenix.maintainschedule.service.ScheduleService;
import sg.edu.nus.iss.phoenix.maintainschedule.entity.Schedule;
import sg.edu.nus.iss.phoenix.maintainschedule.dao.*;
//import sg.edu.nus.iss.phoenix.maintainschedule.dao.impl.*;

public class ModifyScheduleServiceImpl implements ScheduleService{
	DAOFactoryImpl factory;
	ScheduleDao scheduleDao;
	Logger log = Logger.getLogger(getClass().getName());
	
	public ModifyScheduleServiceImpl()
	{
		factory = new DAOFactoryImpl();
		scheduleDao = factory.getScheduleDAO();
	}
	public void maintainSchedule(Schedule schedule)
	{
		try 
		{
			Boolean overlap = checkOverlap(schedule);
			if (overlap.booleanValue() == true)
				throw new OverlapException("Overlap with existing schedule!");
			
			scheduleDao.save(schedule);
		} catch (NotFoundException e){
			log.severe("Schedule not existing while modify schedule :"+ e.getMessage());
			throw new SystemException(e);
		} catch (SQLException e) {
			log.severe("Error occured while modify schedule :"+ e.getMessage());
			throw new SystemException(e);
		}catch(OverlapException exp){
			throw new SystemException(exp);
		}
	}
	
	
	private Boolean checkOverlap(Schedule schedule) throws SQLException
	{
		try
		{
			//get all schedule within same day
			List<Schedule> scheduleList = scheduleDao.loadWeekly(schedule.getProgramDate());
			//check any overlap
			for (int i=0; i<scheduleList.size(); i++)
			{
				if (scheduleList.get(i).getProgramDate().compareTo(schedule.getProgramDate())!=0)
					continue;
				else{
					long elaps1 = schedule.getEndTime().getTime() - schedule.getStartTime().getTime();
					long elaps2 = scheduleList.get(i).getEndTime().getTime() - scheduleList.get(i).getStartTime().getTime();
					
					long elaps3 = schedule.getEndTime().getTime() - scheduleList.get(i).getStartTime().getTime();
					
					if (Math.abs(elaps3)<(Math.abs(elaps1) + Math.abs(elaps2))){
						return new Boolean(true);
					}else
						continue;
				}
			}
			return new Boolean (false);
		}catch (SQLException se){
			throw se;
		}catch (Exception e){
			return new Boolean (false);
		}
	}
}
