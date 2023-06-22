import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Test;

public class ValuesTest {

	@Test
	// Tests Values methods writeToFile, readValues, getRecord, and updateFile
	public void test() throws IOException {
		String name = "ValuesTest.values";
		Values val = new Values(name);

		val.writeToFile("Hello".trim().getBytes("UTF8")); // writeToFile()
		assertEquals("Hello", val.readValues(0)); // readValues() should return "Hello"
		assertEquals(1, val.getRecord()); // getRecord() should be 1

		val.writeToFile("Hi".trim().getBytes("UTF8")); // writeToFile()
		assertEquals("Hi", val.readValues(1)); // readValues() should return "Hi"

		val.updateFile("Hihi".trim().getBytes("UTF8"), 0); // updateFile()
		assertEquals("Hihi", val.readValues(0)); // readValues() should return "Hihi"
		assertEquals(2, val.getRecord()); // getRecord() should still be 2
	}
}
