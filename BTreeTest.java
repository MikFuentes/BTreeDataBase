import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Test;

public class BTreeTest {

	@Test
	// Tests BTree methods insert and findKey
	public void test() throws IOException {
		String name = "TreeTest.bt";
		BTree bt = new BTree(name);
	
		bt.insert(0, bt.findRoot(), 0); 
		long result = bt.findKey(0, bt.findRoot()); 
		//findKey() will return -1 if it's not found
		if (result !=-1) {
			result = 1;
		}
		assertEquals(1, result);
		
		result = bt.findKey(1, bt.findRoot()); 
		//findKey() will return -1
		if (result !=-1) {
			result = 1;
		}
		assertEquals(-1, result);
	}
}
