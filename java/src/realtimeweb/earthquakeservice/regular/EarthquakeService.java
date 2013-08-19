package realtimeweb.earthquakeservice.regular;

import java.io.IOException;
import java.io.InputStream;

import realtimeweb.earthquakeservice.main.AbstractEarthquakeService;
import realtimeweb.earthquakeservice.json.JsonEarthquakeService;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import realtimeweb.earthquakeservice.domain.History;
import realtimeweb.earthquakeservice.domain.Report;
import realtimeweb.earthquakeservice.domain.Threshold;
import realtimeweb.earthquakeservice.exceptions.EarthquakeException;
import realtimeweb.earthquakeservice.exceptions.ParseEarthquakeException;
import realtimeweb.earthquakeservice.json.JsonGetEarthquakesListener;

/**
 * Used to get data as classes.
 */
public class EarthquakeService implements AbstractEarthquakeService {
	private static EarthquakeService instance;
	private JsonEarthquakeService jsonInstance;
	private Gson gson;

	/**
	 * **For internal use only!** Protected Constructor guards against
	 * instantiation.
	 * 
	 * @return
	 */
	protected EarthquakeService() {
		this.jsonInstance = JsonEarthquakeService.getInstance();
		this.gson = new Gson();
	}

	protected EarthquakeService(InputStream dataStream) throws IOException {
		this.jsonInstance = JsonEarthquakeService.getInstance(dataStream);
		this.gson = new Gson();
	}

	/**
	 * Retrieves the singleton instance.
	 * 
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
	 * Retrieves the singleton instance and, if this is the first time being
	 * called, sets the local cache to use the given **source**.
	 * 
	 * @return EarthquakeService
	 * @throws IOException 
	 */
	public static EarthquakeService getInstance(InputStream source) throws IOException {
		if (instance == null) {
			synchronized (EarthquakeService.class) {
				if (instance == null) {
					instance = new EarthquakeService(source);
				}
			}

		}
		return instance;
	}

	/**
	 * Establishes a connection to the online service. Requires an internet
	 * connection.
	 */
	@Override
	public void connect() {
		jsonInstance.connect();
	}

	/**
	 * Establishes that Business Search data should be retrieved locally. This
	 * does not require an internet connection.<br>
	 * <br>
	 * If data is being retrieved locally, you must be sure that your parameters
	 * match locally stored data. Otherwise, you will get nothing in return.
	 */
	@Override
	public void disconnect() {
		jsonInstance.disconnect();
	}

	/**
	 * Retrieves information about earthquakes around the world.
	 * 
	 * @param threshold
	 *            What kind of earthquakes to report. Note that this is a
	 *            minimum - earthquakes at or ABOVE this level will be reported!
	 * @param time
	 *            The historical time range of earthquakes to report.
	 * @return Report
	 */
	public Report getEarthquakes(Threshold threshold, History time)
			throws EarthquakeException {
		String response = jsonInstance.getEarthquakes(threshold, time);
		if (response.equals("")) {
			return Report.makeEmptyReport();
		} else {
			JsonParser parser = new JsonParser();
			JsonObject top = new JsonObject();
			try {
				top = parser.parse(response).getAsJsonObject();
			} catch (JsonSyntaxException e) {
				throw new ParseEarthquakeException(response);
			}
			return new Report(top, gson);
		}
	}

	/**
	 * Retrieves information about earthquakes around the world.
	 * 
	 * @param threshold
	 *            What kind of earthquakes to report. Note that this is a
	 *            minimum - earthquakes at or ABOVE this level will be reported!
	 * @param time
	 *            The historical time range of earthquakes to report.
	 * @param callback
	 *            The listener that will receive the data (or error).
	 */
	public void getEarthquakes(Threshold threshold, History time,
			final GetEarthquakesListener callback) {

		jsonInstance.getEarthquakes(threshold, time,
				new JsonGetEarthquakesListener() {
					@Override
					public void getEarthquakesFailed(Exception exception) {
						callback.getEarthquakesFailed(exception);
					}

					@Override
					public void getEarthquakesCompleted(String response) {
						if (response.equals("")) {
							callback.getEarthquakesCompleted(Report
									.makeEmptyReport());
						} else {
							JsonParser parser = new JsonParser();
							JsonObject top = new JsonObject();
							try {
								top = parser.parse(response).getAsJsonObject();
							} catch (JsonSyntaxException e) {
								callback.getEarthquakesFailed(new ParseEarthquakeException(
										response));
							}
							Report result = new Report(top, gson);
							callback.getEarthquakesCompleted(result);
						}
					}
				});

	}

}
