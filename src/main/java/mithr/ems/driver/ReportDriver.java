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
	public void takeOver() {
		boolean startOver = true;
		try {
			while (startOver) {
				switch (getActionKey()) {
				case GET_PARTICIPANT_KEY:
					getParticipantAction();
					break;
				case GET_ATTENDANCE_KEY:
					getAttendanceAction();
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
		System.out.println(GET_PARTICIPANT_KEY + " to get participant list");
		System.out.println(GET_ATTENDANCE_KEY + " to get attendance");
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

	private void getParticipantAction() throws IOException {
		System.out.print("Please enter event Name, you want to check participation. ");
		final String eventName = inputScanner.readLine();

		final List<Participant> participantList = registerer.getParticipantList(eventName.toUpperCase());

		if (participantList != null && participantList.size() > 0) {
			for (Participant participant : participantList) {
				System.out.println(participant);
			}
		} else {
			System.out.println("There is no participant for the event " + eventName);
		}
		printSomeSapce();
	}

	private void getAttendanceAction() throws IOException {
		System.out.print("Please enter event Name, you want to check Attendance. ");
		final String eventName = inputScanner.readLine();

		final List<Participant> attendeanceList = registerer.getAttendeeList(eventName.toUpperCase());

		if (attendeanceList != null && attendeanceList.size() > 0) {
			for (Participant participant : attendeanceList) {
				System.out.println(participant);
			}
		} else {
			System.out.println("No one attended the event " + eventName);
		}
		printSomeSapce();
	}
}
