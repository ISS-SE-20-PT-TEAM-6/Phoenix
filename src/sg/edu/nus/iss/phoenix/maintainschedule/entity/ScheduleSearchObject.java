package sg.edu.nus.iss.phoenix.maintainschedule.entity;

import java.sql.Date;

public class ScheduleSearchObject {

	private String programName;
	private String startDate;
	private String endDate;
	
	public ScheduleSearchObject(){
		super();
	}
	
	public ScheduleSearchObject(String programName, String startDate, String endDate){
		super();
		this.programName = programName;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	
	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
