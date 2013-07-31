from earthquake import Earthquake
from bounding_box import BoundingBox
class Report(object):
    """
    Information about earthquakes matching certain criteria, including the area that they occurred.
    """
    def __init__(self, area, earthquakes, title):
        """
        Creates a new Report
        
        :param self: This object
        :type self: Report
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
    
    @staticmethod
    def _from_json(json_data):
        """
        Creates a Report from json data.
        
        :param json_data: The raw json data to parse
        :type json_data: dict
        :returns: Report
        """
        return Report(BoundingBox._from_json(json_data['bbox']),
                    map(Earthquake._from_json, json_data['features']),
                    json_data['metadata']['title'])