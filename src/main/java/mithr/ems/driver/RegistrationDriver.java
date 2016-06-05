package mithr.ems.driver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import mithr.ems.handler.Registerer;
import mithr.ems.handler.exception.NoSuchEventException;
import mithr.ems.handler.exception.NoSuchRegistrationException;
import mithr.ems.model.Participant;

public class RegistrationDriver extends DriverImpl {

	private static final int NEW_REGISTRATION_KEY = 1;
	private static final int MAKE_ATTENDANCE_KEY = 2;
	private static final int RETURN_KEY = 3;
	private static final int QUIT_KEY = 4;

	private static RegistrationDriver INSTANCE;

	private Registerer registerer;
	private final BufferedReader inputScanner = new BufferedReader(new InputStreamReader(System.in));

	private RegistrationDriver() {
	}

	public static RegistrationDriver createInstance(final Registerer registerer) {
		RegistrationDriver.INSTANCE = new RegistrationDriver();
		RegistrationDriver.INSTANCE.registerer = registerer;
		return RegistrationDriver.INSTANCE;
	}

	@Override
	public void takeOver() {
		boolean startOver = true;
		try{
			while (startOver) {
				switch (getActionKey()) {
				case NEW_REGISTRATION_KEY:
					newRegistrationAction();
					break;
				case MAKE_ATTENDANCE_KEY:
					makeAttendanceAction();
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
		System.out.println(NEW_REGISTRATION_KEY + " for new Registration");
		System.out.println(MAKE_ATTENDANCE_KEY + " to mark attendance");
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

	private void newRegistrationAction() throws IOException {
		System.out.print("Please enter Participant Name: ");
		final String participantName = inputScanner.readLine();

		System.out.print("Please enter event Name, you want to register for: ");
		final String eventName = inputScanner.readLine();

		try {
			registerer.makeRegistration(eventName.toUpperCase(), new Participant(participantName.toUpperCase()));
			System.out.println("[SUCCESS] Registration is successful.");
			printSomeSapce();
		} catch (NoSuchEventException e) {
			System.out.println(
					"[ERROR] There is no such event named " + eventName + ". Please try again with different event");
		}

	}

	private void makeAttendanceAction() throws IOException {
		System.out.print("Please enter Participant Name: ");
		final String participantName = inputScanner.readLine();

		System.out.print("Please enter event Name, you want to make attendance: ");
		final String eventName = inputScanner.readLine();

		try {
			registerer.markAttendance(eventName.toUpperCase(), new Participant(participantName.toUpperCase()));
			System.out.println("[SUCCESS] Attendance is successful.");
			printSomeSapce();
		} catch (NoSuchEventException e) {
			System.out.println(
					"[ERROR] There is no such event named " + eventName + ". Please try again with different event");
		} catch (NoSuchRegistrationException e) {
			System.out.println("[ERROR] " + participantName + " is not registered for " + eventName);
		}

	}
}
