package mithr.ems.model;

import java.util.Date;

/**
 * Event model object
 * @author cpal1
 *
 */
public class Event {
	
	private String eventName;
	private Date eventDate;
	
	public Event(final String eventName,final Date eventDate){
		this.eventName= eventName;
		this.eventDate=eventDate;
	}
	
	public String getName(){
		return this.eventName;
	}
	
	public Date getEventDate(){
		return this.eventDate;
	}
	
	@Override
	public String toString(){
		return this.eventName+ " on "+this.eventDate.toString();
	}
}
