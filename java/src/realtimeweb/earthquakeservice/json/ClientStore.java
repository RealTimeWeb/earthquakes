package realtimeweb.earthquakeservice.json;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import com.google.gson.Gson;

/**
 * This method is intended for internal use only.
 * 
 * This is the class that is used to store internal results.
 * 
 * @author acbart
 * 
 */
class ClientStore {

	private HashMap<String, String> jsonData;

	/**
	 * Create a client store from a specified file.
	 * 
	 * @param source
	 */
	public ClientStore(String source) throws IOException {
		jsonData = new HashMap<String, String>();
		this.load(source);
	}

	/**
	 * Look up a given query in the client store. If it is not found, returns
	 * the empty string.
	 * 
	 * @param key
	 * @return
	 */
	public String getData(String key) {
		if (jsonData.containsKey(key)) {
			return jsonData.get(key);
		} else {
			return "";
		}
	}

	public void putData(String key, String value) {
		jsonData.put(key, value);
	}

	/**
	 * Create a client store based on the default store.
	 * @throws IOException 
	 */
	public ClientStore() throws IOException {
		this("../cache.json");
	}

	/**
	 * Load the client store from a file.
	 * 
	 * @param source
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void load(String source) throws IOException {
		InputStreamReader is = new InputStreamReader(getClass()
				.getResourceAsStream(source));
		BufferedReader clientData = new BufferedReader(is);
		jsonData = (new Gson()).fromJson(clientData, HashMap.class);
		clientData.close();
		System.out.println(jsonData.keySet());
	}

	public synchronized void save(String source, boolean append)
			throws IOException {
		FileWriter fw = new FileWriter(source, append);
		BufferedWriter clientFile = new BufferedWriter(fw);
		//JSONValue.writeJSONString(jsonData, clientFile);
		clientFile.close();
	}
}
