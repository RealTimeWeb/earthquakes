package realtimeweb.earthquakeservice.util;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * Contains several functions that seriously should have been added to Java
 * core. Or maybe I should have used Guava.
 * 
 * @author acbart
 * 
 */
public class Util {

	
	
	public static String get(String url, HashMap<String, String> query_args) throws IllegalStateException, IOException, URISyntaxException {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpGet service= new HttpGet(url);
		
		// Build query string
		URIBuilder ub = new URIBuilder(service.getURI());
		for (Entry<String, String> qa : query_args.entrySet()) {
			ub.addParameter(qa.getKey(), qa.getValue());
		}
		URI uri = ub.build();
		((HttpRequestBase) service).setURI(uri);
		
		// Execute get request
		HttpResponse response = httpclient.execute(service);
		return EntityUtils.toString(response.getEntity());
	}
	
	public static String post(String url, Map<String, String> body_args) throws IllegalStateException, IOException, URISyntaxException {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost service= new HttpPost(url);
		
		// Build body
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		for (Entry<String, String> ba : body_args.entrySet()) {
			nameValuePairs.add(new BasicNameValuePair(ba.getKey(), ba.getValue()));
		}
		service.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		
		// Execute post request
		HttpResponse response = httpclient.execute(service);
		return EntityUtils.toString(response.getEntity());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String hashRequest(String url, Map<String, String> query_arguments) {
		String queryStrings = "";
		if (query_arguments != null) {
			queryStrings = (new TreeMap(query_arguments)).toString();
		}
		return url + "%" + queryStrings;
	}
}
