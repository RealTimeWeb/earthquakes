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

