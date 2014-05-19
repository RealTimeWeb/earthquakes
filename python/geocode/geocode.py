from __future__ import print_function
import sys
import json

HEADER = {'User-Agent': 'RealTimeWeb Earthquake library for educational purposes'}
PYTHON_3 = sys.version_info >= (3, 0)

if PYTHON_3:
    import urllib.request as request
    from urllib.parse import quote_plus
else:
    import urllib2
    from urllib import quote_plus


class GeocodeException(Exception):
    pass


def _iteritems(d):
    """
    Factor-out Py2-to-3 differences in dictionary item iterator methods
    """
    if PYTHON_3:
        return d.items()
    else:
        return d.iteritems()


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


def _urlencode(query, params):
    """
    Correctly convert the given query and parameters into a full query+query
    string, ensuring the order of the params.
    """
    return query + '?' + '&'.join(key+'='+quote_plus(str(value))
                                  for key, value in _iteritems(params))


def _get_coords(json_res):
    """
    Return a dict containing the longitude and latitude from json response
    """
    location = json_res['results'][0]['geometry']['location']
    lat = location['lat']
    long = location['lng']
    coords = {'latitude': lat, 'longitude': long}
    return coords


def code(address):
    """
    Convert an address to it's respective longitude and latitude
    """
    if not address or not isinstance(address, str):
        raise GeocodeException('No valid address was given')

    params = {'address': address}

    baseurl = 'https://maps.googleapis.com/maps/api/geocode/json'
    query = _urlencode(baseurl, params)
    query = ''.join((query, '&sensor=false'))

    response = _get(query)
    json_res = json.loads(response)
    status = json_res['status']

    if status:
        if 'ZERO_RESULTS' in status:
            raise GeocodeException('Sorry no results found')
        elif not 'OK' in status:
            raise GeocodeException(json_res['error_message'])

    return _get_coords(json_res)
