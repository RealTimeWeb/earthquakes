package realtimeweb.earthquakeservice.main;

import java.util.HashMap;

import realtimeweb.earthquakeservice.json.JsonEarthquakeService;
import realtimeweb.earthquakeservice.regular.EarthquakeService;
import realtimeweb.earthquakeservice.structured.StructuredEarthquakeService;
import realtimeweb.earthquakeservice.structured.StructuredGetEarthquakesListener;

public class EarthquakeServiceTest {

	public static void main(String[] args) {
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
		
		EarthquakeService res = EarthquakeService.getInstance();
		res.connect();
		try {
			System.out.println(res.getEarthquakes(Threshold.ALL, History.HOUR));
		} catch (Exception e) {
			e.printStackTrace();
		}
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
