package realtimeweb.earthquakeservice.main;

import realtimeweb.earthquakeservice.domain.History;
import realtimeweb.earthquakeservice.domain.Report;
import realtimeweb.earthquakeservice.domain.Threshold;
import realtimeweb.earthquakeservice.json.JsonEarthquakeService;
import realtimeweb.earthquakeservice.regular.EarthquakeService;

public class EarthquakeServiceTest {

	public static void main(String[] args) {
		
		JsonEarthquakeService jes = JsonEarthquakeService.getInstance();
		jes.disconnect();
		try {
			//String quakes = jes.getEarthquakes(Threshold.ALL, History.HOUR);
			//System.out.println("Quakes:"+quakes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		EarthquakeService es = EarthquakeService.getInstance();
		es.disconnect();
		try {
			while (true) {
			Report q = es.getEarthquakes(Threshold.ALL, History.HOUR);
			System.out.println(q.getEarthquakes().size());
			Thread.sleep(100);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
