package realtimeweb.earthquakeservice.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;




/**
 * The longitudinal, latitudinal, and depth where the earthquake occurred.
 */
public class Coordinate {
	
    // For some unclear reason, these are stored as a list instead of a dictionary.
    
    private Double longitude;
    private Double latitude;
    private Double depth;
    
    
    /**
     * @return The longitude (West-North) component.
     */
    public Double getLongitude() {
        return this.longitude;
    }
    
    /**
     * @param The longitude (West-North) component.
     * @return Double
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    
    /**
     * @return The latitude (South-North) component.
     */
    public Double getLatitude() {
        return this.latitude;
    }
    
    /**
     * @param The latitude (South-North) component.
     * @return Double
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    
    /**
     * @return The depth (closer or farther from the surface) component.
     */
    public Double getDepth() {
        return this.depth;
    }
    
    /**
     * @param The depth (closer or farther from the surface) component.
     * @return Double
     */
    public void setDepth(Double depth) {
        this.depth = depth;
    }
    
	
	/**
	 * Creates a string based representation of this Coordinate.
	
	 * @return String
	 */
	public String toString() {
		return "Coordinate[" +longitude+", "+latitude+", "+depth+"]";
	}
	
	/**
	 * Internal constructor to create a Coordinate from a json representation.
	 * @param map The raw json data that will be parsed.
	 * @return 
	 */
    public Coordinate(List<Object> raw) {
        // TODO: Check that the data has the correct schema.
        // NOTE: It's much safer to check the Map for fields than to catch a runtime exception.
        try {
            this.longitude = Double.parseDouble(raw.get(0).toString());
            this.latitude = Double.parseDouble(raw.get(1).toString());
            this.depth = Double.parseDouble(raw.get(2).toString());
        } catch (NullPointerException e) {
    		System.err.println("Could not convert the response to a Coordinate; a field was missing.");
    		e.printStackTrace();
    	} catch (ClassCastException e) {
    		System.err.println("Could not convert the response to a Coordinate; a field had the wrong structure.");
    		e.printStackTrace();
        }
    
	}	
}