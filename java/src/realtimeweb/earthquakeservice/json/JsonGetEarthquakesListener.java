package realtimeweb.earthquakeservice.json;

/**
 * A listener for the getEarthquakes method. On success, passes the data into the getEarthquakesCompleted method. On failure, passes the exception to the getEarthquakesFailed method.
 */
public interface JsonGetEarthquakesListener {
	/**
	 * 
	 * @param data The method that should be overridden to handle the data if the method was successful.
	 */
	public abstract void getEarthquakesCompleted(String data);
	/**
	 * 
	 * @param error The method that should be overridden to handle an exception that occurred while getting the SearchResponse.
	 */
	public abstract void getEarthquakesFailed(Exception error);
}
