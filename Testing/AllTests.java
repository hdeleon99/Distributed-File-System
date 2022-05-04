package Testing;

import org.junit.runner.RunWith;  
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)

@SuiteClasses({
	Log_Tester.class, Request_Tester.class, Node_Tester.class, User_Tester.class,
	File_Tester.class, Storage_Tester.class
})
public class AllTests {
}