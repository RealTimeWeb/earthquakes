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

/**
 * The longitudinal, latitudinal, and depth where the earthquake occurred.
 */
public class Coordinate {
	//For some unclear reason, these are stored as a list instead of a dictionary.
	
	
	private double longitude;
	private double latitude;
	private double depth;
	
	
	/**
	 * The longitude (West-North) component.
	
	 * @return double
	 */
	public double getLongitude() {
		return this.longitude;
	}
	
	/**
	 * 
	 * @param longitude The longitude (West-North) component.
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	/**
	 * The latitude (South-North) component.
	
	 * @return double
	 */
	public double getLatitude() {
		return this.latitude;
	}
	
	/**
	 * 
	 * @param latitude The latitude (South-North) component.
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	/**
	 * The depth (closer or farther from the surface) component.
	
	 * @return double
	 */
	public double getDepth() {
		return this.depth;
	}
	
	/**
	 * 
	 * @param depth The depth (closer or farther from the surface) component.
	 */
	public void setDepth(double depth) {
		this.depth = depth;
	}
	
	
	
	/**
	 * The longitudinal, latitudinal, and depth where the earthquake occurred.
	
	 * @return String
	 */
	public String toString() {
		return "Coordinate[" + longitude + ", " + latitude + ", " + depth + "]";
	}
	
	/**
	 * Internal constructor to create a Coordinate from a Json representation.
	 * @param json The raw json data that will be parsed.
	 * @param gson The Gson parser. See <a href='https://code.google.com/p/google-gson/'>https://code.google.com/p/google-gson/</a> for more information.
	 * @return 
	 */
	public  Coordinate(JsonObject json, Gson gson) {
		this.longitude = json.get("0").getAsDouble();
		this.latitude = json.get("1").getAsDouble();
		this.depth = json.get("2").getAsDouble();
	}
	
	/**
	 * Regular constructor to create a Coordinate.
	 * @param longitude The longitude (West-North) component.
	 * @param latitude The latitude (South-North) component.
	 * @param depth The depth (closer or farther from the surface) component.
	 * @return 
	 */
	public  Coordinate(double longitude, double latitude, double depth) {
		this.longitude = longitude;
		this.latitude = latitude;
		this.depth = depth;
	}
	
}
