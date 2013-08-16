package realtimeweb.earthquakeservice.main;

import java.util.HashMap;

import realtimeweb.earthquakeservice.domain.History;
import realtimeweb.earthquakeservice.domain.Report;
import realtimeweb.earthquakeservice.domain.Threshold;
import realtimeweb.earthquakeservice.exceptions.EarthquakeException;
import realtimeweb.earthquakeservice.json.JsonEarthquakeService;
import realtimeweb.earthquakeservice.regular.EarthquakeService;
import realtimeweb.earthquakeservice.structured.StructuredEarthquakeService;
import realtimeweb.earthquakeservice.structured.StructuredGetEarthquakesListener;

public class EarthquakeServiceTest {

	public static void main(String[] args) {
		
		/**
		 * Simplest possible program using earthquake data
		 */
		EarthquakeService res = EarthquakeService.getInstance();
		try {
			Report earthquakeData = res.getEarthquakes(Threshold.ALL, History.HOUR);
			System.out.println(earthquakeData);
		} catch (EarthquakeException e) {
			e.printStackTrace();
		}
		
		System.out.println("\n");
		JsonEarthquakeService jes = JsonEarthquakeService.getInstance();
		jes.connect();
		try {
			System.out.println(jes.getEarthquakes(Threshold.ALL, History.HOUR));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		StructuredEarthquakeService ses = StructuredEarthquakeService.getInstance();
		ses.connect();
		try {
			System.out.println(ses.getEarthquakes(Threshold.ALL, History.HOUR));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("\n");
		
		for (int i = 0; i < 1000; i += 1) {
			// Try to be evil
			try {
				res.getEarthquakes(Threshold.ALL, History.HOUR);
			} catch (Exception e) {
				e.printStackTrace();
				i = 1001;
			}
		}
		System.out.println("Finished being evil");
		
		ses.getEarthquakes(Threshold.SIGNIFICANT, History.DAY, new StructuredGetEarthquakesListener() {
			
			@Override
			public void getEarthquakesFailed(Exception error) {
				System.err.println(error);
			}
			
			@Override
			public void getEarthquakesCompleted(HashMap<String, Object> data) {
				System.out.println(data);
			}
		});
	}

}
