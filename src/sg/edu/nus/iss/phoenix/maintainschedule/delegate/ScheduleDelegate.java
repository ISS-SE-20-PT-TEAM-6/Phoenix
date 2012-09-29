package sg.edu.nus.iss.phoenix.maintainschedule.delegate;

//import java.util.List;


//import sg.edu.nus.iss.phoenix.maintainschedule.exceptions.UserAlreadyExistsException;
//import sg.edu.nus.iss.phoenix.maintainschedule.exceptions.UserNotFoundException;
import sg.edu.nus.iss.phoenix.maintainschedule.entity.Schedule;
import sg.edu.nus.iss.phoenix.maintainschedule.service.ScheduleService;
import sg.edu.nus.iss.phoenix.maintainschedule.service.impl.*;

public class ScheduleDelegate {
	private ScheduleService service;
	
	
	public ScheduleDelegate(String action) {
		if(action.equals("create") || action.equals("createByCopy")){
			service=new CreateScheduleServiceImpl();
		}else if(action.equals("modify")){
			service=new ModifyScheduleServiceImpl();
		}else if(action.equals("delete")){
			service=new DeleteScheduleServiceImpl();
		}
	}
	
	
	
	public void maintainSchedule(Schedule schedule) throws Exception{
		service.maintainSchedule(schedule); 
	}
	

}
