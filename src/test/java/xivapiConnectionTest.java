import com.lenardjensen.impl.xivapiConnection;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class xivapiConnectionTest {

	@Test
	public void testKeyIsValid() {
		xivapiConnection clasUnderTest = new xivapiConnection();
		try {
			assertTrue(clasUnderTest.keyIsValid("e8ccb10450ca485a95370bc8fae2812195a8d494f8c84ea081fb57ce698db370"));
			assertFalse(clasUnderTest.keyIsValid("thisKeyShouldNotWork"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
