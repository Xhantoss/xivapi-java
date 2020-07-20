import com.lenardjensen.Xivapi;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class XivapiTest {

	private Xivapi testedClass;

	@Before
	public void setUp() {
		testedClass = new Xivapi();
	}

	@Test
	public void testKeyInteractions() {
		try {
			// Only test invalidity here
			// it wouldn't be possible to push a valid key to the repo
			// due to possible API-abuse
			String myKey = "invalidKey";
			// Check if invalid keys are properly detected
			testedClass.setApiKey(myKey);
			assertEquals(testedClass.getApiKey(), myKey);
			assertFalse(testedClass.apiKeyIsValid());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testConstructor() {
		try {
			String myKey = "InvalidKey";
			// Check if invalid keys are properly detected
			testedClass = new Xivapi(myKey);
			assertEquals(testedClass.getApiKey(), myKey);
			assertFalse(testedClass.apiKeyIsValid());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetCharByID() {
		// Lodestone ID of 'Adelbern Naxx'
		int ID = 27075395;
		try {
			JSONObject charJSON = testedClass.getCharByID(ID);
			JSONObject charInfo = charJSON.getJSONObject("Character");
			String name = charInfo.get("Name").toString();
			assertEquals(name, "Adelbern Naxx");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Test
	public void testGetCharByIDWithParams() {
		// Lodestone ID of 'Adelbern Naxx'
		int ID = 27075395;
		try {
			List<Xivapi.dataParams> extraParams = Collections.singletonList(Xivapi.dataParams.MIMO);
			JSONObject charJSON = testedClass.getCharByID(ID, true, extraParams);
			JSONObject charInfo = charJSON.getJSONObject("Character");
			JSONArray minions = charJSON.getJSONArray("Minions");
			String name = charInfo.get("Name").toString();
			assertEquals(name, "Adelbern Naxx");

			// Look for a specific minion
			boolean hasRabbit = false;
			for(int i = 0; i < minions.length(); i++) {
				JSONObject minion = (JSONObject) minions.get(i);
				if(minion.get("Name").toString().equals("Dwarf Rabbit")){
					hasRabbit = true;
					break;
				}
			}
			assertTrue(hasRabbit);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
