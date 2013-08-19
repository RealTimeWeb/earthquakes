package realtimeweb.earthquakeservice.exceptions;

/**
 * Thrown when data from a local file or the earthquake service cannot be parsed.
 * @author acbart
 *
 */
public class ParseEarthquakeException extends EarthquakeException {

	public ParseEarthquakeException(String string) {
		super(string);
		// TODO Auto-generated constructor stub
	}

}
