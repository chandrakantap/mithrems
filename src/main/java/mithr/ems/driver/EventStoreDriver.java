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
import mithr.ems.model.Event;

public class EventStoreDriver extends DriverImpl {

	private static final int STORE_EVENT_KEY = 1;
	private static final int LIST_EVENT_KEY = 2;
	private static final int DELETE_EVENT = 3;
	private static final int RETURN_KEY = 4;
	private static final int QUIT_KEY = 5;

	private static EventStoreDriver INSTANCE;

	private EventStore eventStore;
	private final BufferedReader inputScanner = new BufferedReader(new InputStreamReader(System.in));

	private EventStoreDriver() {
	}

	public static EventStoreDriver createInstance(final EventStore eventStore) {
		EventStoreDriver.INSTANCE = new EventStoreDriver();
		EventStoreDriver.INSTANCE.eventStore = eventStore;
		return EventStoreDriver.INSTANCE;
	}

	@Override
	public void takeOver(final Driver previousDriver) {
		try {
			switch (getActionKey()) {
			case STORE_EVENT_KEY:

				storeEventAction(previousDriver);

				break;
			case LIST_EVENT_KEY:
				listEventAction(previousDriver);
				break;
			case DELETE_EVENT:
				deleteEventAction(previousDriver);
				break;
			case RETURN_KEY:
				if (previousDriver != null) {
					previousDriver.takeOver(this);
				} else {
					System.exit(0);
				}
				break;
			case QUIT_KEY:
				System.exit(0);
				break;
			default:
				System.out.println("Please select a proper action :");
				takeOver(previousDriver);
			}
		} catch (IOException e) {
			System.out.println("Some error occured please try again. :(");
			takeOver(previousDriver);
		}

	}

	private int getActionKey() {
		
		System.out.println("=================================");
		System.out.println("Please input :");
		System.out.println(STORE_EVENT_KEY+" to Create Event:");
		System.out.println(LIST_EVENT_KEY+" to List Events:");
		System.out.println(DELETE_EVENT+" to Delete Event:");
		System.out.println(RETURN_KEY+" to return to previous menu:");
		System.out.println(QUIT_KEY+" to quit:");
		System.out.println("=================================");

		int actionKey = -1;
		try {
			actionKey = Integer.parseInt(inputScanner.readLine());
		} catch (NumberFormatException | IOException e) {
			System.out.println("Some error occured please try again. :(");
		}
		return actionKey;
	}

	private void storeEventAction(final Driver previousDriver) throws IOException {

		Date eventDate = null;
		System.out.println("Please enter event date in MM/dd/yy format(e.g. 03/08/15 for 08th march 2015)");
		String eventDateString = inputScanner.readLine();
		DateFormat dateFormatter = new SimpleDateFormat("MM/dd/yy");
		try {
			eventDate = dateFormatter.parse(eventDateString);
			System.out
					.println("Please enter event Name(If you enter a name that already exist it will be overwrited).");
			String eventName = inputScanner.readLine();
			eventStore.storeEvent(new Event(eventName, eventDate));

			System.out.println("The event is created successfully");
		} catch (ParseException e) {
			System.out.println("The date you entered is not proper.Please try again");
		}

		this.takeOver(previousDriver);
	}

	private void listEventAction(final Driver previousDriver) {
		final Collection<Event> eventList = eventStore.getEventList();

		if (eventList.size() > 0) {
			for (Event event : eventList) {
				System.out.println(event);
			}
		} else {
			System.out.println("There is no event");
		}
		printSomeSapce();
		this.takeOver(previousDriver);
	}

	private void deleteEventAction(final Driver previousDriver) throws IOException {
		System.out.println("Please enter event Name, you want to delete).");
		String eventName = inputScanner.readLine();
		System.out.println(" Going to delete event " + eventName + ". Are you sure (Y/N)?");
		String sureOrNot = inputScanner.readLine();
		if ("Y".equalsIgnoreCase(sureOrNot)) {
			eventStore.deleteEvent(eventName);
			System.out.println("Event deleted successfully");
		}
		printSomeSapce();
		this.takeOver(previousDriver);
	}

}
