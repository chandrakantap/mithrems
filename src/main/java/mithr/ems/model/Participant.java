package mithr.ems.model;

public class Participant {
	private String name;

	public Participant(final String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Participant)) {
			return false;
		} else {
			final Participant participant = (Participant) object;
			if (participant.name.equals(this.name)) {
				return true;
			} else {
				return false;
			}
		}
	}

	@Override
	public int hashCode() {
		int hash = 8;
		hash = hash + (this.name != null ? this.name.hashCode() : 0);
		return hash;
	}

}
