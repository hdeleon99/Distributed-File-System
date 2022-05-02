package testing;
import dfs_project.Log; 
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

public class Log_Tester {
	
	@Test
	public void AppendLog_test() {
		Log log = new Log();
		log.AppendLog("new log", new Date());
		System.out.println("Testing AppendLog() with parameters: ('new log', new Date()):\n" + log.printLog());
	}
	
	@Test
	public void ClearLog_test() {
		Log log = new Log();
		log.AppendLog("log1", new Date());
		log.AppendLog("log2", new Date());
		log.AppendLog("log3", new Date());
		System.out.println("Testing ClearLog():\nLog before ClearLog():\n" + log.printLog());
		log.ClearLog();
		System.out.println("Log after ClearLog():\n" + log.printLog());
	}
}
