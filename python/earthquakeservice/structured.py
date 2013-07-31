import requests
import json
import threading
from _cache import _recursively_convert_unicode_to_str, lookup
from report import Report
import raw_json
def connect():
    """
    Connect to the online data source in order to get up-to-date information.
    
    :returns: void
    """
    raw_json.connect()
def disconnect():
    """
    Connect to the local cache, so no internet connection is required.
    
    :returns: void
    """
    raw_json.disconnect()
def get_earthquakes(threshold, time):
    """
    Retrieves information about earthquakes around the world.
    
    :param threshold: A string indicating what kind of earthquakes to report. Must be either "significant" (only significant earthquakes), "all" (all earthquakes, regardless of significance), "4.5", "2.5", or "1.0". Note that for the last three, all earthquakes at and above that level will be reported.
    :type threshold: string
    :param time: A string indicating the time range of earthquakes to report. Must be either "hour" (only earthquakes in the past hour), "day" (only earthquakes that happened today), "week" (only earthquakes that happened in the past 7 days), or "month" (only earthquakes that happened in the past 30 days).
    :type time: string
    :returns: dict
    """
    return _recursively_convert_unicode_to_str(json.loads(raw_json.get_earthquakes(threshold, time)))

def get_earthquakes_async(callback, error_callback, threshold, time):
    """
    Asynchronous version of get_earthquakes
    
    :param callback: Function that consumes the data (a dict) returned on success.
    :type callback: function
    :param error_callback: Function that consumes the exception returned on failure.
    :type error_callback: function
    :param threshold: A string indicating what kind of earthquakes to report. Must be either "significant" (only significant earthquakes), "all" (all earthquakes, regardless of significance), "4.5", "2.5", or "1.0". Note that for the last three, all earthquakes at and above that level will be reported.
    :type threshold: string
    :param time: A string indicating the time range of earthquakes to report. Must be either "hour" (only earthquakes in the past hour), "day" (only earthquakes that happened today), "week" (only earthquakes that happened in the past 7 days), or "month" (only earthquakes that happened in the past 30 days).
    :type time: string
    :returns: void
    """
    def server_call(callback, error_callback, threshold, time):
        """
        Internal closure to thread this call.
        
        :param callback: Function that consumes the data (a dict) returned on success.
        :type callback: function
        :param error_callback: Function that consumes the exception returned on failure.
        :type error_callback: function
        :param threshold: A string indicating what kind of earthquakes to report. Must be either "significant" (only significant earthquakes), "all" (all earthquakes, regardless of significance), "4.5", "2.5", or "1.0". Note that for the last three, all earthquakes at and above that level will be reported.
        :type threshold: string
        :param time: A string indicating the time range of earthquakes to report. Must be either "hour" (only earthquakes in the past hour), "day" (only earthquakes that happened today), "week" (only earthquakes that happened in the past 7 days), or "month" (only earthquakes that happened in the past 30 days).
        :type time: string
        :returns: void
        """
        try:
            callback(get_earthquakes(threshold, time))
        except Exception, e:
            error_callback(e)
    threading.Thread(target=server_call, args = (threshold, time)).start()

