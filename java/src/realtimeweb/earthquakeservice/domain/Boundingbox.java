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
 * The longitudinal, latitudinal, and depth of the region required to display all the earthquakes.
 */
public class BoundingBox {
	//For some unclear reason, these are stored as a list instead of a dictionary.
	
	
	private double minimumLongitude;
	private double minimumLatitude;
	private double minimumDepth;
	private double maximumLongitude;
	private double maximumLatitude;
	private double maximumDepth;
	
	
	/**
	 * The lower longitude (West) component.
	
	 * @return double
	 */
	public double getMinimumLongitude() {
		return this.minimumLongitude;
	}
	
	/**
	 * 
	 * @param minimumLongitude The lower longitude (West) component.
	 */
	public void setMinimumLongitude(double minimumLongitude) {
		this.minimumLongitude = minimumLongitude;
	}
	
	/**
	 * The lower latitude (South) component.
	
	 * @return double
	 */
	public double getMinimumLatitude() {
		return this.minimumLatitude;
	}
	
	/**
	 * 
	 * @param minimumLatitude The lower latitude (South) component.
	 */
	public void setMinimumLatitude(double minimumLatitude) {
		this.minimumLatitude = minimumLatitude;
	}
	
	/**
	 * The lower depth (closer or farther from the surface) component.
	
	 * @return double
	 */
	public double getMinimumDepth() {
		return this.minimumDepth;
	}
	
	/**
	 * 
	 * @param minimumDepth The lower depth (closer or farther from the surface) component.
	 */
	public void setMinimumDepth(double minimumDepth) {
		this.minimumDepth = minimumDepth;
	}
	
	/**
	 * The higher longitude (East) component.
	
	 * @return double
	 */
	public double getMaximumLongitude() {
		return this.maximumLongitude;
	}
	
	/**
	 * 
	 * @param maximumLongitude The higher longitude (East) component.
	 */
	public void setMaximumLongitude(double maximumLongitude) {
		this.maximumLongitude = maximumLongitude;
	}
	
	/**
	 * The higher latitude (North) component.
	
	 * @return double
	 */
	public double getMaximumLatitude() {
		return this.maximumLatitude;
	}
	
	/**
	 * 
	 * @param maximumLatitude The higher latitude (North) component.
	 */
	public void setMaximumLatitude(double maximumLatitude) {
		this.maximumLatitude = maximumLatitude;
	}
	
	/**
	 * The higher depth (closer or farther from the surface) component.
	
	 * @return double
	 */
	public double getMaximumDepth() {
		return this.maximumDepth;
	}
	
	/**
	 * 
	 * @param maximumDepth The higher depth (closer or farther from the surface) component.
	 */
	public void setMaximumDepth(double maximumDepth) {
		this.maximumDepth = maximumDepth;
	}
	
	
	
	/**
	 * The longitudinal, latitudinal, and depth of the region required to display all the earthquakes.
	
	 * @return String
	 */
	public String toString() {
		return "BoundingBox[" + minimumLongitude + ", " + minimumLatitude + ", " + minimumDepth + ", " + maximumLongitude + ", " + maximumLatitude + ", " + maximumDepth + "]";
	}
	
	/**
	 * Internal constructor to create a BoundingBox from a Json representation.
	 * @param json The raw json data that will be parsed.
	 * @param gson The Gson parser. See <a href='https://code.google.com/p/google-gson/'>https://code.google.com/p/google-gson/</a> for more information.
	 * @return 
	 */
	public  BoundingBox(JsonObject json, Gson gson) {
		this.minimumLongitude = json.get("0").getAsDouble();
		this.minimumLatitude = json.get("1").getAsDouble();
		this.minimumDepth = json.get("2").getAsDouble();
		this.maximumLongitude = json.get("0").getAsDouble();
		this.maximumLatitude = json.get("1").getAsDouble();
		this.maximumDepth = json.get("2").getAsDouble();
	}
	
	/**
	 * Regular constructor to create a BoundingBox.
	 * @param minimumLongitude The lower longitude (West) component.
	 * @param minimumLatitude The lower latitude (South) component.
	 * @param minimumDepth The lower depth (closer or farther from the surface) component.
	 * @param maximumLongitude The higher longitude (East) component.
	 * @param maximumLatitude The higher latitude (North) component.
	 * @param maximumDepth The higher depth (closer or farther from the surface) component.
	 * @return 
	 */
	public  BoundingBox(double minimumLongitude, double minimumLatitude, double minimumDepth, double maximumLongitude, double maximumLatitude, double maximumDepth) {
		this.minimumLongitude = minimumLongitude;
		this.minimumLatitude = minimumLatitude;
		this.minimumDepth = minimumDepth;
		this.maximumLongitude = maximumLongitude;
		this.maximumLatitude = maximumLatitude;
		this.maximumDepth = maximumDepth;
	}
	
}
