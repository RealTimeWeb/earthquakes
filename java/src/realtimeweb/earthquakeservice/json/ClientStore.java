package realtimeweb.earthquakeservice.json;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
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
	public ClientStore(InputStream  source) throws IOException {
		jsonData = new HashMap<String, String>();
		try {
			this.load(source);
		} catch (IOException e) {
			throw new IOException("Couldn't find the local data stream "+source, e);
		}
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
		jsonData = new HashMap<String, String>();
		try {
			this.load(getClass().getClassLoader().getResourceAsStream("cache.json"));
		} catch (IOException e) {
			throw new IOException("Couldn't find the built-in data stream.", e);
		}
	}

	/**
	 * Load the client store from a file.
	 * 
	 * @param source
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void load(InputStream source) throws IOException {
		if (source == null) {
			throw new IOException("The given InputStream was null; check to make sure that the file exists.");
		}
		InputStreamReader is = new InputStreamReader(source);
		BufferedReader clientData = new BufferedReader(is);
		jsonData = (new Gson()).fromJson(clientData, HashMap.class);
		clientData.close();
	}

	public synchronized void save(String source, boolean append)
			throws IOException {
		FileWriter fw = new FileWriter(source, append);
		BufferedWriter clientFile = new BufferedWriter(fw);
		//JSONValue.writeJSONString(jsonData, clientFile);
		clientFile.close();
	}
}
