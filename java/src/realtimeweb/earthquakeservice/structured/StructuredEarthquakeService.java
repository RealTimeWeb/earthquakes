package realtimeweb.earthquakeservice.structured;

import realtimeweb.earthquakeservice.main.AbstractEarthquakeService;
import realtimeweb.earthquakeservice.util.Util;
import java.util.HashMap;
import java.util.LinkedHashMap;
import com.google.gson.Gson;
import realtimeweb.earthquakeservice.json.JsonEarthquakeService;
import realtimeweb.earthquakeservice.json.JsonGetEarthquakesListener;

/**
 * Used to get data as built-in Java objects (HashMap, ArrayList, etc.).
 */
public class StructuredEarthquakeService implements AbstractEarthquakeService {
	private static StructuredEarthquakeService instance;
	private JsonEarthquakeService jsonInstance;
	private Gson gson;
	/**
	 * **For internal use only!** Protected Constructor guards against instantiation.
	
	 * @return 
	 */
	protected  StructuredEarthquakeService() {
		this.jsonInstance = JsonEarthquakeService.getInstance();
		this.gson = new Gson();
	}
	
	/**
	 * Retrieves the singleton instance.
	
	 * @return StructuredEarthquakeService
	 */
	public static StructuredEarthquakeService getInstance() {
		if (instance == null) {
			synchronized (StructuredEarthquakeService.class) {
				if (instance == null) {
					instance = new StructuredEarthquakeService();
				}
			}
			
		}
		return instance;
	}
	
	/**
	 * Establishes a connection to the online service. Requires an internet connection.
	
	 */
	@Override
	public void connect() {
		jsonInstance.connect();
	}
	
	/**
	 * Establishes that Business Search data should be retrieved locally. This does not require an internet connection.<br><br>If data is being retrieved locally, you must be sure that your parameters match locally stored data. Otherwise, you will get nothing in return.
	
	 */
	@Override
	public void disconnect() {
		jsonInstance.disconnect();
	}
	
	/**
	 * Retrieves information about earthquakes around the world.
	 * @param threshold A string indicating what kind of earthquakes to report. Must be either "significant" (only significant earthquakes), "all" (all earthquakes, regardless of significance), "4.5", "2.5", or "1.0". Note that for the last three, all earthquakes at and above that level will be reported.
	 * @param time A string indicating the time range of earthquakes to report. Must be either "hour" (only earthquakes in the past hour), "day" (only earthquakes that happened today), "week" (only earthquakes that happened in the past 7 days), or "month" (only earthquakes that happened in the past 30 days).
	 * @return HashMap<String, Object>
	 */
	public HashMap<String, Object> getEarthquakes(String threshold, String time) throws Exception {
		return gson.fromJson(jsonInstance.getEarthquakes(threshold, time), LinkedHashMap.class);
	}
	
	/**
	 * Retrieves information about earthquakes around the world.
	 * @param threshold A string indicating what kind of earthquakes to report. Must be either "significant" (only significant earthquakes), "all" (all earthquakes, regardless of significance), "4.5", "2.5", or "1.0". Note that for the last three, all earthquakes at and above that level will be reported.
	 * @param time A string indicating the time range of earthquakes to report. Must be either "hour" (only earthquakes in the past hour), "day" (only earthquakes that happened today), "week" (only earthquakes that happened in the past 7 days), or "month" (only earthquakes that happened in the past 30 days).
	 * @param callback The listener that will be given the data (or error)
	 */
	public void getEarthquakes(String threshold, String time, final StructuredGetEarthquakesListener callback) {
		
		jsonInstance.getEarthquakes(threshold, time, new JsonGetEarthquakesListener() {
		    @Override
		    public void getEarthquakesFailed(Exception exception) {
		        callback.getEarthquakesFailed(exception);
		    }
		    
		    @Override
		    public void getEarthquakesCompleted(String data) {
		        callback.getEarthquakesCompleted(gson.fromJson(data, LinkedHashMap.class));
		    }
		});
		
	}
	
}
