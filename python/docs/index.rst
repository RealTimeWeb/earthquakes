.. earthquake service documentation master file, created by
   sphinx-quickstart on Tue Jul 23 13:06:29 2013.
   You can adapt this file completely to your liking, but it should at least
   contain the root `toctree` directive.

Welcome to the Earthquake Service documentation!
================================================

The Earthquake library offers access to the United States Geological Survey's Earthquake feed. This is international data about earthquakes happening all over the world. They offer a lot of information, including magnitude and coordinates. You can get information as far back as the past month, or as most recent as the past hour. Note that this data stream has a very high velocity - if you check every five minutes, you'll find it may have already changed.

>>> import earthquakes

You can get a Report about the latest earthquakes.

>>> report = earthquakes.get_report()
>>> report
{'title': 'USGS Significant Earthquakes, Past Hour', 
 'earthquakes': [], 
 'area': {'maximum': {'latitude': 0.0, 'depth': 0.0, 'longitude': 0.0}, 
          'minimum': {'latitude': 0.0, 'depth': 0.0, 'longitude': 0.0}}}

By default, it only gets the significant earthquakes from the past hour. There are usually not that many significant earthquakes in the world, though.

>>> len(earthquakes.get_report('week', 'all')['earthquakes'])
1612
>>> report = earthquakes.get_report('hour', '1.0')
>>> len(report['earthquakes'])
4

Reports have a title, a list of earthquakes, and a boundary box that fits all the earthquakes.

>>> report['title']
'USGS Magnitude 1.0+ Earthquakes, Past Hour'
>>> report['earthquakes']
[ .. ]
>>> report['area']
{'maximum' : ... , 'minimum': ... }

A given earthquake has many properties available.

>>> quake = report['earthquakes'][0]
>>> quake
{ ... }
>>> quake['magnitude']
1.1
>>> quake['location']
{'latitude': ... , 'longitude': ..., 'depth': ...}
>>> quake['location_description']
'2km SW of The Geysers, California'
>>> quake['time'] # epoch time
1399423359500

Other fields: alert_level, distance, event_source, felt_reports, gap, id, maximum_estimated_intensity, maximum_reported_intensity, root_mean_square, significance, url.

.. warning:: Sometimes earthquake information is released before it is fully compiled, such as the number of felt_reports. Therefore, data may change between calls to the service. Moreover, older earthquakes in a Report may have more data available to them than newer ones.

The built-in cache allows you to work online:

>>> earthquakes.connect() # unnecessary: default is connected

or offline:

>>> earthquakes.disconnect()
>>> earthquakes.get_report('day', 'all')

But remember there must be data in the cache already!

report = earthquakes.get_report('hour', '1.0')
earthquakes.earthquakes.USGSException: No data was in the cache for this time and threshold ('hour', '1.0').

The cache can be configured to handle repeated calls differently. For example, if you want, you could make it return new results every time you call:

>>> len(earthquakes.get_report('hour', 'all')['earthquakes'])
9
>>> len(earthquakes.get_report('hour', 'all')['earthquakes'])
12
>>> len(earthquakes.get_report('hour', 'all')['earthquakes'])
6

Populating the cache
^^^^^^^^^^^^^^^^^^^^

Say you want to add the latest earthquakes to the cache:

>>> earthquakes._start_editing()
>>> earthquakes.get_posts('hour', '1.0')
>>> earthquakes._save_cache()

Now the file "cache.json" file will have an entry for ("hour", "1.0"), and
you can use that as an input to the function when disconnected.

You can also create a different cache file by passing a filename to the _save_cache() method, and use that cache by passing its name to the disconnect() method.

For example, this will populate a file called "violent.json", ideally taken during a time of high geologic activity.

>>> earthquakes._start_editing()
>>> earthquakes.get_posts('hour', '1.0')
>>> earthquakes._save_cache('violent.json')

To use that cached file, specify the json file name when you call disconnect():

>>> earthquakes.disconnect("violent.json")

Finally, you can put multiple entries into the cache for a given input, simulating multiple calls. These items will be appended. If the cache runs out, it will start returning empty reports.

>>> earthquakes.connect()
>>> earthquakes._start_editing()
>>> report = earthquakes.get_posts('hour', '1.0')
<Report USGS Magnitude 1.0+ Earthquakes, Past Hour, 9 Quakes>
>>> time.sleep(1000 * 60) # 1 hour
>>> earthquakes.get_posts('hour', '1.0')
<Report USGS Magnitude 1.0+ Earthquakes, Past Hour, 12 Quakes>
>>> earthquakes._save_cache()
>>> earthquakes.disconnect()
>>> earthquakes.get_posts('hour', '1.0')
<Report USGS Magnitude 1.0+ Earthquakes, Past Hour, 9 Quakes>
>>> earthquakes.get_posts('hour', '1.0')
<Report USGS Magnitude 1.0+ Earthquakes, Past Hour, 12 Quakes>
>>> earthquakes.get_posts('hour', '1.0')
<Report USGS Magnitude 1.0+ Earthquakes, Past Hour, 0 Quakes>
>>> earthquakes.get_posts('hour', '1.0')
<Report USGS Magnitude 1.0+ Earthquakes, Past Hour, 0 Quakes>

Tests
^^^^^

To run the unit tests from the command line:

>>> python -m tests.test

Further documentation is available in the docs/_builds/index.html file.

Methods
-------

.. autofunction:: earthquakes.connect

.. autofunction:: earthquakes.disconnect

.. autofunction:: earthquakes.get_report

Data Classes
------------

.. autoclass:: earthquakes.Report
    :members:
    :special-members: __init__
    
.. autoclass:: earthquakes.Earthquake
    :members:
    :special-members: __init__
    
.. autoclass:: earthquakes.BoundingBox
    :members:
    :special-members: __init__
    
.. autoclass:: earthquakes.Coordinate
    :members:
    :special-members: __init__
