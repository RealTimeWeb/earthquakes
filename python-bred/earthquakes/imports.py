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