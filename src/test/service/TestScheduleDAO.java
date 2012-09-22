package test.service;

import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.sql.Time;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;
import sg.edu.nus.iss.phoenix.maintainschedule.dao.ScheduleDao;
import sg.edu.nus.iss.phoenix.maintainschedule.dao.impl.ScheduleDaoImpl;
import sg.edu.nus.iss.phoenix.maintainschedule.entity.Schedule;;


public class TestScheduleDAO
{
	private ScheduleDao scheduleDao;
	private Schedule schedule;
	
	@Before
	public void setUp() throws Exception {
		
	}
	
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testConvertScheduleID() {
		schedule = new Schedule();
		schedule.setScheduleID("201209212240");
		schedule.setProgramDate(new Date(2012-1900,9-1,21));
		schedule.setStartTime(Time.valueOf("22:40:00"));
		scheduleDao = new ScheduleDaoImpl();
		String strID=((ScheduleDaoImpl) scheduleDao).testConvertScheduleID(schedule);
		Assert.assertEquals(schedule.getScheduleID(), strID);
	}
	@Test
	public void testCreate(){
		try
		{
		schedule = new Schedule();
		schedule.setScheduleID("201209212200");
		schedule.setProgramDate(new Date(2012-1900,9-1,21));
		schedule.setStartTime(Time.valueOf("22:00:00"));
		schedule.setEndTime(Time.valueOf("22:30:00"));
		schedule.setProgramName("charity");
		schedule.setPresenter("dilbert");
		schedule.setProducer("dogbert");
		
		scheduleDao = new ScheduleDaoImpl();
		try
		{
			scheduleDao.delete(schedule);
		}
		catch(Exception ex) {};
		scheduleDao.create(schedule);
		Schedule lSchedule = new Schedule();
		lSchedule.setScheduleID(new String(schedule.getScheduleID()));
		scheduleDao.load(lSchedule);
		Assert.assertTrue(schedule.hasEqualMapping(lSchedule));
		}
		catch(Exception ex){
			Assert.fail();
		}
	}
	
	@Test
	public void testLoadAll(){
		try
		{
			scheduleDao = new ScheduleDaoImpl();
			List<Schedule> lSchedule = scheduleDao.loadAll();
			int rows = scheduleDao.countAll();
			Assert.assertEquals(rows,lSchedule.size());
		
		}catch(Exception ex){
			Assert.fail();
		}
	}
}
