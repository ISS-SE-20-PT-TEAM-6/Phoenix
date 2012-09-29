package sg.edu.nus.iss.phoenix.core.delegate;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import sg.edu.nus.iss.phoenix.core.service.ScheduleSerivce;
import sg.edu.nus.iss.phoenix.maintainschedule.entity.Schedule;
import sg.edu.nus.iss.phoenix.maintainschedule.entity.ScheduleSearchObject;

public class ScheduleDelegate {
	
	private ScheduleSerivce scheduleService;
	
	public ScheduleDelegate(){
		scheduleService = new ScheduleSerivce();
	}

	public List<Schedule> searchProgramSlots(ScheduleSearchObject searchObj) throws ParseException, SQLException{
		return scheduleService.searchPrograms(searchObj);
	}

	public Schedule searchProgram(String scheduleID) {
		return scheduleService.searchProgram(scheduleID);
	}
}
