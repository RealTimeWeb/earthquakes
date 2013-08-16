package realtimeweb.earthquakeservice.domain;


import com.google.gson.Gson;
import com.google.gson.JsonObject;


/**
 * Information about a specific earthquake.
 */
public class Earthquake {
	
	
	private Coordinate location;
	private double magnitude;
	private String locationDescription;
	private long time;
	private String url;
	private int feltReports;
	private double maximumReportedIntensity;
	private double maximumEstimatedIntensity;
	private String alertLevel;
	private String eventSource;
	private int significance;
	private String id;
	private double distance;
	private double rootMeanSquare;
	private double gap;
	
	
	/**
	 * The location of the earthquake.
	
	 * @return coordinate
	 */
	public Coordinate getLocation() {
		return this.location;
	}
	
	/**
	 * 
	 * @param location The location of the earthquake.
	 */
	public void setLocation(Coordinate location) {
		this.location = location;
	}
	
	/**
	 * The magnitude of the earthquake on the Richter Scale.
	
	 * @return double
	 */
	public double getMagnitude() {
		return this.magnitude;
	}
	
	/**
	 * 
	 * @param magnitude The magnitude of the earthquake on the Richter Scale.
	 */
	public void setMagnitude(double magnitude) {
		this.magnitude = magnitude;
	}
	
	/**
	 * A human-readable description of the location.
	
	 * @return String
	 */
	public String getLocationDescription() {
		return this.locationDescription;
	}
	
	/**
	 * 
	 * @param locationDescription A human-readable description of the location.
	 */
	public void setLocationDescription(String locationDescription) {
		this.locationDescription = locationDescription;
	}
	
	/**
	 * The epoch time (http://en.wikipedia.org/wiki/Unix_time) when this earthquake occurred.
	
	 * @return long
	 */
	public long getTime() {
		return this.time;
	}
	
	/**
	 * 
	 * @param time The epoch time (http://en.wikipedia.org/wiki/Unix_time) when this earthquake occurred.
	 */
	public void setTime(long time) {
		this.time = time;
	}
	
	/**
	 * A webpage with more information about the earthquake.
	
	 * @return String
	 */
	public String getUrl() {
		return this.url;
	}
	
	/**
	 * 
	 * @param url A webpage with more information about the earthquake.
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * The total number of "Felt" reports submitted, or -1 if the data is not available.
	
	 * @return int
	 */
	public int getFeltReports() {
		return this.feltReports;
	}
	
	/**
	 * 
	 * @param feltReports The total number of "Felt" reports submitted, or -1 if the data is not available.
	 */
	public void setFeltReports(int feltReports) {
		this.feltReports = feltReports;
	}
	
	/**
	 * The maximum reported intensity for this earthquake, or -1 if the data is not available. While typically reported as a roman numeral, intensity is reported here as a decimal number. More information can be found at http://earthquake.usgs.gov/learn/topics/mag_vs_int.php
	
	 * @return double
	 */
	public double getMaximumReportedIntensity() {
		return this.maximumReportedIntensity;
	}
	
	/**
	 * 
	 * @param maximumReportedIntensity The maximum reported intensity for this earthquake, or -1 if the data is not available. While typically reported as a roman numeral, intensity is reported here as a decimal number. More information can be found at http://earthquake.usgs.gov/learn/topics/mag_vs_int.php
	 */
	public void setMaximumReportedIntensity(double maximumReportedIntensity) {
		this.maximumReportedIntensity = maximumReportedIntensity;
	}
	
	/**
	 * The maximum estimated instrumental intensity for the event, or -1 if the data is not available. While typically reported as a roman numeral, intensity is reported here as the decimal equivalent. More information can be found at http://earthquake.usgs.gov/learn/topics/mag_vs_int.php
	
	 * @return double
	 */
	public double getMaximumEstimatedIntensity() {
		return this.maximumEstimatedIntensity;
	}
	
	/**
	 * 
	 * @param maximumEstimatedIntensity The maximum estimated instrumental intensity for the event, or -1 if the data is not available. While typically reported as a roman numeral, intensity is reported here as the decimal equivalent. More information can be found at http://earthquake.usgs.gov/learn/topics/mag_vs_int.php
	 */
	public void setMaximumEstimatedIntensity(double maximumEstimatedIntensity) {
		this.maximumEstimatedIntensity = maximumEstimatedIntensity;
	}
	
	/**
	 * A color string (one of "green", "yellow", "orange", "red") indicating how dangerous the quake was, or an empty string ("") if the data is not available. More information about this kind of alert is available at http://earthquake.usgs.gov/research/pager/
	
	 * @return String
	 */
	public String getAlertLevel() {
		return this.alertLevel;
	}
	
	/**
	 * 
	 * @param alertLevel A color string (one of "green", "yellow", "orange", "red") indicating how dangerous the quake was, or an empty string ("") if the data is not available. More information about this kind of alert is available at http://earthquake.usgs.gov/research/pager/
	 */
	public void setAlertLevel(String alertLevel) {
		this.alertLevel = alertLevel;
	}
	
	/**
	 * Either "AUTOMATIC", "PUBLISHED", or "REVIEWED". Indicates how the earthquake was identified and whether it was reviewed by a human.
	
	 * @return String
	 */
	public String getEventSource() {
		return this.eventSource;
	}
	
	/**
	 * 
	 * @param eventSource Either "AUTOMATIC", "PUBLISHED", or "REVIEWED". Indicates how the earthquake was identified and whether it was reviewed by a human.
	 */
	public void setEventSource(String eventSource) {
		this.eventSource = eventSource;
	}
	
	/**
	 * A number describing how significant the event is. Larger numbers indicate a more significant event. This value is determined on a number of factors, including: magnitude, maximum estimated intensity, felt reports, and estimated impact.
	
	 * @return int
	 */
	public int getSignificance() {
		return this.significance;
	}
	
	/**
	 * 
	 * @param significance A number describing how significant the event is. Larger numbers indicate a more significant event. This value is determined on a number of factors, including: magnitude, maximum estimated intensity, felt reports, and estimated impact.
	 */
	public void setSignificance(int significance) {
		this.significance = significance;
	}
	
	/**
	 * A uniquely identifying id for this earthquake.
	
	 * @return String
	 */
	public String getId() {
		return this.id;
	}
	
	/**
	 * 
	 * @param id A uniquely identifying id for this earthquake.
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Horizontal distance from the epicenter to the nearest station (in degrees), or -1 if the data is not available. 1 degree is approximately 111.2 kilometers. In general, the smaller this number, the more reliable is the calculated depth of the earthquake.
	
	 * @return double
	 */
	public double getDistance() {
		return this.distance;
	}
	
	/**
	 * 
	 * @param distance Horizontal distance from the epicenter to the nearest station (in degrees), or -1 if the data is not available. 1 degree is approximately 111.2 kilometers. In general, the smaller this number, the more reliable is the calculated depth of the earthquake.
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	/**
	 * The root-mean-square (RMS) travel time residual, in sec, using all weights. This parameter provides a measure of the fit of the observed arrival times to the predicted arrival times for this location. Smaller numbers reflect a better fit of the data. The value is dependent on the accuracy of the velocity model used to compute the earthquake location, the quality weights assigned to the arrival time data, and the procedure used to locate the earthquake.
	
	 * @return double
	 */
	public double getRootMeanSquare() {
		return this.rootMeanSquare;
	}
	
	/**
	 * 
	 * @param rootMeanSquare The root-mean-square (RMS) travel time residual, in sec, using all weights. This parameter provides a measure of the fit of the observed arrival times to the predicted arrival times for this location. Smaller numbers reflect a better fit of the data. The value is dependent on the accuracy of the velocity model used to compute the earthquake location, the quality weights assigned to the arrival time data, and the procedure used to locate the earthquake.
	 */
	public void setRootMeanSquare(double rootMeanSquare) {
		this.rootMeanSquare = rootMeanSquare;
	}
	
	/**
	 * The largest azimuthal gap between azimuthally adjacent stations (in degrees), or -1 if the data is not available. In general, the smaller this number, the more reliable is the calculated horizontal position of the earthquake.
	
	 * @return double
	 */
	public double getGap() {
		return this.gap;
	}
	
	/**
	 * 
	 * @param gap The largest azimuthal gap between azimuthally adjacent stations (in degrees), or -1 if the data is not available. In general, the smaller this number, the more reliable is the calculated horizontal position of the earthquake.
	 */
	public void setGap(double gap) {
		this.gap = gap;
	}
	
	
	
	/**
	 * Information about a specific earthquake.
	
	 * @return String
	 */
	public String toString() {
		return "Earthquake[" + location + ", " + magnitude + ", " + locationDescription + ", " + time + ", " + url + ", " + feltReports + ", " + maximumReportedIntensity + ", " + maximumEstimatedIntensity + ", " + alertLevel + ", " + eventSource + ", " + significance + ", " + id + ", " + distance + ", " + rootMeanSquare + ", " + gap + "]";
	}
	
	/**
	 * Internal constructor to create a Earthquake from a Json representation.
	 * @param json The raw json data that will be parsed.
	 * @param gson The Gson parser. See <a href='https://code.google.com/p/google-gson/'>https://code.google.com/p/google-gson/</a> for more information.
	 * @return 
	 */
	public  Earthquake(JsonObject json, Gson gson) {
		this.location = new Coordinate(json.get("geometry").getAsJsonObject().get("coordinates").getAsJsonArray(), gson);
		JsonObject properties = json.get("properties").getAsJsonObject();
		this.magnitude = properties.get("mag").getAsDouble();
		this.locationDescription = properties.get("place").getAsString();
		this.time = properties.get("time").getAsLong();
		this.url = properties.get("url").getAsString();
		if (properties.get("felt").isJsonNull()) {
			this.feltReports = -1;
		} else {
			this.feltReports = properties.get("felt").getAsInt();
		}
		if (properties.get("cdi").isJsonNull()) {
			this.maximumReportedIntensity = -1;
		} else {
			this.maximumReportedIntensity = properties.get("cdi").getAsDouble();
		}
		if (properties.get("mmi").isJsonNull()) {
			this.maximumEstimatedIntensity = -1;
		} else {
			this.maximumEstimatedIntensity = properties.get("mmi").getAsDouble();
		}
		if (properties.get("alert").isJsonNull()) {
			this.alertLevel= "";
		} else {
			this.alertLevel = properties.get("alert").getAsString();
		}
		this.eventSource = properties.get("status").getAsString();
		this.significance = properties.get("sig").getAsInt();
		this.id = json.get("id").getAsString();
		if (properties.get("dmin").isJsonNull()) {
			this.distance = -1;
		} else {
			this.distance = properties.get("dmin").getAsDouble();
		}
		if (properties.get("rms").isJsonNull()) {
			this.rootMeanSquare = -1;
		} else {
			this.rootMeanSquare = properties.get("rms").getAsDouble();
		}
		if (properties.get("gap").isJsonNull()) {
			this.gap= -1;
		} else {
			this.gap = properties.get("gap").getAsDouble();
		}
	}
	
	/**
	 * Regular constructor to create a Earthquake.
	 * @param location The location of the earthquake.
	 * @param magnitude The magnitude of the earthquake on the Richter Scale.
	 * @param locationDescription A human-readable description of the location.
	 * @param time The epoch time (http://en.wikipedia.org/wiki/Unix_time) when this earthquake occurred.
	 * @param url A webpage with more information about the earthquake.
	 * @param feltReports The total number of "Felt" reports submitted, or null if the data is not available.
	 * @param maximumReportedIntensity The maximum reported intensity for this earthquake, or null if the data is not available. While typically reported as a roman numeral, intensity is reported here as a decimal number. More information can be found at http://earthquake.usgs.gov/learn/topics/mag_vs_int.php
	 * @param maximumEstimatedIntensity The maximum estimated instrumental intensity for the event, or null if the data is not available. While typically reported as a roman numeral, intensity is reported here as the decimal equivalent. More information can be found at http://earthquake.usgs.gov/learn/topics/mag_vs_int.php
	 * @param alertLevel A color string (one of "green", "yellow", "orange", "red") indicating how dangerous the quake was, or null if the data is not available. More information about this kind of alert is available at http://earthquake.usgs.gov/research/pager/
	 * @param eventSource Either "AUTOMATIC", "PUBLISHED", or "REVIEWED". Indicates how the earthquake was identified and whether it was reviewed by a human.
	 * @param significance A number describing how significant the event is. Larger numbers indicate a more significant event. This value is determined on a number of factors, including: magnitude, maximum estimated intensity, felt reports, and estimated impact.
	 * @param id A uniquely identifying id for this earthquake.
	 * @param distance Horizontal distance from the epicenter to the nearest station (in degrees), or null if the data is not available. 1 degree is approximately 111.2 kilometers. In general, the smaller this number, the more reliable is the calculated depth of the earthquake.
	 * @param rootMeanSquare The root-mean-square (RMS) travel time residual, in sec, using all weights. This parameter provides a measure of the fit of the observed arrival times to the predicted arrival times for this location. Smaller numbers reflect a better fit of the data. The value is dependent on the accuracy of the velocity model used to compute the earthquake location, the quality weights assigned to the arrival time data, and the procedure used to locate the earthquake.
	 * @param gap The largest azimuthal gap between azimuthally adjacent stations (in degrees), or null if the data is not available. In general, the smaller this number, the more reliable is the calculated horizontal position of the earthquake.
	 * @return 
	 */
	public  Earthquake(Coordinate location, double magnitude, String locationDescription, long time, String url, int feltReports, double maximumReportedIntensity, double maximumEstimatedIntensity, String alertLevel, String eventSource, int significance, String id, double distance, double rootMeanSquare, double gap) {
		this.location = location;
		this.magnitude = magnitude;
		this.locationDescription = locationDescription;
		this.time = time;
		this.url = url;
		this.feltReports = feltReports;
		this.maximumReportedIntensity = maximumReportedIntensity;
		this.maximumEstimatedIntensity = maximumEstimatedIntensity;
		this.alertLevel = alertLevel;
		this.eventSource = eventSource;
		this.significance = significance;
		this.id = id;
		this.distance = distance;
		this.rootMeanSquare = rootMeanSquare;
		this.gap = gap;
	}
	
	/**
	 * Returns the hashcode for this Earthquake, based on its ID
	 */
	public int hashCode() {
		return this.id.hashCode();
	}
	
	/**
	 * Compares two objects for equality. Two earthquakes are equal if they have the same ID.
	 */
	public boolean equals(Object that) {
		if ( !(that instanceof Earthquake) ) return false; 
		
		return this.id.equals(((Earthquake)that).getId());
	}
	
}
