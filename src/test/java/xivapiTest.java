import com.lenardjensen.xivapi;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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
}
