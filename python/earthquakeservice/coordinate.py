class Coordinate(object):
    """
    The longitudinal, latitudinal, and depth where the earthquake occurred.
    """
    def __init__(self, longitude, latitude, depth):
        """
        Creates a new Coordinate
        
        :param self: This object
        :type self: Coordinate
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
    
    @staticmethod
    def _from_json(json_data):
        """
        Creates a Coordinate from json data.
        
        :param json_data: The raw json data to parse
        :type json_data: dict
        :returns: Coordinate
        """
        return Coordinate(json_data[0],
                    json_data[1],
                    json_data[2])