import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class DriverTest {

	@Test
	// Tests Driver methods insert, select, and update
	public void test() throws IOException {
		Driver drv = new Driver("Data.bt", "Data.values");

		assertEquals("1052 inserted.", drv.insert(1052, "Hello"));
		assertEquals("1052 => Hello", drv.select(1052));
		assertEquals("ERROR: 1052 already exists.", drv.insert(1052, "Hi"));
		assertEquals("1052 updated.", drv.update(1052, "Hi"));
		assertEquals("1052 => Hi", drv.select(1052));

		assertEquals("ERROR: 100 does not exist.", drv.update(100, "Bye"));
		assertEquals("100 inserted.", drv.insert(100, "Bye"));

		assertEquals("9001 inserted.", drv.insert(9001, "Today is a nice day."));
		assertEquals("300 inserted.", drv.insert(300, ""));
		assertEquals("300 => ", drv.select(300));
		assertEquals("9001 => Today is a nice day.", drv.select(9001));

		assertEquals("ERROR: 9999 does not exist.", drv.select(9999));

		assertEquals("ERROR: key must be positive.", drv.insert(-1, "Hello"));
		assertEquals("ERROR: key must be positive.", drv.select(-1));
		assertEquals("ERROR: key must be positive.", drv.update(-1, "Hi"));

	}

}
