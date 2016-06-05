package mithr.ems.model

import spock.lang.Specification

class RegistrationSpec extends Specification {
	
	private final Event sampleEvent = Mock(Event)
	private final Participant sampleParticipant = Mock(Participant)	
	private final String eventName = "Water Dancing 101"
	
	def " Registration object creation"(){
		
		when :" we try to created a Registration Object with an event and a participant"
		final Registration forellSignUp = new Registration(sampleParticipant,sampleEvent);
		
		then:" it should be created properly"
		sampleEvent.equals(forellSignUp.getEvent())
		sampleParticipant.equals(forellSignUp.getParticipant())
		
		and: " Registration title should be same as event Name"
		sampleEvent.getName().equals(forellSignUp.getTitle())
	}
	
	def " Registration title should be same as event name"(){
		
		given:"Event name is WATER DANCING 101"
		final String eventName = "WATER DANCING 101"
		sampleEvent.getName() >> eventName
		
		
		when :" we  createda Registration Object with an event and a participant"
		final Registration forellSignUp = new Registration(sampleParticipant,sampleEvent);
		
		then: " Registration title should be same as event Name"
		sampleEvent.getName().equals(forellSignUp.getTitle())
	}
	
	
	def "Registration toString() must be descriptive"(){
		
		given: "Sample participant name is Syrio Forell and event name is WATER DANCING 101"
		final String participantName = "Syrio Forell"
		sampleParticipant.getName() >>  participantName
		
		final String eventName = "WATER DANCING 101" 
		sampleEvent.getName() >> eventName
		
		when: " we create a Registration and call toString() on this"
		final Registration forellSignUp = new Registration(sampleParticipant,sampleEvent);
		final String registration = forellSignUp.toString();
		
		then: " it should return Syrio Forell registered for WATER DANCING 101"
		final String expected = "Syrio Forell registered for WATER DANCING 101"
		expected.equals(registration)
		
	}
	

}
