package realtimeweb.earthquakeservice.structured;

import realtimeweb.earthquakeservice.main.AbstractEarthquakeService;
import realtimeweb.earthquakeservice.main.History;
import realtimeweb.earthquakeservice.main.Threshold;
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
	 * @param threshold What kind of earthquakes to report. Note that this is a minimum - earthquakes at or ABOVE this level will be reported!
	 * @param time The historical time range of earthquakes to report.
	 * @return HashMap<String, Object>
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> getEarthquakes(Threshold threshold, History time) throws Exception {
		return gson.fromJson(jsonInstance.getEarthquakes(threshold, time), LinkedHashMap.class);
	}
	
	/**
	 * Retrieves information about earthquakes around the world.
	 * @param threshold What kind of earthquakes to report. Note that this is a minimum - earthquakes at or ABOVE this level will be reported!
	 * @param time The historical time range of earthquakes to report.
	 * @param callback The listener that will be given the data (or error)
	 */
	public void getEarthquakes(Threshold threshold, History time, final StructuredGetEarthquakesListener callback) {
		
		jsonInstance.getEarthquakes(threshold, time, new JsonGetEarthquakesListener() {
		    @Override
		    public void getEarthquakesFailed(Exception exception) {
		        callback.getEarthquakesFailed(exception);
		    }
		    
		    @SuppressWarnings("unchecked")
			@Override
		    public void getEarthquakesCompleted(String data) {
		        callback.getEarthquakesCompleted(gson.fromJson(data, LinkedHashMap.class));
		    }
		});
		
	}
	
}
