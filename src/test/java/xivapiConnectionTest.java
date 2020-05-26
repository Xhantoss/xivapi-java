import com.lenardjensen.impl.xivapiConnection;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertFalse;

public class xivapiConnectionTest {

	@Test
	public void testKeyIsValid() {
		xivapiConnection clasUnderTest = new xivapiConnection();
		try {
			// Test for invalidity as one can not just push a valid key to a repository
			// Valid keys can be tested locally
			assertFalse(clasUnderTest.keyIsValid("thisKeyShouldNotWork"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
