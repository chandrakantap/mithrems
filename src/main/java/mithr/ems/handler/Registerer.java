package mithr.ems.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mithr.ems.driver.ReportDriver;
import mithr.ems.handler.exception.NoSuchEventException;
import mithr.ems.model.Participant;

public class Registerer {

	private static Registerer INSTANCE;
	private EventStore eventStore;

	private Map<String, List<Participant>> registrationIndex = new HashMap<String, List<Participant>>();
	private Map<String, List<Participant>> attendanceIndex = new HashMap<String, List<Participant>>();

	private Registerer() {
	}
	
	public static Registerer createInstance(final EventStore eventStore) {
		Registerer.INSTANCE = new Registerer();
		Registerer.INSTANCE.eventStore = eventStore;
		return Registerer.INSTANCE;
	}

	public static Registerer getInstance() {
		if(Registerer.INSTANCE==null){
			throw new IllegalStateException();
		}
		return Registerer.INSTANCE;
	}

	public void makeRegistration(final String eventName, final Participant participant) throws NoSuchEventException {
		if (eventStore.hasEvent(eventName)) {
			List<Participant> participantForEvent = registrationIndex.get(eventName);
			if (participantForEvent == null) {
				registrationIndex.put(eventName, new ArrayList<Participant>());
			}
			registrationIndex.get(eventName).add(participant);
		} else {
			throw new NoSuchEventException("There is no event exist named" + eventName);
		}
	}

	public void markAttendance(final String eventName, final Participant participant) throws NoSuchEventException {
		if (eventStore.hasEvent(eventName)) {
			List<Participant> participantForEvent = attendanceIndex.get(eventName);
			if (participantForEvent == null) {
				attendanceIndex.put(eventName, new ArrayList<Participant>());
			}
			attendanceIndex.get(eventName).add(participant);
		} else {
			throw new NoSuchEventException("There is no event exist named" + eventName);
		}
	}

	public List<Participant> getParticipantList(final String eventName) {
		
		if (registrationIndex.containsKey(eventName)) {
			return Collections.unmodifiableList(registrationIndex.get(eventName));
		}
		return null;
	}

	public List<Participant> getAttendeeList(final String eventName) {
		if (attendanceIndex.containsKey(eventName)) {
			return Collections.unmodifiableList(attendanceIndex.get(eventName));
		}
		return null;
	}

	public void setEventStore(EventStore eventStore) {
		this.eventStore = eventStore;
	}

}
