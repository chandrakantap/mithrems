package mithr.ems.driver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import mithr.ems.handler.EventStore;
import mithr.ems.handler.exception.NoSuchEventException;
import mithr.ems.model.Event;

public class EventStoreDriver extends DriverImpl {

	private static final int STORE_EVENT_KEY = 1;
	private static final int LIST_EVENT_KEY = 2;
	private static final int DELETE_EVENT = 3;
	private static final int RETURN_KEY = 4;
	private static final int QUIT_KEY = 5;

	private static EventStoreDriver INSTANCE;

	private EventStore eventStore;
	private final  BufferedReader inputScanner = new BufferedReader(new InputStreamReader(System.in));

	private EventStoreDriver() {
	}

	public static EventStoreDriver createInstance(final EventStore eventStore) {
		EventStoreDriver.INSTANCE = new EventStoreDriver();
		EventStoreDriver.INSTANCE.eventStore = eventStore;
		return EventStoreDriver.INSTANCE;
	}

	@Override
	public void takeOver() {
		boolean startOver = true;
		try {
			while (startOver) {
				switch (getActionKey()) {
				case STORE_EVENT_KEY:
					storeEventAction();
					break;
				case LIST_EVENT_KEY:
					listEventAction();
					break;
				case DELETE_EVENT:
					deleteEventAction();
					break;
				case RETURN_KEY:
					startOver = false;
					break;
				case QUIT_KEY:
					startOver = false;
					inputScanner.close();
					System.exit(0);
					break;
				default:
					System.out.println("[ERROR] Please select a proper action :");
				}
			}
		} catch (IOException e) {
			System.out.println("[ERROR] Some error occured please try again. :(");
		}

	}

	private int getActionKey() {

		System.out.println("");
		System.out.println("Please input :");
		System.out.println(STORE_EVENT_KEY + " to Create Event:");
		System.out.println(LIST_EVENT_KEY + " to List Events:");
		System.out.println(DELETE_EVENT + " to Delete Event:");
		System.out.println(RETURN_KEY + " to return to previous menu:");
		System.out.println(QUIT_KEY + " to quit:");
		System.out.println("");
		System.out.print("ems:> ");

		int actionKey = -1;
		try {
			actionKey = Integer.parseInt(inputScanner.readLine());
		} catch (NumberFormatException | IOException e) {
			System.out.println("[ERROR] Some error occured please try again. :(");
		}
		return actionKey;
	}

	private void storeEventAction() throws IOException {

		Date eventDate = null;
		System.out.print("Please enter event date in dd/MM/yy format(e.g. 08/03/15 for 08th march 2015). ");
		String eventDateString = inputScanner.readLine();
		DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yy");
		try {
			eventDate = dateFormatter.parse(eventDateString);
			System.out.print("Please enter event Name(Event name are not case sensitive.If you enter a name that already exist it will be overwrited). ");
			String eventName = inputScanner.readLine();
			final Event newEvent = new Event(eventName.toUpperCase(), eventDate);
			eventStore.storeEvent(newEvent);
			System.out.println("[SUCCESS] " + newEvent + " Created successfully.");
		} catch (ParseException e) {
			System.out.println("[ERROR] " + "The date you entered is not proper.Please try again");
		}
	}

	private void listEventAction() {
		final Collection<Event> eventList = eventStore.getEventList();

		if (eventList.size() > 0) {
			System.out.println("Event List");
			System.out.println("==========");
			for (Event event : eventList) {
				System.out.println(event);
			}
		} else {
			System.out.println("There is no event");
		}
		printSomeSapce();
	}

	private void deleteEventAction() throws IOException {
		System.out.print("Please enter event Name, you want to delete). ");
		String eventName = inputScanner.readLine();
		System.out.print("Going to delete event " + eventName + ". Are you sure (Y/N)? ");
		String sureOrNot = inputScanner.readLine();
		if ("Y".equalsIgnoreCase(sureOrNot)) {
			try {
				eventStore.deleteEvent(eventName.toUpperCase());
				System.out.println("[SUCCESS] Event deleted successfully");
			} catch (NoSuchEventException e) {
				System.out.println("[ERROR] No such event exist named " + eventName);
			}

		}
		printSomeSapce();
	}

}
