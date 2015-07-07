package realtimeweb.earthquakeservice;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import realtimeweb.earthquakeservice.domain.*;
import realtimeweb.stickyweb.EditableCache;
import realtimeweb.stickyweb.StickyWeb;
import realtimeweb.stickyweb.StickyWebRequest;
import realtimeweb.stickyweb.StickyWebResponse;
import realtimeweb.stickyweb.exceptions.StickyWebDataSourceNotFoundException;
import realtimeweb.stickyweb.exceptions.StickyWebDataSourceParseException;
import realtimeweb.stickyweb.exceptions.StickyWebInternetException;
import realtimeweb.stickyweb.exceptions.StickyWebInvalidPostArguments;
import realtimeweb.stickyweb.exceptions.StickyWebInvalidQueryString;
import realtimeweb.stickyweb.exceptions.StickyWebJsonResponseParseException;
import realtimeweb.stickyweb.exceptions.StickyWebLoadDataSourceException;
import realtimeweb.stickyweb.exceptions.StickyWebNotInCacheException;

/**
 * Get the latest information about earthquakes around the world.
 */
public class SimpleEarthquake {
	private StickyWeb connection;
	private boolean online;
	private HashSet<String> seen_quakes;

	public static void main(String[] args) {
		SimpleEarthquake simpleEarthquake = new SimpleEarthquake("e-violent-quakes.json");

		for (Earthquake quake : simpleEarthquake.getEarthquakes("all", "week")) {
			System.out.println("--- New Quake ---");
			System.out.println(quake.getLocationDescription());
			System.out.println(quake.getLocation());
			System.out.println(quake.getMagnitude());
		}

		// The following pre-generated code demonstrates how you can
		// use StickyWeb's EditableCache to create data files.
		try {
			// First, you create a new EditableCache, possibly passing in an
			// existing cache to add to it
			EditableCache recording = new EditableCache();
			for (int i = 0; i < 5; i += 1) {
				System.out.println("Adding another request.");
				recording.addData(simpleEarthquake.getEarthquakesRequest("all", "week"));
				Thread.sleep(1000);
			}
			/*
			 * // First you get a request object StickyWebRequest request =
			 * SimpleEarthquake.getEarthquakesRequest(...);
			 * Then you can get the request's hash and value, and add it to the EditableCache
			 * recording.addData(request.getHashedRequest(),
			 * request.execute().asText());
			 */
			// Then you can save the expanded cache over the original
			recording.saveToStream(new FileOutputStream("cache.json"));
		} catch (StickyWebDataSourceNotFoundException e) {
			System.err
					.println("The given FileStream was not able to be found. Reason: "+e.getMessage());
		} catch (StickyWebDataSourceParseException e) {
			System.err
					.println("The given FileStream could not be parsed; possibly the structure is incorrect.");
		} catch (StickyWebLoadDataSourceException e) {
			System.err.println("The given data source could not be loaded.");
		} catch (FileNotFoundException e) {
			System.err
					.println("The given cache.json file was not found, or could not be opened.");
		}
		// ** End of how to use the EditableCache
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StickyWebNotInCacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StickyWebInternetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StickyWebInvalidQueryString e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StickyWebInvalidPostArguments e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Create a new, online connection to the service
	 */
	public SimpleEarthquake() {
		this.online = true;
		this.initializeSeenQuakes();
		try {
			this.connection = new StickyWeb(null);
		} catch (StickyWebDataSourceNotFoundException e) {
			System.err.println("The given datastream could not be loaded.");
		} catch (StickyWebDataSourceParseException e) {
			System.err.println("The given datastream could not be parsed");
		} catch (StickyWebLoadDataSourceException e) {
			System.err.println("The given data source could not be loaded");
		}
	}

	/**
	 * Create a new, offline connection to the service.
	 * 
	 * @param cache
	 *            The filename for this cache.
	 */
	public SimpleEarthquake(String cache) {
		try {
			this.online = false;
			this.initializeSeenQuakes();
			this.connection = new StickyWeb(new FileInputStream(cache));
		} catch (StickyWebDataSourceNotFoundException e) {
			System.err.println("The given data source could not be found.");
			System.exit(1);
		} catch (StickyWebDataSourceParseException e) {
			System.err
					.println("Could not read the data source. Perhaps its format is incorrect?");
			System.exit(1);
		} catch (StickyWebLoadDataSourceException e) {
			System.err.println("The given data source could not be read.");
			System.exit(1);
		} catch (FileNotFoundException e) {
			System.err
					.println("The given cache file could not be found. Make sure it is in the right folder.");
			System.exit(1);
		}
	}

	private void initializeSeenQuakes() {
		this.seen_quakes = new HashSet<String>();
	}

	/**
	 * Whether or not we're filtering based on "significant", or if we want
	 * "all" the earthquakes. There are a couple other valid options too, but
	 * they've been removed for simplicity.
	 * 
	 * @param threshold
	 * @return
	 */
	private static boolean isValidThreshold(String threshold) {
		return threshold.equalsIgnoreCase("all")
				|| threshold.equalsIgnoreCase("significant");
	}

	/**
	 * How far back results can go: the past "hour", "day", "week", or "month".
	 * 
	 * @param time
	 * @return
	 */
	private static boolean isValidTime(String time) {
		return time.equalsIgnoreCase("hour") || time.equalsIgnoreCase("day")
				|| time.equalsIgnoreCase("week")
				|| time.equalsIgnoreCase("month");
	}

	private StickyWebRequest getEarthquakesRequest(String threshold, String time) {
		try {
			final String url = String
					.format("http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/%s_%s.geojson",
							String.valueOf(threshold.toLowerCase()),
							String.valueOf(time.toLowerCase()));
			HashMap<String, String> parameters = new HashMap<String, String>();
			// TODO: Validate the inputs here
			ArrayList<String> indexList = new ArrayList<String>();

			return connection.get(url, parameters).setOnline(online)
					.setIndexes(indexList);
		} catch (StickyWebDataSourceNotFoundException e) {
			System.err.println("Could not find the data source.");
		}
		return null;
	}

	/**
	 * Retrieves information about earthquakes around the world.
	 * 
	 * @param threshold
	 *            A string indicating what kind of earthquakes to report. Must
	 *            be either "significant" (only significant earthquakes), "all"
	 *            (all earthquakes, regardless of significance), "4.5", "2.5",
	 *            or "1.0". Note that for the last three, all earthquakes at and
	 *            above that level will be reported.
	 * @param time
	 *            A string indicating the time range of earthquakes to report.
	 *            Must be either "hour" (only earthquakes in the past hour),
	 *            "day" (only earthquakes that happened today), "week" (only
	 *            earthquakes that happened in the past 7 days), or "month"
	 *            (only earthquakes that happened in the past 30 days).
	 * @return the list of earthquakes
	 */
	public ArrayList<Earthquake> getEarthquakes(String threshold, String time) {
		try {
			StickyWebRequest request = getEarthquakesRequest(threshold, time);

			ArrayList<Earthquake> result = new ArrayList<Earthquake>();
			StickyWebResponse response = request.execute();
			// TODO: Validate the output here using response.isNull,
			// response.asText, etc.
			if (response.isNull())
				return result;
			if (response.asText().equals("404 File Not Found")) {
				System.err.println("The given parameters were not valid.");
				System.exit(1);
			}
			Iterator<Object> resultIter = ((ArrayList<Object>) ((Map<String, Object>) response
					.asJSON()).get("features")).iterator();
			while (resultIter.hasNext()) {
				result.add(new Earthquake((Map<String, Object>) resultIter
						.next()));
			}
			return result;
		} catch (StickyWebNotInCacheException e) {
			System.err
					.println("There is no query in the cache for the given inputs. Perhaps something was mispelled?");
		} catch (StickyWebInternetException e) {
			System.err
					.println("Could not connect to the web service. It might be your internet connection, or a problem with the web service.");
		} catch (StickyWebInvalidQueryString e) {
			System.err
					.println("The given arguments were invalid, and could not be turned into a query.");
		} catch (StickyWebInvalidPostArguments e) {
			System.err
					.println("The given arguments were invalid, and could not be turned into a query.");

		} catch (StickyWebJsonResponseParseException e) {
			System.err
					.println("The response from the server couldn't be understood.");

		}
		return null;
	}

	public ArrayList<Earthquake> getNewEarthquakes(String threshold, String time) {
		ArrayList<Earthquake> new_quakes = getEarthquakes(threshold, time);
		ArrayList<Earthquake> result_quakes = new ArrayList<Earthquake>();
		for (Earthquake q : new_quakes) {
			if (!seen_quakes.contains(q.getId())) {
				result_quakes.add(q);
				seen_quakes.add(q.getId());
			}
		}
		return result_quakes;
	}

}