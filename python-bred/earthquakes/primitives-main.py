def get_report(time='hour', threshold='significant'):
    """
    Retrieves a new Report about recent earthquakes.
    
    :param str time: A string indicating the time range of earthquakes to report. Must be either "hour" (only earthquakes in the past hour), "day" (only earthquakes that happened today), "week" (only earthquakes that happened in the past 7 days), or "month" (only earthquakes that happened in the past 30 days).
    :param str threshold: A string indicating what kind of earthquakes to report. Must be either "significant" (only significant earthquakes), "all" (all earthquakes, regardless of significance), "4.5", "2.5", or "1.0". Note that for the last three, all earthquakes at and above that level will be reported.
    :returns: A list of dictionaries representing earthquakes (each dictionary has the fields 'magnitude', 'time', and 'location')
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
        return []
    elif result:
        try:
            json_result = _from_json(result)
        except ValueError:
            raise USGSException("The response from the server didn't make any sense.")
        report = Report._from_json(json_result)
        return [{'magnitude': e.magnitude, 'time': e.time, 'location': e.location_description} for e in report.earthquakes]
    else:
        if _CONNECTED:
            raise USGSException("No response from the server.")
        else:
            raise USGSException("No data was in the cache for this time and threshold ('{}', '{}').".format(time, threshold))
    