package realtimeweb.earthquakeservice.domain;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;

/**
 * Information about earthquakes matching certain criteria, including the area that they occurred.
 */
public class Report {
	
	
	private BoundingBox area;
	private ArrayList<Earthquake> earthquakes;
	private String title;
	
	
	/**
	 * A region that contains all the earthquakes.
	
	 * @return bounding box
	 */
	public BoundingBox getArea() {
		return this.area;
	}
	
	/**
	 * 
	 * @param area A region that contains all the earthquakes.
	 */
	public void setArea(BoundingBox area) {
		this.area = area;
	}
	
	/**
	 * A list of the earthquakes.
	
	 * @return ArrayList<Earthquake>
	 */
	public ArrayList<Earthquake> getEarthquakes() {
		return this.earthquakes;
	}
	
	/**
	 * 
	 * @param earthquakes A list of the earthquakes.
	 */
	public void setEarthquakes(ArrayList<Earthquake> earthquakes) {
		this.earthquakes = earthquakes;
	}
	
	/**
	 * A human-readable title that describes this data.
	
	 * @return String
	 */
	public String getTitle() {
		return this.title;
	}
	
	/**
	 * 
	 * @param title A human-readable title that describes this data.
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	
	/**
	 * Information about earthquakes matching certain criteria, including the area that they occurred.
	
	 * @return String
	 */
	public String toString() {
		return "Report[" + area + ", " + earthquakes + ", " + title + "]";
	}
	
	/**
	 * Internal constructor to create a Report from a Json representation.
	 * @param json The raw json data that will be parsed.
	 * @param gson The Gson parser. See <a href='https://code.google.com/p/google-gson/'>https://code.google.com/p/google-gson/</a> for more information.
	 * @return 
	 */
	public  Report(JsonObject json, Gson gson) {
		this.earthquakes = new ArrayList<Earthquake>();
		if (json.has("bbox")) {
			for (JsonElement e : json.get("features").getAsJsonArray()) {
				this.earthquakes.add(new Earthquake(e.getAsJsonObject(), gson));
			}
			this.area = new BoundingBox(json.get("bbox").getAsJsonArray(), gson);
		} else {
			this.area = new BoundingBox(0, 0, 0, 0, 0, 0);
		}
		this.title = json.get("metadata").getAsJsonObject().get("title").getAsString();
	}
	
	/**
	 * Regular constructor to create a Report.
	 * @param area A region that contains all the earthquakes.
	 * @param earthquakes A list of the earthquakes.
	 * @param title A human-readable title that describes this data.
	 * @return 
	 */
	public  Report(BoundingBox area, ArrayList<Earthquake> earthquakes, String title) {
		this.area = area;
		this.earthquakes = earthquakes;
		this.title = title;
	}
	
}
