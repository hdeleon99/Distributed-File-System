//
// the idea of this class is to have all of the information that we would need to be sending back and forth
// put into a single object for simplicity
//
public class Request{
	File file;
	Log log;
	User user;
	//
	// if type is 0: sending a file
	// if type is 1: requesting a file
	// if type is 2: requesting to view log
	// if type is 3: request to remove file
	// if type is 4: request to remove a client from client list
	// if type is 5: login request
	// if type is 6: logout request
	// if type is 7: creating a new user
	//
	// we can have the server handle the request based on the request type
	//

	
	private int requestType;
	//
	// false means failed request, true means successful request
	//
	// ONLY SERVER CHANGING REQUESTSTATUS
	private boolean requestStatus, loggedIn, loggedOut;
	//
	// this is a default request
	// 
	// Note: blank file and blank log because we don't know what the client wants to do yet
	//
	// Note: we don't know if the user is a supervisor until logged in
	//
	// Note: request status is assumed to be failure until server sets it to successful
	//
	public Request() {
		//file = new File();
		log = new Log();
		requestStatus = loggedIn = loggedOut = false;
		user = new User();
	}
	//
	// setters
	//
	public void setFile(File file) { this.file = file; }
	public void setLog(Log log) { this.log = log; }// Log class should have a print method
	public void setRequestType(int requestType) { this.requestType = requestType; }
	public void setRequestStatus(boolean requestStatus) { this.requestStatus = requestStatus; }
	public void setLoggedIn(boolean loggedIn) { this.loggedIn = loggedIn; }
	public void setLoggedOut(boolean loggedOut) { this.loggedOut = loggedOut; }
	public void setUser(User user) { this.user = user; }
	//
	// getters
	//
	public File getFile() { return file; }
	public Log getLog() { return log; }
	public int getRequestType() { return requestType; }
	public boolean getRequestStatus() { return requestStatus; }
	public boolean isLoggedIn() { return loggedIn; }
	public boolean isLoggedOut() { return loggedOut; }
	public User getUser() { return user; }
}