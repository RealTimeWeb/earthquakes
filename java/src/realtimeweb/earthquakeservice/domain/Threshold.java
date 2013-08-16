package realtimeweb.earthquakeservice.domain;

/**
 * Note that the threshold indicates the MINIMUM level that earthquakes are
 * reported; so using Threshold.MINOR will yield all earthquakes that are 2.5+!
 * 
 * @author acbart
 * 
 */
public enum Threshold {
	SIGNIFICANT, ALL, MICRO, MINOR, LIGHT;

	public String toString() {
		switch (this) {
		case SIGNIFICANT:
			return "significant";
		case ALL:
			return "all";
		case MICRO:
			return "1.0";
		case MINOR:
			return "2.5";
		case LIGHT:
			return "4.5";
		default:
			return "all";
		}
	}
}
