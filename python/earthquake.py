from coordinate import Coordinate
from long import Long
class Earthquake(object):
    """
    Information about a specific earthquake.
    """
    def __init__(self, location, magnitude, location_description, time, url, felt_reports, maximum_reported_intensity, maximum_estimated_intensity, alert_level, event_source, significance, id, distance, root_mean_square, gap):
        """
        Creates a new Earthquake
        
        :param self: This object
        :type self: Earthquake
        :param location: The location of the earthquake.
        :type location: Coordinate
        :param magnitude: The magnitude of the earthquake on the Richter Scale.
        :type magnitude: float
        :param location_description: A human-readable description of the location.
        :type location_description: string
        :param time: The epoch time (http://en.wikipedia.org/wiki/Unix_time) when this earthquake occurred.
        :type time: int
        :param url: A webpage with more information about the earthquake.
        :type url: string
        :param felt_reports: The total number of "Felt" reports submitted, or null if the data is not available.
        :type felt_reports: int
        :param maximum_reported_intensity: The maximum reported intensity for this earthquake, or null if the data is not available. While typically reported as a roman numeral, intensity is reported here as a decimal number. More information can be found at http://earthquake.usgs.gov/learn/topics/mag_vs_int.php
        :type maximum_reported_intensity: float
        :param maximum_estimated_intensity: The maximum estimated instrumental intensity for the event, or null if the data is not available. While typically reported as a roman numeral, intensity is reported here as the decimal equivalent. More information can be found at http://earthquake.usgs.gov/learn/topics/mag_vs_int.php
        :type maximum_estimated_intensity: float
        :param alert_level: A color string (one of "green", "yellow", "orange", "red") indicating how dangerous the quake was, or null if the data is not available. More information about this kind of alert is available at http://earthquake.usgs.gov/research/pager/
        :type alert_level: string
        :param event_source: Either "AUTOMATIC", "PUBLISHED", or "REVIEWED". Indicates how the earthquake was identified and whether it was reviewed by a human.
        :type event_source: string
        :param significance: A number describing how significant the event is. Larger numbers indicate a more significant event. This value is determined on a number of factors, including: magnitude, maximum estimated intensity, felt reports, and estimated impact.
        :type significance: int
        :param id: A uniquely identifying id for this earthquake.
        :type id: string
        :param distance: Horizontal distance from the epicenter to the nearest station (in degrees), or null if the data is not available. 1 degree is approximately 111.2 kilometers. In general, the smaller this number, the more reliable is the calculated depth of the earthquake.
        :type distance: float
        :param root_mean_square: The root-mean-square (RMS) travel time residual, in sec, using all weights. This parameter provides a measure of the fit of the observed arrival times to the predicted arrival times for this location. Smaller numbers reflect a better fit of the data. The value is dependent on the accuracy of the velocity model used to compute the earthquake location, the quality weights assigned to the arrival time data, and the procedure used to locate the earthquake.
        :type root_mean_square: float
        :param gap: The largest azimuthal gap between azimuthally adjacent stations (in degrees), or null if the data is not available. In general, the smaller this number, the more reliable is the calculated horizontal position of the earthquake.
        :type gap: float
        :returns: Earthquake
        """
        self.location = location
        self.magnitude = magnitude
        self.location_description = location_description
        self.time = time
        self.url = url
        self.felt_reports = felt_reports
        self.maximum_reported_intensity = maximum_reported_intensity
        self.maximum_estimated_intensity = maximum_estimated_intensity
        self.alert_level = alert_level
        self.event_source = event_source
        self.significance = significance
        self.id = id
        self.distance = distance
        self.root_mean_square = root_mean_square
        self.gap = gap
    
    @staticmethod
    def _from_json(json_data):
        """
        Creates a Earthquake from json data.
        
        :param json_data: The raw json data to parse
        :type json_data: dict
        :returns: Earthquake
        """
        return Earthquake(coordinate._from_json(json_data['geometry']['coordinates']),
                    json_data['properties']['mag'],
                    json_data['properties']['place'],
                    json_data['properties']['time'],
                    json_data['properties']['url'],
                    json_data['properties']['felt'],
                    json_data['properties']['cdi'],
                    json_data['properties']['mmi'],
                    json_data['properties']['alert'],
                    json_data['properties']['status'],
                    json_data['properties']['sig'],
                    json_data['properties']['id'],
                    json_data['properties']['dmin'],
                    json_data['properties']['rms'],
                    json_data['properties']['gap'])