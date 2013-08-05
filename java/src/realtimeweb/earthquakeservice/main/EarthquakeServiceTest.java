package realtimeweb.earthquakeservice.main;

import realtimeweb.earthquakeservice.json.JsonEarthquakeService;
import realtimeweb.earthquakeservice.regular.EarthquakeService;
import realtimeweb.earthquakeservice.structured.StructuredEarthquakeService;

public class EarthquakeServiceTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JsonEarthquakeService jes = JsonEarthquakeService.getInstance();
		jes.connect();
		try {
			System.out.println(jes.getEarthquakes(Threshold.ALL, History.HOUR));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		StructuredEarthquakeService ses = StructuredEarthquakeService.getInstance();
		ses.connect();
		try {
			System.out.println(ses.getEarthquakes(Threshold.ALL, History.HOUR));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("\n");
		EarthquakeService res = EarthquakeService.getInstance();
		res.connect();
		try {
			System.out.println(res.getEarthquakes(Threshold.ALL, History.HOUR));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
