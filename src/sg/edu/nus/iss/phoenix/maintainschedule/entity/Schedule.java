package sg.edu.nus.iss.phoenix.maintainschedule.entity;
import java.io.Serializable;
import java.sql.Time;
import java.util.Date;


import sg.edu.nus.iss.phoenix.user.entity.Role;
import sg.edu.nus.iss.phoenix.user.entity.User;


/**
 * User Value Object. This class is value object representing database table
 * user This class is intented to be used together with associated Dao object.
 */

public class Schedule implements Cloneable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Persistent Instance variables. This data is directly mapped to the
	 * columns of database table.
	 */
	private String scheduleID;
	private String programName;
	private Date programDate;
	private String presenter;
	private String producer;
	private Time startTime;
	private Time endTime;
	
	public Schedule() {
	}
	
	public Schedule( String scheduleID, String programName, Date programDate, String presenter, String producer, Time startTime, Time endTime ){
		this.scheduleID =scheduleID;
		this.programName = programName;
		this.programDate = programDate;
		this.presenter = presenter;
		this.producer = producer;
		this.startTime =startTime;
		this.endTime = endTime;		
	}
	
	public String getScheduleID() {
		return this.scheduleID;
	}
	
	public void setScheduleID(String scheduleIDIn){
		this.scheduleID= scheduleIDIn;
	}
	
	public String getProgramName() {
		return this.programName;
	}
	
	public void setProgramName(String programNameIn){
		this.programName = programNameIn;
	}
	public Date getProgramDate() {
		return this.programDate;
	}
	public void setProgramDate(Date programDateIn) {
		this.programDate=programDateIn;
	}
	public String getPresenter(){
		return this.presenter;
	}
	public void setPresenter(String presenterIn) {
		this.presenter=presenterIn;
	}
	public String getProducer(){
		return this.producer;		
	}
	public void setProducer(String producerIn){
		this.producer=producerIn;
	}
	
	public Time getStartTime(){
		return this.startTime;
	}
	public void setStartTime(Time startTimeIn){
		this.startTime=startTimeIn;
	}
	public Time getEndTime(){
		return this.endTime;
	}
	public void setEndTime(Time endTimeIn){
		this.endTime = endTimeIn;
	}
	
	/**
	 * hasEqualMapping-method will compare two User instances and return true if
	 * they contain same values in all persistent instance variables. If
	 * hasEqualMapping returns true, it does not mean the objects are the same
	 * instance. However it does mean that in that moment, they are mapped to
	 * the same row in database.
	 */
	public boolean hasEqualMapping(Schedule valueObject) {

		if (!this.scheduleID.equals(valueObject.getScheduleID() )) {
			return (false);
		}
		if (this.programName == null) {
			if (valueObject.getProgramName() != null)
				return (false);
		} else if (!this.programName.equals(valueObject.getProgramName())) {
			return (false);
		}
		if (this.presenter== null) {
			if (valueObject.getPresenter() != null)
				return (false);
		} else if (!this.presenter.equals(valueObject.getPresenter())) {
			return (false);
		}
		if (this.producer== null) {
			if (valueObject.getProducer() != null)
				return (false);
		} else if (!this.producer.equals(valueObject.getProducer())) {
			return (false);
		}
		if (this.programDate== null) {
			if (valueObject.getProgramDate() != null)
				return (false);
		} else if (this.programDate.compareTo(valueObject.getProgramDate())!=0) {
			return (false);
		}

		if (this.startTime== null) {
			if (valueObject.getStartTime() != null)
				return (false);
		} else if (this.startTime.compareTo(valueObject.getStartTime())!=0) {
			return (false);
		}
		if (this.endTime== null) {
			if (valueObject.getEndTime() != null)
				return (false);
		} else if (this.endTime.compareTo(valueObject.getEndTime())!=0) {
			return (false);
		}

		
		return true;
	}

	/**
	 * toString will return String object representing the state of this
	 * valueObject. This is useful during application development, and possibly
	 * when application is writing object states in console logs.
	 */
	public String toString() {
		StringBuffer out = new StringBuffer("toString: ");
		out.append("\nclass Schedule, mapping to table schedule\n");
		out.append("Persistent attributes: \n");
		out.append("scheduleid = " + this.getScheduleID() + "\n");
		out.append("programName = " + this.programName + "\n");
		out.append("presenter = " + this.presenter + "\n");
		out.append("producer = " + this.producer + "\n");
		out.append("programDate = " + this.programDate.toString() + "\n");
		out.append("startTime = " + this.startTime.toString() + "\n");
		out.append("endTime = " + this.endTime + "\n");
		return out.toString();
	}

	/**
	 * Clone will return identical deep copy of this valueObject. Note, that
	 * this method is different than the clone() which is defined in
	 * java.lang.Object. Here, the returned cloned object will also have all its
	 * attributes cloned.
	 */
	public Object clone() {
		Schedule cloned = new Schedule();
		cloned.setScheduleID(new String(this.scheduleID));
		if (this.programName != null)
			cloned.setProgramName(new String(this.programName));
		if (this.presenter != null)
			cloned.setPresenter(new String(this.presenter));
		if (this.producer != null)
			cloned.setProducer(new String(this.producer));
		if(this.programDate!=null)
			cloned.setProgramDate((Date) this.programDate.clone());
		if(this.startTime!=null)
			cloned.setStartTime( (Time)this.startTime.clone());
		if(this.endTime!=null)
			cloned.setStartTime( (Time)this.endTime.clone());

		return cloned;
	}

	public String toJson() {
		
		StringBuffer out = new StringBuffer("{");
		out.append("\"scheduleid\" : \"" + this.getScheduleID() + "\",");
		out.append("\"programName\" : \"" + this.programName + "\",");
		out.append("\"presenter\" : \"" + this.presenter + "\",");
		out.append("\"producer\" : \"" + this.producer + "\",");
		out.append("\"programDate\" : \"" + this.programDate.toString() + "\",");
		out.append("\"startTime\" : \"" + this.startTime.toString() + "\",");
		out.append("\"endTime\" : \"" + this.endTime + "\"}");
		return out.toString();
	}
}
