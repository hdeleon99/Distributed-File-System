package mainpackage;
import java.io.Serializable;

//
// the idea of this class is to have all of the information that we would need to be sending back and forth
// put into a single object for simplicity
//
public class Request implements Serializable{
	private File file;
	private Log log;
	private User user;
	private String fileName, fileType;
	private int requestType;
	private boolean requestStatus, loggedIn, sendErrMsg, hidden;
	private Node node;
	public Request() {
		//file = new File("","");
		log = new Log();
		requestStatus = loggedIn = sendErrMsg = hidden = false;
		
		fileName = fileType = "";
	}
	//
	// setters
	//
	public void setNode(Node node) {this.node = node;}
	public void setFile(File file) 
		{
		this.file = file;
		this.fileName = file.GetName();
		this.fileType = file.GetType();
		}
	public void setLog(Log log) { this.log = log; }// Log class should have a print method
	public void setRequestType(int requestType) { this.requestType = requestType; }
	public void setRequestStatus(boolean requestStatus) { this.requestStatus = requestStatus; }
	public void setLoggedIn(boolean loggedIn) { this.loggedIn = loggedIn; }
	public void setUser(User user) { this.user = user; }
	public void setFileName(String fileName) { this.fileName = fileName; }
	public void setFileType(String fileType) { this.fileType = fileType; }
	public void setErrStatus(boolean sendErrMsg) { this.sendErrMsg = sendErrMsg; }
	public void setHidden(boolean hidden) { this.hidden = hidden; }
	//
	// getters
	//
	public Node getNode() {return node;}
	public boolean getErrorStatus() { return sendErrMsg; }
	public File getFile() { return file; }
	public Log getLog() { return log; }
	public int getRequestType() { return requestType; }
	public boolean getRequestStatus() { return requestStatus; }
	public boolean isLoggedIn() { return loggedIn; }
	public boolean getHidden() { return hidden; }
	public User getUser() { return user; }
	public String getFileName() { return fileName; }
	public String getFileType() { return fileType; }
	public String printErrMsg() { return "Error processing request..."; } 
}