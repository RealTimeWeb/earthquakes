import json

import operator
def lrange(num1, num2 = None, step = 1):
    op = operator.__lt__

    if num2 is None:
        num1, num2 = 0, num1
    if num2 < num1:
        if step > 0:
            num1 = num2
        op = operator.__gt__
    elif step < 0:
        num1 = num2

    while op(num1, num2):
        yield num1
        num1 += step


input_string = """http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson%{{time={}}}"""
URL = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson"
HOUR = 3600000
FIVE_MINUTES = 5 * 1000 * 60

def stagger_quakes(filename):
    with open(filename, "r") as input:
        earthquakes = json.load(input)
        first_earthquake = min([e["properties"]["time"] for e in earthquakes])
        last_earthquake = max([e["properties"]["time"] for e in earthquakes])
        binned_earthquakes = {}
        times = lrange(first_earthquake, last_earthquake, FIVE_MINUTES)
        for time_index, bin_time in enumerate(times):
            key = input_string.format(time_index)
            quakes = [earthquake for earthquake in earthquakes 
                        if bin_time <= earthquake["properties"]["time"] < bin_time+HOUR]
            if quakes:
                min_longitude = min([e["geometry"]["coordinates"][0] for e in quakes])
                min_latitude = min([e["geometry"]["coordinates"][1] for e in quakes])
                min_depth = min([e["geometry"]["coordinates"][2] for e in quakes])
                max_longitude = max([e["geometry"]["coordinates"][0] for e in quakes])
                max_latitude = max([e["geometry"]["coordinates"][1] for e in quakes])
                max_depth = max([e["geometry"]["coordinates"][2] for e in quakes])
                report = {"type": "FeatureCollection", 
                          "metadata": {"generated":bin_time, 
                                       "url":URL, 
                                       "title": "USGS All Earthquakes, Past Hour", 
                                       "status":200, 
                                       "api": "1.0.9", 
                                       "count": len(quakes)},
                          "features": quakes,
                          "bbox": [min_longitude, min_latitude, min_depth, max_longitude, max_latitude, max_depth]}
                binned_earthquakes[key] = json.dumps(report)
        return binned_earthquakes
            
json.dump(stagger_quakes("day_sparse.json"), open("normal.quakes", 'wb'))
