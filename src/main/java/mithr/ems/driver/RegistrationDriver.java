package mithr.ems.driver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import mithr.ems.handler.Registerer;
import mithr.ems.handler.exception.NoSuchEventException;
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
	public void takeOver(final Driver previousDriver) {
		try {
			switch (getActionKey()) {
			case NEW_REGISTRATION_KEY:
				newRegistrationAction(previousDriver);
				break;
			case MAKE_ATTENDANCE_KEY:
				makeAttendanceAction(previousDriver);
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
		System.out.println(NEW_REGISTRATION_KEY + " for new Registration");
		System.out.println(MAKE_ATTENDANCE_KEY + " to mark attendance");
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

	private void newRegistrationAction(final Driver previousDriver) throws IOException {
		System.out.println("Please enter event Name, you want to register for:).");
		String eventName = inputScanner.readLine();
		
		System.out.println("Please enter Participant Name:");
		String participantName = inputScanner.readLine();
		
		try {
			registerer.makeRegistration(eventName, new Participant(participantName));
		} catch (NoSuchEventException e) {
			System.out.println("There is no such event named "+eventName+". Please try again with different event");
			this.takeOver(previousDriver);
		}
		System.out.println("Registration is successful.");
		printSomeSapce();
		this.takeOver(previousDriver);
		
	}

	private void makeAttendanceAction(final Driver previousDriver) throws IOException {
		System.out.println("Please enter event Name, you want to make attendance:).");
		String eventName = inputScanner.readLine();
		
		System.out.println("Please enter Participant Name:).");
		String participantName = inputScanner.readLine();
		
		try {
			registerer.markAttendance(eventName, new Participant(participantName));
		} catch (NoSuchEventException e) {
			System.out.println("There is no such event named "+eventName+". Please try again with different event");
			this.takeOver(previousDriver);
		}
		System.out.println("Attendance is successful.");
		printSomeSapce();
		this.takeOver(previousDriver);
	}
}
