import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ BTreeTest.class, DriverTest.class, ValuesTest.class })
public class AllTests {

}
