package mithr.ems.model;

public class Registration {

	private final Event event;
	private final Participant participant;
	private final String title;

	public Registration(final Participant participant, final Event event) {
		this.participant = participant;
		this.event = event;
		this.title = event.getName();
	}

	public Event getEvent() {
		return event;
	}

	public Participant getParticipant() {
		return participant;
	}

	public String getTitle() {
		return title;
	}

	@Override
	public String toString() {
		return participant.getName() + " registered for " + event.getName();
	}
}
