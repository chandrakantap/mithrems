package mithr.ems.handler

import java.awt.image.SampleModel;

import mithr.ems.handler.exception.NoSuchEventException
import mithr.ems.handler.exception.NoSuchRegistrationException
import mithr.ems.model.Participant
import spock.lang.Specification

class RegistererSpec extends Specification{

	private EventStore eventStore = Mock(EventStore);
	private Registerer registerer = Registerer.createInstance(eventStore);

	def "We do not need more than one Registerer object, so it must be singletone"(){
		
		when:"We create two or more Register object using getInstance() method"		
		final Registerer regOne = Registerer.getInstance();
		final Registerer regTwo = Registerer.getInstance();
		final Registerer regThree = Registerer.getInstance();

		then:" all the returned objects must be same"
		regOne == regTwo
		regTwo == regThree
	}

	def " must be able to do registration"(){
		given:" An even and a particiapant"
		final Participant sampleParticipant = Mock(Participant)
		final String eventName = "ROYAL MARRIAGE"

		eventStore.hasEvent(eventName) >> true

		when:" we will try to make registration of the participant for the event"
		registerer.makeRegistration(eventName,sampleParticipant)

		then:" it should register the participant properly"
		registerer.getParticipantList(eventName).size() == 1
	}

	def " must throw exception when trying to registration with invalid event"(){
		given:" An invalid event and a particiapant"
		final Participant sampleParticipant = Mock(Participant)

		final String eventName = "ROYAL MARRIAGE"

		// an invalid event
		eventStore.hasEvent(eventName) >> false

		when:" we will try to make registration of the participant for the event"
		registerer.makeRegistration(eventName,sampleParticipant)

		then:" it should throw NoSuchEventException"
		thrown NoSuchEventException

	}

	def " must be able to make attendance"(){
		given:" An event and a registered particiapant "
		final Participant sampleParticipant = Mock(Participant)

		final String eventName = "ROYAL MARRIAGE"

		eventStore.hasEvent(eventName) >> true
		registerer.makeRegistration(eventName,sampleParticipant);

		when:" we will try to make attendance of the participant for the event"
		registerer.markAttendance(eventName,sampleParticipant)

		then:" it should mark the attendance properly"
		registerer.getAttendeeList(eventName).size() == 1
	}

	def " must throw exception when trying make attendance with invalid event"(){
		given:" An invalid event and a particiapant"
		final Participant sampleParticipant = Mock(Participant)
		final String eventName = "ROYAL MARRIAGE"

		// an invalid event
		eventStore.hasEvent(eventName) >> false

		when:" we will try to make registration of the participant for the event"
		registerer.markAttendance(eventName,sampleParticipant)

		then:" it should throw NoSuchEventException"
		thrown NoSuchEventException

	}
	
	def " must throw exception when trying make attendance with Participant not registered"(){
		given:" an valid event and a particiapant"
		final Participant sampleParticipant = Mock(Participant)
		final String eventName = "ROYAL MARRIAGE"

		// a valid event
		eventStore.hasEvent(eventName) >> true

		when:" we will try to make registration of the participant for the event"
		registerer.markAttendance(eventName,sampleParticipant)

		then:" it should throw NoSuchRegistrationException"
		thrown NoSuchRegistrationException

	}
	
}
