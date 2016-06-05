package mithr.ems;

import mithr.ems.driver.EventStoreDriver;
import mithr.ems.driver.MainDriver;
import mithr.ems.driver.RegistrationDriver;
import mithr.ems.driver.ReportDriver;
import mithr.ems.handler.EventStore;
import mithr.ems.handler.Registerer;

public class Main {

	/**
	 * It handles dependencies and invoke Main Driver
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		final Registerer registerer = Registerer.createInstance(EventStore.getInstance());

		final EventStoreDriver eventStoreDriver = EventStoreDriver.createInstance(EventStore.getInstance());
		final RegistrationDriver registrationDriver = RegistrationDriver.createInstance(registerer);
		final ReportDriver reportDriver = ReportDriver.createInstance(Registerer.getInstance());
		final MainDriver mainDriver = MainDriver.createInstance(eventStoreDriver, registrationDriver, reportDriver);
		mainDriver.takeOver(null);
	}

}
