package mithr.ems.driver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

import mithr.ems.handler.Registerer;
import mithr.ems.model.Event;
import mithr.ems.model.Participant;

public class ReportDriver extends DriverImpl {

	private static final int GET_PARTICIPANT_KEY = 1;
	private static final int GET_ATTENDANCE_KEY = 2;
	private static final int RETURN_KEY = 3;
	private static final int QUIT_KEY = 4;

	private static ReportDriver INSTANCE;

	private Registerer registerer;
	private final BufferedReader inputScanner = new BufferedReader(new InputStreamReader(System.in));

	private ReportDriver() {
	}

	public static ReportDriver createInstance(final Registerer registerer) {
		ReportDriver.INSTANCE = new ReportDriver();
		ReportDriver.INSTANCE.registerer = registerer;
		return ReportDriver.INSTANCE;
	}

	@Override
	public void takeOver(final Driver previousDriver) {
		try {
			switch (getActionKey()) {
			case GET_PARTICIPANT_KEY:
				getParticipantAction(previousDriver);
				break;
			case GET_ATTENDANCE_KEY:
				getAttendanceAction(previousDriver);
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
		System.out.println(GET_PARTICIPANT_KEY + " to get participant list");
		System.out.println(GET_ATTENDANCE_KEY + " to get attendance");
		System.out.println(RETURN_KEY + " to return to previous menu:");
		System.out.println(QUIT_KEY + " to quit:");
		System.out.println("=================================");

		int actionKey = -1;
		try {
			actionKey = Integer.parseInt(inputScanner.readLine());
		} catch (NumberFormatException | IOException e) {
			System.out.println("Some error occured please try again. :(");
		}
		return actionKey;
	}

	private void getParticipantAction(final Driver previousDriver) throws IOException {
		System.out.println("Please enter event Name, you want to check participation:).");
		String eventName = inputScanner.readLine();

		final List<Participant> participantList = registerer.getParticipantList(eventName);

		if (participantList!=null && participantList.size() > 0) {
			for (Participant participant : participantList) {
				System.out.println(participant);
			}
		} else {
			System.out.println("There is no participant for the event " + eventName);
		}
		printSomeSapce();
		this.takeOver(previousDriver);
	}

	private void getAttendanceAction(final Driver previousDriver) throws IOException {
		System.out.println("Please enter event Name, you want to check Attendance:).");
		String eventName = inputScanner.readLine();

		final List<Participant> attendeanceList = registerer.getAttendeeList(eventName);

		if (attendeanceList!=null && attendeanceList.size() > 0) {
			for (Participant participant : attendeanceList) {
				System.out.println(participant);
			}
		} else {
			System.out.println("No one attended the event " + eventName);
		}
		printSomeSapce();
		this.takeOver(previousDriver);
	}
}
