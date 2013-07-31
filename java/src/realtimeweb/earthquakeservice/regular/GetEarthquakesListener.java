package realtimeweb.earthquakeservice.regular;

import java.util.ArrayList;
import java.util.HashMap;
import realtimeweb.earthquakeservice.domain.Report;
/**
 * A listener for the getEarthquakes method. On success, passes the data into the getEarthquakesCompleted method. On failure, passes the exception to the getEarthquakesFailed method.
 */
public interface GetEarthquakesListener {
	/**
	 * 
	 * @param data The method that should be overridden to handle the data if the method was successful.
	 */
	public abstract void getEarthquakesCompleted(Report data);
	/**
	 * 
	 * @param error The method that should be overridden to handle an exception that occurred while getting the SearchResponse.
	 */
	public abstract void getEarthquakesFailed(Exception error);
}
