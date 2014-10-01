class Coordinate(object):
    """
    The longitudinal, latitudinal, and depth where the earthquake occurred.
    """
    def __init__(self, longitude, latitude, depth):
        """
        Creates a new Coordinate
        
        :param longitude: The longitude (West-North) component.
        :type longitude: float
        :param latitude: The latitude (South-North) component.
        :type latitude: float
        :param depth: The depth (closer or farther from the surface) component.
        :type depth: float
        :returns: Coordinate
        """
        self.longitude = longitude
        self.latitude = latitude
        self.depth = depth
        
    def __unicode__(self):
        return '<Coordinate {0:.3f}, {1:.3f}>'.format(self.longitude, self.latitude)
    
    def __repr__(self):
        if PYTHON_3:
            return '<Coordinate {0:.3f}, {1:.3f}>'.format(self.longitude, self.latitude)
        else:
            return unicode(self).encode('utf-8')
    
    def __str__(self):
        if PYTHON_3:
            return '<Coordinate {0:.3f}, {1:.3f}>'.format(self.longitude, self.latitude)
        else:
            return unicode(self).encode('utf-8')
    
    @staticmethod
    def _from_json(json_data):
        """
        Creates a Coordinate from json data.
        
        :param json_data: The raw json data to parse
        :type json_data: dict
        :returns: Coordinate
        """
        if len(json_data) >= 3:
            return Coordinate(_parse_float(json_data[0]),
                        _parse_float(json_data[1]),
                        _parse_float(json_data[2]))
        else:
            raise USGSException("The given coordinate information was incomplete.")
            
class BoundingBox(object):
    """
    The minimum and maximum coordinates needed for to display all the earthquakes in this report.
    """
    def __init__(self, minimum, maximum):
        """
        Creates a new BoundingBox
        
        :param minimum: The lower, South-West component.
        :type minimum: Coordinate
        :param maximum: The upper, North-East component.
        :type maximum: Coordinate
        :returns: BoundingBox
        """
        self.minimum = minimum
        self.maximum = maximum
        
    def __unicode__(self):
        return '<Box ({0:.2f}, {1:.2f}), ({2:.2f}, {3:.2f})>'.format(self.minimum.longitude, self.minimum.latitude, self.maximum.longitude, self.maximum.latitude)
    
    def __repr__(self):
        if PYTHON_3:
            return '<Box ({0:.2f}, {1:.2f}), ({2:.2f}, {3:.2f})>'.format(self.minimum.longitude, self.minimum.latitude, self.maximum.longitude, self.maximum.latitude)
        else:
            return unicode(self).encode('utf-8')
    
    def __str__(self):
        if PYTHON_3:
            return '<Box ({0:.2f}, {1:.2f}), ({2:.2f}, {3:.2f})>'.format(self.minimum.longitude, self.minimum.latitude, self.maximum.longitude, self.maximum.latitude)
        else:
            return unicode(self).encode('utf-8')
    
    @staticmethod
    def _from_json(json_data):
        """
        Creates a BoundingBox from json data.
        
        :param json_data: The raw json data to parse
        :type json_data: dict
        :returns: BoundingBox
        """
        if len(json_data) >= 6:
            return BoundingBox(
                        Coordinate(_parse_float(json_data[0]),
                                   _parse_float(json_data[1]),
                                   _parse_float(json_data[2])),
                        Coordinate(_parse_float(json_data[3]),
                                   _parse_float(json_data[4]),
                                   _parse_float(json_data[5])))
        else:
            raise USGSException("The bounding box information was incomplete.")
            
class Earthquake(object):
    """
    Information about a specific earthquake.
    """
    def __init__(self, location, magnitude, location_description, time, url, felt_reports, maximum_reported_intensity, maximum_estimated_intensity, alert_level, event_source, significance, id, distance, root_mean_square, gap):
        """
        Creates a new Earthquake
        
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
        
    def __unicode__(self):
        return '<Earthquake {}>'.format(self.id)
    
    def __repr__(self):
        if PYTHON_3:
            return '<Earthquake {}>'.format(self.id)
        else:
            return unicode(self).encode('utf-8')
    
    def __str__(self):
        if PYTHON_3:
            return '<Earthquake {}>'.format(self.id)
        else:
            return unicode(self).encode('utf-8')
    
    @staticmethod
    def _from_json(json_data):
        """
        Creates a Earthquake from json data.
        
        :param json_data: The raw json data to parse
        :type json_data: dict
        :returns: Earthquake
        """
        try:
            coordinates = json_data['geometry']['coordinates']
        except KeyError:
            raise USGSException("The geometry information was not returned from the USGS website.")
        try:
            properties = json_data['properties']
        except KeyError:
            raise USGSException("One of the earthquakes did not have any property information")
        return Earthquake(Coordinate._from_json(coordinates),
                          _parse_float(properties.get('mag', '0'), 0.0),
                          properties.get('place', ''),
                          _parse_int(properties.get('time', '0'), 0),
                          properties.get('url', ''),
                          _parse_int(properties.get('felt', '0'), 0),
                          _parse_float(properties.get('cdi', '0'), 0.0),
                          _parse_float(properties.get('mmi', '0'), 0.0),
                          properties['alert'] if 'alert' in properties and properties['alert'] else '',
                          properties.get('status', ''),
                          _parse_int(properties.get('sig', '0'), 0),
                          json_data.get('id', ''),
                          _parse_float(properties.get('dmin', '0'), 0.0),
                          _parse_float(properties.get('rms', '0'), 0.0),
                          _parse_float(properties.get('gap', '0'), 0.0))
                          
class Report(object):
    """
    Information about earthquakes matching certain criteria, including the area that they occurred.
    """
    def __init__(self, area, earthquakes, title):
        """
        Creates a new Report
        
        :param area: A region that contains all the earthquakes.
        :type area: BoundingBox
        :param earthquakes: A list of the earthquakes.
        :type earthquakes: listof Earthquake
        :param title: A human-readable title that describes this data.
        :type title: string
        :returns: Report
        """
        self.area = area
        self.earthquakes = earthquakes
        self.title = title
        
    def __unicode__(self):
        return '<Report {}, {} Quakes>'.format(self.title, len(self.earthquakes))
    
    def __repr__(self):
        if PYTHON_3:
            return '<Report {}, {} Quakes>'.format(self.title, len(self.earthquakes))
        else:
            return unicode(self).encode('utf-8')
    
    def __str__(self):
        if PYTHON_3:
            return '<Report {}, {} Quakes>'.format(self.title, len(self.earthquakes))
        else:
            return unicode(self).encode('utf-8')
    
    @staticmethod
    def _from_json(json_data):
        """
        Creates a Report from json data.
        
        :param json_data: The raw json data to parse
        :type json_data: dict
        :returns: Report
        """
        if 'bbox' in json_data:
            box = BoundingBox._from_json(json_data['bbox'])
        else:
            box = BoundingBox(Coordinate(0.,0.,0.), Coordinate(0.,0.,0.))
        if 'features' in json_data and json_data['features']:
            quakes = list(map(Earthquake._from_json, json_data['features']))
        else:
            quakes = []
        try:
            title = json_data['metadata']['title']
        except KeyError:
            raise USGSException("No report title information returned by server")
        return Report(box, quakes, title)
