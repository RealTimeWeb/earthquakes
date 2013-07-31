#lang racket

; Provide the external structs
(provide (struct-out report))
(provide (struct-out earthquake))
(provide (struct-out coordinate))
(provide (struct-out bounding-box))

; Provide the external functions
(provide get-earthquakes)
(provide get-earthquakes/string)
(provide get-earthquakes/json)
(provide disconnect-earthquake-service)
(provide connect-earthquake-service)

; Load the internal libraries
(require net/url)
(require srfi/19)
(require srfi/6)
(require racket/port)
(require json)
(require net/uri-codec)

; Define the structs
(define-struct report (area earthquakes title))

(define-struct earthquake (location magnitude location-description time url felt-reports maximum-reported-intensity maximum-estimated-intensity alert-level event-source significance id distance root-mean-square gap))

(define-struct coordinate (longitude latitude depth))

(define-struct bounding-box (minimum-longitude minimum-latitude minimum-depth maximum-longitude maximum-latitude maximum-depth))

(define (json->report jdata)
	(make-report (json->bounding-box (hash-ref jdata 'bbox))
			(map json->earthquake (hash-ref jdata 'features))
			(hash-ref (hash-ref jdata 'metadata) 'title)))

(define (json->earthquake jdata)
	(make-earthquake (json->coordinate (hash-ref (hash-ref jdata 'geometry) 'coordinates))
			(hash-ref (hash-ref jdata 'properties) 'mag)
			(hash-ref (hash-ref jdata 'properties) 'place)
			(hash-ref (hash-ref jdata 'properties) 'time)
			(hash-ref (hash-ref jdata 'properties) 'url)
			(hash-ref (hash-ref jdata 'properties) 'felt)
			(hash-ref (hash-ref jdata 'properties) 'cdi)
			(hash-ref (hash-ref jdata 'properties) 'mmi)
			(hash-ref (hash-ref jdata 'properties) 'alert)
			(hash-ref (hash-ref jdata 'properties) 'status)
			(hash-ref (hash-ref jdata 'properties) 'sig)
			(hash-ref jdata 'id)
			(hash-ref (hash-ref jdata 'properties) 'dmin)
			(hash-ref (hash-ref jdata 'properties) 'rms)
			(hash-ref (hash-ref jdata 'properties) 'gap)))

(define (json->coordinate jdata)
	(make-coordinate (first jdata)
			(second jdata)
			(third jdata)))

(define (json->bounding-box jdata)
	(make-bounding-box (first jdata)
			(second jdata)
			(third jdata)
			(fourth jdata)
			(fifth jdata)
			(sixth jdata)))


; Handle connections
(define CONNECTION true)
(define (disconnect-earthquake-service)
	(set! CONNECTION false))
(define (connect-earthquake-service)
	(set! CONNECTION true))

; Build Client Store
(define CLIENT_STORE (read-json (open-input-file "cache.json")))

(define (boolean->string a-boolean)
	(if a-boolean
		"true"
		"false"))
(define (string->boolean a-string)
	(string=? a-string "true"))
(define (key-value pair)
	(string-append (symbol->string (car pair)) "=" (cdr pair)))
(define (convert-post-args data)
	(string->bytes/utf-8 (alist->form-urlencoded data)))
(define (convert-get-args url data)
	(string-append url "?" (string-join (map key-value data) "&")))
(define (hash-request url data)
	(string-append url "%{" (string-join (map key-value data) "}%{") "}"))
(define (post->json url full-data index-data)
	(if CONNECTION
		(port->string (post-pure-port (string->url url) (convert-post-args full-data)))
		(hash-ref CLIENT_STORE (hash-request url index-data) "")))
(define (get->json url full-data index-data)
	(if CONNECTION
		(port->string (get-pure-port (string->url (convert-get-args url full-data))))
		(hash-ref CLIENT_STORE (hash-request url index-data) "")))

; Define the services, and their helpers
(define (get-earthquakes threshold time)
	(json->report (get-earthquakes/json threshold time)))

(define (get-earthquakes/json threshold time)
	(string->jsexpr (get-earthquakes/string threshold time)))

(define (get-earthquakes/string threshold time)
	(get->json (string-append "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/" threshold "_" time ".geojson") 
	 	(list) 
	 	(list (cons 'threshold threshold) (cons 'time time))))

