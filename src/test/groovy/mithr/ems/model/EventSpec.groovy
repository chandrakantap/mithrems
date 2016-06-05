package mithr.ems.model;

import java.text.DateFormat
import java.text.SimpleDateFormat

import spock.lang.Specification

class EventSpec extends Specification {

	def " Event object creation"(){
		
		given: "event name and event date"		
		final String eventName = " ELIXIR2016";
		final DateFormat dateFormatter = new SimpleDateFormat("MM/dd/yy");
		final Date eventDate = dateFormatter.parse("03/18/16");
		
		when :" we try to created an Event Object"		
		final Event elixir2016 = new Event(eventName,eventDate);
		
		then:" it should be created properly"
		eventName.equals(elixir2016.getName())
		eventDate.equals(elixir2016.getEventDate())
	}
	
	
	def "Event toString() must be descriptive"(){
		given: " An event name"
		final String eventName = "HACKATHON2015"	
		
		
		when: " we create an Event and call toString() on this"
		final Event sampleEvent = new Event(eventName,new Date());
		final String sampleEventToString = sampleEvent.toString(); 
		
		then: " it should return descriptive Name"
		sampleEventToString.contains(eventName)
	}
}
