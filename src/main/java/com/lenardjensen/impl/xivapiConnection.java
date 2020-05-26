package com.lenardjensen.impl;

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
}

// How to JSON:
//	JSONTokener tokener = new JSONTokener(url.openStream());
//	JSONObject root = new JSONObject(tokener);