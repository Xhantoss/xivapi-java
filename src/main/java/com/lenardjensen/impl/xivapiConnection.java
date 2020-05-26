package com.lenardjensen.impl;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This class will contain the boilerplate code needed to run the various requests
 */
public class xivapiConnection {

	/**
	 * Check key validity by trying a request with the provded key and checking the http response
	 * @param key The API key provded
	 * @throws IOException If there was an error with the connection itself
	 * @return True, if a successful request could be made
	 */
	public boolean keyIsValid (String key) throws IOException {
		URL url = new URL("https://xivapi.com/item/1675?private_key=" + key);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.connect();

		int code = connection.getResponseCode();

		return code == 200;
	}

	/**
	 * Performs a GET request against the xivapi
	 * @param params Parameters following the "https://xivapi.com/"
	 * @return JSON object containing the relevant information
	 * @throws IOException If there was an error with the connection itself
	 */
	private JSONObject getRequestOutput (String params) throws IOException {
		URL url = new URL("https://xivapi.com/"+params);
		JSONTokener tokener = new JSONTokener(url.openStream());
		return new JSONObject(tokener);
	}

	/**
	 * Performs a GET request against the xivapi with a provided key
	 * @param params Parameters following the "https://xivapi.com/"
	 * @param key An API key
	 * @return JSON object containing the relevant information
	 * @throws IOException If there was an error with the connection itself
	 */
	public JSONObject getRequestOutput (String params, String key) throws IOException {
		if (key.isEmpty()) {
			// If no key was provided, ignore it
			return getRequestOutput(params);
		}
		// With a key provided one can simply append it to the parameters provided
		if(params.contains("?")) {
			return getRequestOutput(params+"&private_key="+key);
		}
		return getRequestOutput(params+"?private_key="+key);
	}
}

// How to JSON:
//	JSONTokener tokener = new JSONTokener(url.openStream());
//	JSONObject root = new JSONObject(tokener);