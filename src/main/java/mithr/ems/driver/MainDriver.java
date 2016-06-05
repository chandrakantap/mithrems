package mithr.ems.driver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainDriver implements Driver {

	private static final int MANAGE_EVENT_KEY = 1;
	private static final int MANAGE_REGISTRATION_KEY = 2;
	private static final int REPORT_KEY = 3;
	private static final int QUIT_KEY = 4;
	
	private static MainDriver INSTANCE;
	
	private Driver eventStoreDriver;
	private Driver registrationDriver;
	private Driver reportDriver;
	
	private final BufferedReader inputScanner = new BufferedReader(new InputStreamReader(System.in));
	
	private MainDriver(){}
	
	public static MainDriver createInstance(final Driver eventStoreDriver,final Driver registrationDriver,final Driver reportDriver) {
		MainDriver.INSTANCE = new MainDriver();
		MainDriver.INSTANCE.eventStoreDriver=eventStoreDriver;
		MainDriver.INSTANCE.registrationDriver=registrationDriver;
		MainDriver.INSTANCE.reportDriver=reportDriver;		
		return MainDriver.INSTANCE;		
	}

	@Override
	public void takeOver(final Driver driver) {
		switch (getActionKey()) {
		case MANAGE_EVENT_KEY:
			eventStoreDriver.takeOver(this);
			break;
		case MANAGE_REGISTRATION_KEY:
			registrationDriver.takeOver(this);
			break;
		case REPORT_KEY:
			reportDriver.takeOver(this);
			break;
		case QUIT_KEY:
			System.exit(0);
			break;
		default:
			System.out.println("Please select a proper action :");
			takeOver(driver);
		}

	}

	private int getActionKey() {		
		System.out.println("=================================");
		System.out.println("Please input :");
		System.out.println(MANAGE_EVENT_KEY+" to Manage Events:");
		System.out.println(MANAGE_REGISTRATION_KEY+" to Manage Registration/Attendance:");
		System.out.println(REPORT_KEY+" for Reports:");
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

}
