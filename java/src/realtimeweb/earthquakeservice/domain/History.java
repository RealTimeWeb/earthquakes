package realtimeweb.earthquakeservice.domain;

public enum History {
	HOUR, DAY, WEEK, MONTH;

	public String toString() {
		switch (this) {
		case HOUR:
			return "hour";
		case DAY:
			return "day";
		case WEEK:
			return "week";
		case MONTH:
			return "month";
		default:
			return "hour";
		}
	}
}
