class Boundingbox(object):
    """
    The longitudinal, latitudinal, and depth of the region required to display all the earthquakes.
    """
    def __init__(self, minimum_longitude, minimum_latitude, minimum_depth, maximum_longitude, maximum_latitude, maximum_depth):
        """
        Creates a new Boundingbox
        
        :param self: This object
        :type self: Boundingbox
        :param minimum_longitude: The lower longitude (West) component.
        :type minimum_longitude: float
        :param minimum_latitude: The lower latitude (South) component.
        :type minimum_latitude: float
        :param minimum_depth: The lower depth (closer or farther from the surface) component.
        :type minimum_depth: float
        :param maximum_longitude: The higher longitude (East) component.
        :type maximum_longitude: float
        :param maximum_latitude: The higher latitude (North) component.
        :type maximum_latitude: float
        :param maximum_depth: The higher depth (closer or farther from the surface) component.
        :type maximum_depth: float
        :returns: Boundingbox
        """
        self.minimum_longitude = minimum_longitude
        self.minimum_latitude = minimum_latitude
        self.minimum_depth = minimum_depth
        self.maximum_longitude = maximum_longitude
        self.maximum_latitude = maximum_latitude
        self.maximum_depth = maximum_depth
    
    @staticmethod
    def _from_json(json_data):
        """
        Creates a Boundingbox from json data.
        
        :param json_data: The raw json data to parse
        :type json_data: dict
        :returns: Boundingbox
        """
        return Boundingbox(json_data['0'],
                    json_data['1'],
                    json_data['2'],
                    json_data['0'],
                    json_data['1'],
                    json_data['2'])