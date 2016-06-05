package mithr.ems.handler

import mithr.ems.model.Event
import spock.lang.Specification

class EventStoreSpec extends Specification {

	private EventStore eventStore = EventStore.getInstance()

	def "We do not need more than one EventStore object, so it must be singletone"(){

		when:"We create two or more EventStore object using getInstance() method"
		EventStore eventStoreOne = EventStore.getInstance();
		EventStore eventStoreTwo = EventStore.getInstance();
		EventStore eventStoreThree = EventStore.getInstance();

		then:" all the returned objects must be same"
		eventStoreOne == eventStoreTwo
		eventStoreTwo == eventStoreThree
	}

	def "Must store an event"(){
		given:" An event"
		Event sampleEvent = Mock(Event)

		when:" we store the event"
		eventStore.storeEvent(sampleEvent);

		then:" event count must increase by one"
		eventStore.getEventCount() == old(eventStore.getEventCount()) +1
		eventStore.getEventList().size() == old(eventStore.getEventCount()) +1
	}

	def "Must delete an event"(){
		given:" Two event and we store them to EventStore"
		Event sampleEventOne = Mock(Event)
		Event sampleEventTwo = Mock(Event)

		sampleEventOne.getName() >> "sampleEventOne"
		sampleEventTwo.getName() >> "sampleEventTwo"

		eventStore.storeEvent(sampleEventOne);
		eventStore.storeEvent(sampleEventTwo);

		when:" we delete one store"
		eventStore.deleteEvent("sampleEventOne")


		then:" event count must decrease by one"
		eventStore.getEventCount() == old(eventStore.getEventCount()) - 1
	}
	
	def "Must return event object by name"(){
		given:"A event with name TRIAL BY COMBAT and we store that"
		Event sampleEventOne = Mock(Event)
		final String eventName = "TRIAL BY COMBAT"
		sampleEventOne.getName() >> eventName

		eventStore.storeEvent(sampleEventOne);

		when:" we try to get that event from store"
		Event returnedEvent = eventStore.getEvent(eventName)


		then:" it must return the same event"
		sampleEventOne==returnedEvent
	}
	
	def "Must tell if and event object exist by name"(){
		given:"A event with name TRIAL BY COMBAT and we store that"
		Event sampleEventOne = Mock(Event)
		final String eventName = "TRIAL BY COMBAT"
		sampleEventOne.getName() >> eventName

		eventStore.storeEvent(sampleEventOne);

		when:" we try to check if an event exist with the name TRIAL BY COMBAT"
		boolean  isEventExist= eventStore.hasEvent(eventName)


		then:" it must return true"
		isEventExist
	}
	
	def "Must tell if and event object do not exist by name"(){
		
		given:"There was no event stored named BLIND DATING"		

		when:" we try to check if an event exist with the name BLIND DATING"
		boolean  isEventExist= eventStore.hasEvent("BLIND DATING")


		then:" it must return false"
		isEventExist==false
	}
}
