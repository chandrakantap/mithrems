package mithr.ems.driver;

/**
 * Drives user interactiona and application execution flow
 * @author cpal1
 *
 */
public interface Driver {
	public void takeOver(Driver previousDriver);
}
