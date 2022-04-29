package testing;
import dfs_project.File;    
import dfs_project.User;
import dfs_project.Log;
import dfs_project.Request;
import dfs_project.Node;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

public class Request_Tester {

	@Test
	public void setFile_test() {
		File file = new File("new file","PDF");
		Request rqst = new Request();
		rqst.setFile(file);
		System.out.println("Testing setFile()");
		assertTrue(rqst.getFile().equals(file));
	}
	
	@Test
	public void setFileName_test() {
		String filename = "filename";
		Request rqst = new Request();
		rqst.setFileName(filename);
		System.out.println("Testing setFileName()");
		assertTrue(rqst.getFileName().equals(filename));
	}
	
	@Test
	public void setFileType_test() {
		String filetype = "filetype";
		Request rqst = new Request();
		rqst.setFileType(filetype);
		System.out.println("Testing setFileType()");
		assertTrue(rqst.getFileType().equals(filetype));
	}
	
	@Test
	public void setNode_test() {
		Node node = new Node("name", new User());
		Request rqst = new Request();
		rqst.setNode(node);
		System.out.println("Testing setNode()");
		assertTrue(rqst.getNode().equals(node));
	}
	
	@Test
	public void setLog_test() {
		Log log = new Log();
		Request rqst = new Request();
		rqst.setLog(log);
		System.out.println("Testing setLog()");
		assertTrue(rqst.getLog().equals(log));
	}
	
	@Test
	public void setUser_test() {
		User user = new User();
		Request rqst = new Request();
		rqst.setUser(user);
		System.out.println("Testing setUser()");
		assertTrue(rqst.getUser().equals(user));
	}
	
	@Test
	public void setRequestStatus_test() {
		Request rqst = new Request();
		rqst.setRequestStatus(true);
		System.out.println("Testing setRequestStatus()");
		assertTrue(rqst.getRequestStatus());
	}
	
	@Test
	public void setLoggedIn_test() {
		Request rqst = new Request();
		rqst.setLoggedIn(true);
		System.out.println("Testing setLoggedIn()");
		assertTrue(rqst.isLoggedIn());
	}
	
	@Test
	public void setHidden_test() {
		Request rqst = new Request();
		rqst.setHidden(true);
		System.out.println("Testing setHidden()");
		assertTrue(rqst.getHidden());
	}
	
	@Test
	public void setErrStatus_test() {
		Request rqst = new Request();
		rqst.setErrStatus(true);
		System.out.println("Testing setErrStatus()");
		assertTrue(rqst.getErrStatus());
	}
	
}
