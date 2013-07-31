package realtimeweb.earthquakeservice.json;

import realtimeweb.earthquakeservice.main.AbstractEarthquakeService;
import java.util.HashMap;
import realtimeweb.earthquakeservice.util.Util;

/**
 * Used to get data as a raw string.
 */
public class JsonEarthquakeService implements AbstractEarthquakeService {
	private static JsonEarthquakeService instance;
	protected boolean local;
	private ClientStore clientStore;
	/**
	 * **For internal use only!** Protected Constructor guards against instantiation.
	
	 * @return 
	 */
	protected  JsonEarthquakeService() {
		disconnect();
		this.clientStore = new ClientStore();
	}
	
	/**
	 * Retrieves the singleton instance.
	
	 * @return JsonEarthquakeService
	 */
	public static JsonEarthquakeService getInstance() {
		if (instance == null) {
			synchronized (JsonEarthquakeService.class) {
				if (instance == null) {
					instance = new JsonEarthquakeService();
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
		this.local = false;
	}
	
	/**
	 * Establishes that Business Search data should be retrieved locally. This does not require an internet connection.<br><br>If data is being retrieved locally, you must be sure that your parameters match locally stored data. Otherwise, you will get nothing in return.
	
	 */
	@Override
	public void disconnect() {
		this.local = true;
	}
	
	/**
	 * **For internal use only!** The ClientStore is the internal cache where offline data is stored.
	
	 * @return ClientStore
	 */
	public ClientStore getClientStore() {
		return this.clientStore;
	}
	
	/**
	 * Retrieves information about earthquakes around the world.
	 * @param threshold A string indicating what kind of earthquakes to report. Must be either "significant" (only significant earthquakes), "all" (all earthquakes, regardless of significance), "4.5", "2.5", or "1.0". Note that for the last three, all earthquakes at and above that level will be reported.
	 * @param time A string indicating the time range of earthquakes to report. Must be either "hour" (only earthquakes in the past hour), "day" (only earthquakes that happened today), "week" (only earthquakes that happened in the past 7 days), or "month" (only earthquakes that happened in the past 30 days).
	 * @return String
	 */
	public String getEarthquakes(String threshold, String time) throws Exception {
		String url = String.format("http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/%s_%s.geojson", String.valueOf(threshold), String.valueOf(time));
		HashMap<String, String> parameters = new HashMap<String, String>();
		if (this.local) {
			return clientStore.getData(Util.hashRequest(url, parameters));
		}
		String jsonResponse = "";
		try {
		    jsonResponse = Util.get(url, parameters);
		    if (jsonResponse.startsWith("<")) {
		        throw new Exception(jsonResponse);
		    }
		    return jsonResponse;
		} catch (Exception e) {
		    throw new Exception(e.toString());
		}
	}
	
	/**
	 * Retrieves information about earthquakes around the world.
	 * @param threshold A string indicating what kind of earthquakes to report. Must be either "significant" (only significant earthquakes), "all" (all earthquakes, regardless of significance), "4.5", "2.5", or "1.0". Note that for the last three, all earthquakes at and above that level will be reported.
	 * @param time A string indicating the time range of earthquakes to report. Must be either "hour" (only earthquakes in the past hour), "day" (only earthquakes that happened today), "week" (only earthquakes that happened in the past 7 days), or "month" (only earthquakes that happened in the past 30 days).
	 * @param callback The listener that will be given the data (or error).
	 */
	public void getEarthquakes(final String threshold, final String time, final JsonGetEarthquakesListener callback) {
		
		Thread thread = new Thread() {
		    @Override
		    public void run() {
		        try {
		            callback.getEarthquakesCompleted(JsonEarthquakeService.getInstance().getEarthquakes(threshold, time));
		        } catch (Exception e) {
		            callback.getEarthquakesFailed(e);
		        }
		    }
		};
		thread.start();
		
	}
	
}
