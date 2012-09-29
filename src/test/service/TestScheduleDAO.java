package test.service;

import java.util.ArrayList;
import java.util.Calendar;
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
	private Schedule schedule, schedule1;
	
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
		schedule.setScheduleID("201209212300");
		schedule.setProgramDate(new Date(2012-1900,9-1,21));
		schedule.setStartTime(Time.valueOf("23:00:00"));
		schedule.setEndTime(Time.valueOf("23:30:00"));
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
	
	@Test
	public void testLoadAnnual(){
		try
		{
			scheduleDao = new ScheduleDaoImpl();
			List<Schedule> lSchedule = scheduleDao.loadAnnual(2012);
			List<Schedule> lScheduleAll = scheduleDao.loadAll();
			int rows = 0;
			Calendar calender = Calendar.getInstance();
			for(Schedule schedule: lScheduleAll)
			{
				calender.setTime(schedule.getProgramDate());
				if(calender.get(Calendar.YEAR)==2012)
					rows++;
			}
			Assert.assertEquals(rows,lSchedule.size());
		
		}catch(Exception ex){
			Assert.fail();
		}
	}
		
	@Test
	public void testLoadDaily(){
		try
		{
			scheduleDao = new ScheduleDaoImpl();
			schedule1 = new Schedule();
			schedule1.setScheduleID("201909210800");
			Date scheduleDate = new Date(2019-1900,9-1,21);
			schedule1.setProgramDate(new Date(2019-1900,9-1,21));
			schedule1.setStartTime(Time.valueOf("08:00:00"));
			schedule1.setEndTime(Time.valueOf("08:30:00"));
			schedule1.setProgramName("charity");
			schedule1.setPresenter("dilbert");
			schedule1.setProducer("dogbert");
			try{
				scheduleDao.delete(schedule1);
			}
			catch(Exception ex){}
			scheduleDao.create(schedule1);
			List<Schedule> lSchedule = scheduleDao.loadDaily(scheduleDate);
			List<Schedule> lScheduleAll = scheduleDao.loadAll();
			int rows = 0;
			
			for(Schedule schedule: lScheduleAll)
			{
				if(scheduleDate.compareTo(schedule.getProgramDate())==0)
					rows++;
			}
			Assert.assertEquals(rows,lSchedule.size());
		
		}catch(Exception ex){
			Assert.fail();
		}
	}
	
	@Test
	public void testLoadWeekly(){
		try
		{
			scheduleDao = new ScheduleDaoImpl();
			schedule1 = new Schedule();
			schedule1.setScheduleID("201210010000");
			Date scheduleDate = new Date(2012-1900,10-1,01);
			schedule1.setProgramDate(new Date(2012-1900,10-1,01));
			schedule1.setStartTime(Time.valueOf("00:00:00"));
			schedule1.setEndTime(Time.valueOf("01:00:00"));
			schedule1.setProgramName("charity");
			schedule1.setPresenter("dilbert");
			schedule1.setProducer("dogbert");
			try{
				scheduleDao.delete(schedule1);
			}			catch(Exception ex){}
			scheduleDao.create(schedule1);

			Schedule schedule2 = new Schedule();
			schedule2.setScheduleID("201210072300");
			Date endDate = new Date(2012-1900,10-1,7);
			schedule2.setProgramDate(new Date(2012-1900,10-1,7));
			schedule2.setStartTime(Time.valueOf("23:00:00"));
			schedule2.setEndTime(Time.valueOf("23:59:59"));
			schedule2.setProgramName("charity");
			schedule2.setPresenter("dilbert");
			schedule2.setProducer("dogbert");
			try{
				scheduleDao.delete(schedule2);
			}
			catch(Exception ex){}
			scheduleDao.create(schedule2);

			List<Schedule> lSchedule = scheduleDao.loadWeekly(scheduleDate);
			List<Schedule> lScheduleAll = scheduleDao.loadAll();
			int rows = 0;
		
			for(Schedule schedule: lScheduleAll)
			{
				if(scheduleDate.compareTo(schedule.getProgramDate())<=0 && endDate.compareTo(schedule.getProgramDate())>=0)
					rows++;
			}
			Assert.assertEquals(rows,lSchedule.size());
		
		}catch(Exception ex){
			Assert.fail();
		}
	}	
	
	@Test
	public void testSearch(){
		try
		{
			scheduleDao = new ScheduleDaoImpl();
			schedule1 = new Schedule();
			schedule1.setScheduleID("201210010000");
			Date scheduleDate = new Date(2012-1900,10-1,01);
			schedule1.setProgramDate(new Date(2012-1900,10-1,01));
			schedule1.setStartTime(Time.valueOf("00:00:00"));
			schedule1.setEndTime(Time.valueOf("01:00:00"));
			schedule1.setProgramName("charity");
			schedule1.setPresenter("dilbert");
			schedule1.setProducer("dogbert");
			try{
				scheduleDao.delete(schedule1);
			}			catch(Exception ex){}
			scheduleDao.create(schedule1);

			Schedule schedule2 = new Schedule();
			schedule2.setScheduleID("201210072300");
			Date endDate = new Date(2012-1900,10-1,7);
			schedule2.setProgramDate(new Date(2012-1900,10-1,7));
			schedule2.setStartTime(Time.valueOf("23:00:00"));
			schedule2.setEndTime(Time.valueOf("23:59:59"));
			schedule2.setProgramName("charity");
			schedule2.setPresenter("dilbert");
			schedule2.setProducer("dogbert");
			try{
				scheduleDao.delete(schedule2);
			}
			catch(Exception ex){}
			scheduleDao.create(schedule2);
			Schedule schedule3 = new Schedule();
			schedule3.setProgramName("charit");
			schedule3.setPresenter("dilber");
			schedule3.setProducer("dogber");
			schedule3.setScheduleID("201210");

			List<Schedule> lSchedule = scheduleDao.searchMatching(schedule3);
			List<Schedule> lScheduleAll = scheduleDao.loadAll();
			int rows = 0;
		
			for(Schedule schedule: lScheduleAll)
			{
				if(schedule.getScheduleID().contains("201210") &&
					schedule.getProgramName().contains("charit") &&
					schedule.getPresenter().contains("dilber") &&
					schedule.getProducer().contains("dogber")
				)
					rows++;
			}
			Assert.assertEquals(rows,lSchedule.size());
		
		}catch(Exception ex){
			Assert.fail();
		}
	}	
}
