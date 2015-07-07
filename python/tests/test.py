import sys
PYTHON_3 = sys.version_info >= (3, 0)

import earthquakes.earthquakes as earthquakes
import unittest

class TestEarthquakesOnline(unittest.TestCase):
    def setUp(self):
        earthquakes.connect()
        
    def test_reports_dict(self):
        for threshold in ('significant', '4.5', '2.5', '1.0', 'all'):
            for time in ('hour', 'day', 'week', 'month')[:1]:
                report = None
                attempts = 0
                while report is None and attempts < 10:
                    try:
                        report = earthquakes.get_report(time, threshold)
                        mags = earthquakes.get_magnitudes(time, threshold)
                    except earthquakes.USGSException:
                        print("Possible connection error, retrying")
                        attempts += 1
                self.assertIsInstance(mags, list)
                self.assertIsInstance(report, dict, msg='input {}, {}'.format(time, threshold))
                self.assertIsInstance(report['title'], str, msg='input {}, {}'.format(time, threshold))
                self.assertIsInstance(report['area'], dict, msg='input {}, {}'.format(time, threshold))
                self.assertIsInstance(report['area']['minimum'], dict, msg='input {}, {}'.format(time, threshold))
                self.assertIsInstance(report['area']['minimum']['longitude'], float, msg='input {}, {}'.format(time, threshold))
                self.assertIsInstance(report['area']['minimum']['latitude'], float, msg='input {}, {}'.format(time, threshold))
                self.assertIsInstance(report['area']['minimum']['depth'], float, msg='input {}, {}'.format(time, threshold))
                self.assertIsInstance(report['area']['maximum'], dict, msg='input {}, {}'.format(time, threshold))
                self.assertIsInstance(report['area']['maximum']['longitude'], float, msg='input {}, {}'.format(time, threshold))
                self.assertIsInstance(report['area']['maximum']['latitude'], float, msg='input {}, {}'.format(time, threshold))
                self.assertIsInstance(report['area']['maximum']['depth'], float, msg='input {}, {}'.format(time, threshold))
        self.assertTrue(report['earthquakes'])
        quake = report['earthquakes'][0]
        self.assertIsInstance(quake, dict)
        self.assertIsInstance(quake['location'], dict)
        self.assertIsInstance(quake['location']['longitude'], float)
        self.assertIsInstance(quake['location']['latitude'], float)
        self.assertIsInstance(quake['location']['depth'], float)
        self.assertIsInstance(quake['magnitude'], float)
        self.assertIsInstance(quake['location_description'], str)
        if PYTHON_3:
            self.assertIsInstance(quake['time'], int)
        else:
            self.assertIsInstance(quake['time'], ( int, long ))
        self.assertIsInstance(quake['url'], str)
        self.assertIsInstance(quake['felt_reports'], int)
        self.assertIsInstance(quake['maximum_reported_intensity'], float)
        self.assertIsInstance(quake['maximum_estimated_intensity'], float)
        self.assertIsInstance(quake['alert_level'], str)
        self.assertIsInstance(quake['event_source'], str)
        self.assertIsInstance(quake['significance'], int)
        self.assertIsInstance(quake['id'], str)
        self.assertIsInstance(quake['distance'], float)
        self.assertIsInstance(quake['root_mean_square'], float)
        self.assertIsInstance(quake['gap'], float)
    
    def test_reports_classy(self):
        for threshold in ('significant', '4.5', '2.5', '1.0', 'all'):
            for time in ('hour', 'day', 'week', 'month'):
                report = earthquakes.get_report(time, threshold)
                self.assertIsInstance(report, earthquakes.Report, msg='input {}, {}'.format(time, threshold))
                self.assertIsInstance(report.title, str, msg='input {}, {}'.format(time, threshold))
                self.assertIsInstance(report.area, earthquakes.BoundingBox, msg='input {}, {}'.format(time, threshold))
                self.assertIsInstance(report.area.minimum, earthquakes.Coordinate, msg='input {}, {}'.format(time, threshold))
                self.assertIsInstance(report.area.minimum.longitude, float, msg='input {}, {}'.format(time, threshold))
                self.assertIsInstance(report.area.minimum.latitude, float, msg='input {}, {}'.format(time, threshold))
                self.assertIsInstance(report.area.minimum.depth, float, msg='input {}, {}'.format(time, threshold))
                self.assertIsInstance(report.area.maximum, earthquakes.Coordinate, msg='input {}, {}'.format(time, threshold))
                self.assertIsInstance(report.area.maximum.longitude, float, msg='input {}, {}'.format(time, threshold))
                self.assertIsInstance(report.area.maximum.latitude, float, msg='input {}, {}'.format(time, threshold))
                self.assertIsInstance(report.area.maximum.depth, float, msg='input {}, {}'.format(time, threshold))
        self.assertTrue(report.earthquakes)
        quake = report.earthquakes[0]
        self.assertIsInstance(quake, earthquakes.Earthquake)
        self.assertIsInstance(quake.location, earthquakes.Coordinate)
        self.assertIsInstance(quake.location.longitude, float)
        self.assertIsInstance(quake.location.latitude, float)
        self.assertIsInstance(quake.location.depth, float)
        self.assertIsInstance(quake.magnitude, float)
        self.assertIsInstance(quake.location_description, str)
        self.assertIsInstance(quake.time, int)
        self.assertIsInstance(quake.url, str)
        self.assertIsInstance(quake.felt_reports, int)
        self.assertIsInstance(quake.maximum_reported_intensity, float)
        self.assertIsInstance(quake.maximum_estimated_intensity, float)
        self.assertIsInstance(quake.alert_level, str)
        self.assertIsInstance(quake.event_source, str)
        self.assertIsInstance(quake.significance, int)
        self.assertIsInstance(quake.id, str)
        self.assertIsInstance(quake.distance, float)
        self.assertIsInstance(quake.root_mean_square, float)
        self.assertIsInstance(quake.gap, float)
    def test_empty_subearthquakes(self):
        with self.assertRaises(earthquakes.USGSException):
            earthquakes.get_report("forever", "all")
        with self.assertRaises(earthquakes.USGSException):
            earthquakes.get_report("hour", "some")
            
class TestearthquakesEditing(unittest.TestCase):
    def setUp(self):
        earthquakes.connect()

    def test_cache(self):
        earthquakes._start_editing()
        earthquakes.get_report("hour")
        earthquakes.get_report("day")
        earthquakes.get_report("week")
        earthquakes.get_report("hour", 'all')
        earthquakes.get_report("day", 'all')
        earthquakes.get_report("week", 'all')
        earthquakes.get_report("week", 'all')
        earthquakes.get_report("week", 'all')
        self.assertEqual(len(earthquakes._CACHE), 6)
        
    def tearDown(self):
        earthquakes._save_cache("cache.json")
        

class TestearthquakesOffline(unittest.TestCase):
    def setUp(self):
        earthquakes.disconnect()
    
    def test_virginiatech(self):
        with self.assertRaises(earthquakes.USGSException):
            earthquakes.get_report("month")
    def test_askearthquakes(self):
        p1 = earthquakes.get_report("week")
        self.assertIsInstance(p1, dict)
    
    def test_runningout(self):
        earthquakes.get_report("hour", 'all')
        earthquakes.get_report("hour", 'all')
        earthquakes.get_report("hour", 'all')
        empty = earthquakes.get_report("hour", 'all')
        self.assertIsInstance(empty, dict)
        self.assertFalse(empty['earthquakes'])
   
if __name__ == '__main__':
    unittest.main()