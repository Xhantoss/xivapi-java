import com.lenardjensen.xivapi;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class xivapiTest {

	private xivapi testedClass;

	@Before
	public void setUp() {
		testedClass = new xivapi();
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
			testedClass = new xivapi(myKey);
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
}
