
#lang scribble/manual
 
@title{earthquake-service}
@author{author+email "" ""}

@section{Structs}
 
Get the latest information about earthquakes around the world.


@defproc[(make-report [area bounding-box?]
			[earthquakes (listof earthquake?)]
			[title string?]) report]{

Information about earthquakes matching certain criteria, including the area that they occurred.
@itemlist[

			@item{@racket[area] --- A region that contains all the earthquakes.}

			@item{@racket[earthquakes] --- A list of the earthquakes.}

			@item{@racket[title] --- A human-readable title that describes this data.}]}

@defproc[(make-earthquake [location coordinate?]
			[magnitude number?]
			[location-description string?]
			[time number?]
			[url string?]
			[felt-reports number?]
			[maximum-reported-intensity number?]
			[maximum-estimated-intensity number?]
			[alert-level string?]
			[event-source string?]
			[significance number?]
			[id string?]
			[distance number?]
			[root-mean-square number?]
			[gap number?]) earthquake]{

Information about a specific earthquake.
@itemlist[

			@item{@racket[location] --- The location of the earthquake.}

			@item{@racket[magnitude] --- The magnitude of the earthquake on the Richter Scale.}

			@item{@racket[location-description] --- A human-readable description of the location.}

			@item{@racket[time] --- The epoch time (http://en.wikipedia.org/wiki/Unix_time) when this earthquake occurred.}

			@item{@racket[url] --- A webpage with more information about the earthquake.}

			@item{@racket[felt-reports] --- The total number of "Felt" reports submitted, or null if the data is not available.}

			@item{@racket[maximum-reported-intensity] --- The maximum reported intensity for this earthquake, or null if the data is not available. While typically reported as a roman numeral, intensity is reported here as a decimal number. More information can be found at http://earthquake.usgs.gov/learn/topics/mag_vs_int.php}

			@item{@racket[maximum-estimated-intensity] --- The maximum estimated instrumental intensity for the event, or null if the data is not available. While typically reported as a roman numeral, intensity is reported here as the decimal equivalent. More information can be found at http://earthquake.usgs.gov/learn/topics/mag_vs_int.php}

			@item{@racket[alert-level] --- A color string (one of "green", "yellow", "orange", "red") indicating how dangerous the quake was, or null if the data is not available. More information about this kind of alert is available at http://earthquake.usgs.gov/research/pager/}

			@item{@racket[event-source] --- Either "AUTOMATIC", "PUBLISHED", or "REVIEWED". Indicates how the earthquake was identified and whether it was reviewed by a human.}

			@item{@racket[significance] --- A number describing how significant the event is. Larger numbers indicate a more significant event. This value is determined on a number of factors, including: magnitude, maximum estimated intensity, felt reports, and estimated impact.}

			@item{@racket[id] --- A uniquely identifying id for this earthquake.}

			@item{@racket[distance] --- Horizontal distance from the epicenter to the nearest station (in degrees), or null if the data is not available. 1 degree is approximately 111.2 kilometers. In general, the smaller this number, the more reliable is the calculated depth of the earthquake.}

			@item{@racket[root-mean-square] --- The root-mean-square (RMS) travel time residual, in sec, using all weights. This parameter provides a measure of the fit of the observed arrival times to the predicted arrival times for this location. Smaller numbers reflect a better fit of the data. The value is dependent on the accuracy of the velocity model used to compute the earthquake location, the quality weights assigned to the arrival time data, and the procedure used to locate the earthquake.}

			@item{@racket[gap] --- The largest azimuthal gap between azimuthally adjacent stations (in degrees), or null if the data is not available. In general, the smaller this number, the more reliable is the calculated horizontal position of the earthquake.}]}

@defproc[(make-coordinate [longitude number?]
			[latitude number?]
			[depth number?]) coordinate]{

The longitudinal, latitudinal, and depth where the earthquake occurred.
@itemlist[

			@item{@racket[longitude] --- The longitude (West-North) component.}

			@item{@racket[latitude] --- The latitude (South-North) component.}

			@item{@racket[depth] --- The depth (closer or farther from the surface) component.}]}

@defproc[(make-bounding-box [minimum-longitude number?]
			[minimum-latitude number?]
			[minimum-depth number?]
			[maximum-longitude number?]
			[maximum-latitude number?]
			[maximum-depth number?]) bounding-box]{

The longitudinal, latitudinal, and depth of the region required to display all the earthquakes.
@itemlist[

			@item{@racket[minimum-longitude] --- The lower longitude (West) component.}

			@item{@racket[minimum-latitude] --- The lower latitude (South) component.}

			@item{@racket[minimum-depth] --- The lower depth (closer or farther from the surface) component.}

			@item{@racket[maximum-longitude] --- The higher longitude (East) component.}

			@item{@racket[maximum-latitude] --- The higher latitude (North) component.}

			@item{@racket[maximum-depth] --- The higher depth (closer or farther from the surface) component.}]}



@section{Functions}

@defproc[(disconnect-earthquake-service ) void]{

Establishes that data will be retrieved locally.
@itemlist[

		]}

@defproc[(connect-earthquake-service ) void]{

Establishes that the online service will be used.
@itemlist[

		]}

@defproc[(get-earthquakes [threshold string?]
			[time string?]) report?]{

Retrieves information about earthquakes around the world.
@itemlist[

			@item{@racket[threshold] --- A string indicating what kind of earthquakes to report. Must be either "significant" (only significant earthquakes), "all" (all earthquakes, regardless of significance), "4.5", "2.5", or "1.0". Note that for the last three, all earthquakes at and above that level will be reported.}

			@item{@racket[time] --- A string indicating the time range of earthquakes to report. Must be either "hour" (only earthquakes in the past hour), "day" (only earthquakes that happened today), "week" (only earthquakes that happened in the past 7 days), or "month" (only earthquakes that happened in the past 30 days).}]}

