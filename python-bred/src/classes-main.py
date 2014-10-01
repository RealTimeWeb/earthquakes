import sys
PYTHON_3 = sys.version_info >= (3, 0)
import urllib
HEADER = { 'User-Agent' : 'RealTimeWeb Earthquake library for educational purposes' }
if PYTHON_3:
    import urllib.request as request
    from urllib.parse import quote_plus
    from urllib.error import HTTPError
else:
    import urllib2
    from urllib import quote_plus
    from urllib2 import HTTPError
try:
    import simplejson as json
except ImportError:
    import json
from datetime import datetime

################################################################################
# Auxilary
################################################################################

def urlencode(query, params):
    """
    Correctly convert the given query and parameters into a full query+query
    string, ensuring the order of the params.
    """
    return query + '?' + "&".join(key+'='+quote_plus(str(value)) 
                                  for key, value in params)

def _parse_int(value, default=0):
    """
    Attempt to cast *value* into an integer, returning *default* if it fails.
    """
    if value is None:
        return default
    try:
        return int(value)
    except ValueError:
        return default

def _parse_float(value, default=0.0):
    """
    Attempt to cast *value* into a float, returning *default* if it fails.
    """
    if value is None:
        return default
    try:
        return float(value)
    except ValueError:
        return default

def _parse_boolean(value, default=False):
    """
    Attempt to cast *value* into a bool, returning *default* if it fails.
    """
    if value is None:
        return default
    try:
        return bool(value)
    except ValueError:
        return default
        
def _get(url):
    """
    Convert a URL into it's response (a *str*).
    """
    if PYTHON_3:
        req = request.Request(url, headers=HEADER)
        response = request.urlopen(req)
        return response.read().decode('utf-8')
    else:
        req = urllib2.Request(url, headers=HEADER)
        response = urllib2.urlopen(req)
        return response.read()
        
def _recursively_convert_unicode_to_str(input):
    """
    Force the given input to only use `str` instead of `bytes` or `unicode`.
    This works even if the input is a dict, list, 
    """
    if isinstance(input, dict):
        return {_recursively_convert_unicode_to_str(key): _recursively_convert_unicode_to_str(value) for key, value in input.items()}
    elif isinstance(input, list):
        return [_recursively_convert_unicode_to_str(element) for element in input]
    elif not PYTHON_3 and isinstance(input, unicode):
        return input.encode('utf-8')
    else:
        return input

def _from_json(data):
    """
    Convert the given string data into a JSON dict/list/primitive, ensuring that
    `str` are used instead of bytes.
    """
    return _recursively_convert_unicode_to_str(json.loads(data))
################################################################################
# Cache
################################################################################

_CACHE = {}
_CACHE_COUNTER = {}
_EDITABLE = False
_CONNECTED = True
_PATTERN = "empty"
def _start_editing(pattern=_PATTERN):
    """
    Start adding seen entries to the cache. So, every time that you make a request,
    it will be saved to the cache. You must :ref:`_save_cache` to save the
    newly edited cache to disk, though!
    """
    global _EDITABLE, _PATTERN
    _EDITABLE = True
    _PATTERN = pattern
def _stop_editing():
    """
    Stop adding seen entries to the cache.
    """
    global _EDITABLE
    _EDITABLE = False
def connect():
    """
    Connect to the online data source in order to get up-to-date information.
    :returns: void
    """
    global _CONNECTED
    _CONNECTED = True
def disconnect(filename="cache.json"):
    """
    Connect to the local cache, so no internet connection is required.
    :returns: void
    """
    global _CONNECTED, _CACHE
    try:
        with open(filename, 'r') as f:
            _CACHE = _recursively_convert_unicode_to_str(json.load(f))['data']
    except FileNotFoundError:
        raise USGSException("""The cache file '{0}' was not found, and I cannot disconnect without one. If you have not been given a cache.json file, then you can create a new one:
    >>> from earthquakes import earthquakes
    >>> earthquakes.connect()
    >>> earthquakes._start_editing()
    ...
    >>> earthquakes.get_report()
    ...
    >>> earthquakes._save_cache('{0}')
""".format(filename))
    for key in _CACHE.keys():
        _CACHE_COUNTER[key] = 0
    _CONNECTED = False
def _lookup(key):
    """
    Internal method that looks up a key in the local cache.
    :param key: Get the value based on the key from the cache.
    :type key: string
    :returns: void
    """
    if key not in _CACHE:
        return None
    if _CACHE_COUNTER[key] >= len(_CACHE[key][1:]):
        if _CACHE[key][0] == "empty":
            return ""
        elif _CACHE[key][0] == "repeat" and _CACHE[key][1:]:
            return _CACHE[key][-1]
        elif _CACHE[key][0] == "repeat":
            return ""
        else:
            _CACHE_COUNTER[key] = 1
    else:
        _CACHE_COUNTER[key] += 1
    if _CACHE[key]:
        return _CACHE[key][_CACHE_COUNTER[key]]
    else:
        return ""
def _add_to_cache(key, value):
    """
    Internal method to add a new key-value to the local cache.
    :param str key: The new url to add to the cache
    :param str value: The HTTP response for this key.
    :returns: void
    """
    if key in _CACHE:
        _CACHE[key].append(value)
    else:
        _CACHE[key] = [_PATTERN, value]
        _CACHE_COUNTER[key] = 0
        
def _clear_key(key):
    """
    Internal method to remove a key from the local cache.
    :param str key: The url to remove from the cache
    """
    if key in _CACHE:
        del _CACHE[key]
        
def _save_cache(filename="cache.json"):
    """
    Internal method to save the cache in memory to a file, so that it can be used later.
    
    :param str filename: the location to store this at.
    """
    with open(filename, 'w') as f:
        json.dump({"data": _CACHE, "metadata": ""}, f)
        
################################################################################
# Exceptions
################################################################################
class USGSException(Exception):
    pass

        
################################################################################
# Domain Objects
################################################################################
    
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

################################################################################
# Service Methods
################################################################################

THRESHOLDS = ('significant', 'all', '4.5', '2.5', '1.0')
TIMES = ('hour', 'day', 'week', 'month')

def _get_report_request(time='hour', threshold='significant'):
    """
    Used to build the request string used by :func:`get_report`.
    
    :param str time: A string indicating the time range of earthquakes to report. Must be either "hour" (only earthquakes in the past hour), "day" (only earthquakes that happened today), "week" (only earthquakes that happened in the past 7 days), or "month" (only earthquakes that happened in the past 30 days).
    :param str threshold: A string indicating what kind of earthquakes to report. Must be either "significant" (only significant earthquakes), "all" (all earthquakes, regardless of significance), "4.5", "2.5", or "1.0". Note that for the last three, all earthquakes at and above that level will be reported.
    """
    return "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/{}_{}.geojson".format(threshold, time)

def _get_report_string(time='hour', threshold='significant'):
    """
    Like :func:`get_report` except returns the raw data instead.
    
    :param str time: A string indicating the time range of earthquakes to report. Must be either "hour" (only earthquakes in the past hour), "day" (only earthquakes that happened today), "week" (only earthquakes that happened in the past 7 days), or "month" (only earthquakes that happened in the past 30 days).
    :param str threshold: A string indicating what kind of earthquakes to report. Must be either "significant" (only significant earthquakes), "all" (all earthquakes, regardless of significance), "4.5", "2.5", or "1.0". Note that for the last three, all earthquakes at and above that level will be reported.
    :returns: str
    """
    key = _get_report_request(time, threshold)
    result = _get(key) if _CONNECTED else _lookup(key)
    if _CONNECTED and _EDITABLE:
        _add_to_cache(key, result)
    return result

def get_report(time='hour', threshold='significant'):
    """
    Retrieves a new Report about recent earthquakes.
    
    :param str time: A string indicating the time range of earthquakes to report. Must be either "hour" (only earthquakes in the past hour), "day" (only earthquakes that happened today), "week" (only earthquakes that happened in the past 7 days), or "month" (only earthquakes that happened in the past 30 days).
    :param str threshold: A string indicating what kind of earthquakes to report. Must be either "significant" (only significant earthquakes), "all" (all earthquakes, regardless of significance), "4.5", "2.5", or "1.0". Note that for the last three, all earthquakes at and above that level will be reported.
    :returns: :ref:`Report`
    """
    if threshold not in THRESHOLDS:
        raise USGSException('Unknown threshold: "{}" (must be either "significant", "all", "4.5", "2.5", or "1.0")'.format(threshold))
    if time not in TIMES:
        raise USGSException('Unknown time: "{}" (must be either "hour", "day", "week", "month")'.format(time))
    try:
        result = _get_report_string(time, threshold)
    except HTTPError as e:
        raise USGSException("Internet error ({}): {}".format(e.code, e.reason))
    if result == "":
        formatted_threshold = 'Magnitude {}+' if threshold not in ('significant', 'all') else threshold.title()
        return Report._from_json({'metadata': {'title': 'USGS {} Earthquakes, Past {}'.format(formatted_threshold, time.title())}})
    elif result:
        try:
            json_result = _from_json(result)
        except ValueError:
            raise USGSException("The response from the server didn't make any sense.")
        return Report._from_json(json_result)
    else:
        if _CONNECTED:
            raise USGSException("No response from the server.")
        else:
            raise USGSException("No data was in the cache for this time and threshold ('{}', '{}').".format(time, threshold))
    