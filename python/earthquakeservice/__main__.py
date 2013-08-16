import regular as earthquake_service
import datetime
from collections import Counter

report = earthquake_service.get_earthquakes("all", "month")

#print map(lambda x: x.time, report.earthquakes)

days = map(lambda x : x.time.day, report.earthquakes)

print Counter(days)