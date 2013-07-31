package realtimeweb.earthquakeservice.regular;

import realtimeweb.earthquakeservice.main.AbstractEarthquakeService;
import realtimeweb.earthquakeservice.json.JsonEarthquakeService;
import realtimeweb.earthquakeservice.util.Util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import realtimeweb.earthquakeservice.domain.Report;
import realtimeweb.earthquakeservice.json.JsonGetEarthquakesListener;

/**
 * Used to get data as classes.
 */
public class EarthquakeService implements AbstractEarthquakeService {
	private static EarthquakeService instance;
	private JsonEarthquakeService jsonInstance;
	private Gson gson;
	/**
	 * **For internal use only!** Protected Constructor guards against instantiation.
	
	 * @return 
	 */
	protected  EarthquakeService() {
		this.jsonInstance = JsonEarthquakeService.getInstance();
		this.gson = new Gson();
	}
	
	/**
	 * Retrieves the singleton instance.
	
	 * @return EarthquakeService
	 */
	public static EarthquakeService getInstance() {
		if (instance == null) {
			synchronized (EarthquakeService.class) {
				if (instance == null) {
					instance = new EarthquakeService();
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
	 * @return Report
	 */
	public Report getEarthquakes(String threshold, String time) throws Exception {
		String response = jsonInstance.getEarthquakes(threshold,time);
		JsonParser parser = new JsonParser();
		JsonObject top = parser.parse(response).getAsJsonObject();
		return new Report(top, gson);
	}
	
	/**
	 * Retrieves information about earthquakes around the world.
	 * @param threshold A string indicating what kind of earthquakes to report. Must be either "significant" (only significant earthquakes), "all" (all earthquakes, regardless of significance), "4.5", "2.5", or "1.0". Note that for the last three, all earthquakes at and above that level will be reported.
	 * @param time A string indicating the time range of earthquakes to report. Must be either "hour" (only earthquakes in the past hour), "day" (only earthquakes that happened today), "week" (only earthquakes that happened in the past 7 days), or "month" (only earthquakes that happened in the past 30 days).
	 * @param callback The listener that will receive the data (or error).
	 */
	public void getEarthquakes(String threshold, String time, final GetEarthquakesListener callback) {
		
		jsonInstance.getEarthquakes(threshold, time, new JsonGetEarthquakesListener() {
		    @Override
		    public void getEarthquakesFailed(Exception exception) {
		        callback.getEarthquakesFailed(exception);
		    }
		    
		    @Override
		    public void getEarthquakesCompleted(String response) {
		        JsonParser parser = new JsonParser();
		JsonObject top = parser.parse(response).getAsJsonObject();
		        Report result = new Report(top, gson);
		        callback.getEarthquakesCompleted(result);
		    }
		});
		
	}
	
}
