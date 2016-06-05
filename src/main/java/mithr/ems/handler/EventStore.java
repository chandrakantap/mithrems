package mithr.ems.handler;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import mithr.ems.model.Event;

public class EventStore {

	private static EventStore INSTANCE;

	private Map<String, Event> eventDataStore = new HashMap<String, Event>();

	private EventStore() {
	}

	public static EventStore getInstance() {
		if (EventStore.INSTANCE == null) {
			EventStore.INSTANCE = new EventStore();
		}
		return EventStore.INSTANCE;
	}

	public void storeEvent(final Event event) {
		this.eventDataStore.put(event.getName(), event);
	}

	public void deleteEvent(final String eventName) {
		this.eventDataStore.remove(eventName);
	}

	public Event getEvent(final String eventName) {
		return eventDataStore.get(eventName);
	}

	public Collection<Event> getEventList() {
		return Collections.unmodifiableCollection(eventDataStore.values());
	}

	public boolean hasEvent(final String eventName) {
		return this.eventDataStore.containsKey(eventName);
	}

	public int getEventCount() {
		return eventDataStore.size();
	}
}
