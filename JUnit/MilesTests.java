package Testing;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)

@SuiteClasses({NodeTester.class, UserTester.class, StorageTester.class})

public class MilesTests {

}
