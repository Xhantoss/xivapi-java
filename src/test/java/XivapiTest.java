import de.lenardjensen.Xivapi;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
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

	// Most tests here rely on querying data that may change in the future.
	// If the following tests break, check if maybe some infos just got outdated
	// E.g. player characters getting deleted, or guilds being disbanded

	@Test
	public void testGetFCByID() {
		// Lodestone ID of "The Drakes' Nest"
		// Using an FC that is unlikely to dissolve with an active Leader named "Drakanous Firestone"
		String ID = "9231112598714336349";
		try {
			// Test both the "simple" method of acquiring data and the method using data parameters
			JSONObject simpleFreeCompanyJSON = testedClass.getFCByID(ID);
			JSONObject freeCompanyInfo = simpleFreeCompanyJSON.getJSONObject("FreeCompany");
			JSONObject complexFreeCompanyJSON = testedClass.getFCByID(ID, true, true);
			JSONArray memberInfo = complexFreeCompanyJSON.getJSONArray("FreeCompanyMembers");
			boolean hasMemberX = false;
			for(int i = 0; i < memberInfo.length(); i++) {
				JSONObject member = memberInfo.getJSONObject(i);
				if(member.getString("Name").equals("Drakanous Firestone")){
					hasMemberX = true;
					break;
				}
			}
			// The test is complete if the FC leader was located in the correct FC
			assertTrue(hasMemberX);
			assertEquals("The Drakes' Nest", freeCompanyInfo.getString("Name"));
		} catch (IOException e) {
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
			// Create a list of multiple data parameters to also test if all params are correctly parsed with correct syntax
			List<Xivapi.dataParams> extraParams = new ArrayList<>();
			extraParams.add(Xivapi.dataParams.AC);
			extraParams.add(Xivapi.dataParams.MIMO);
			extraParams.add(Xivapi.dataParams.CJ);

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
