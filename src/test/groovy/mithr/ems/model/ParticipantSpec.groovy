package mithr.ems.model

import spock.lang.Specification

class ParticipantSpec extends Specification {
	
	def " Participant object creation"(){
		
		given: "participant name"
		final String participantName = "Jaquen H'ger";
		
		when :" we try to created an Participant Object"
		final Participant jaquen = new Participant(participantName);
		
		then:" it should be created properly"
		participantName.equals(jaquen.getName())
	}
	
	
	def "Participant toString() must be descriptive"(){
		given: " A participant name"
		final String participantName = "Mycella Tyrell";
		
		
		when: " we create a Participant and call toString() on this"
		final Participant mycella = new Participant(participantName);
		final String participantToString = mycella.toString();
		
		then: " it should return Participant Name"
		participantName.equals(participantToString)
	}
	
	def "Participation name are same then they are equal"(){
		
		when:" Two Participant have same Name"
		Participant participantOne = new Participant("Dario naharis");
		Participant participantTwo = new Participant("Dario naharis");
		
		then:" They must be equal"
		participantOne.equals(participantTwo)==true
	}
}
