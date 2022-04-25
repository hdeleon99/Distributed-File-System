import java.io.Serializable;

public class User implements Serializable
{
	private String userID;
	private String password;
	private boolean usingClient = false;
	private boolean supervisor = false;
	private Client client = null;
	
	public User(String userID, String password)
	{
		this.userID = userID;
		this.password = password;
		if (userID.charAt(0) == 'S') {supervisor = true;} //If 1st character of "userID" == 'S' set "supervisor" = true
	}
	
	public String GetUserID() {return userID;}
	public String GetPassword() {return password;}
	
	public boolean GetUsingClient() {return usingClient;}
	public void SetUsingClient(boolean usingClient) {this.usingClient = usingClient;}
	
	public boolean GetSupervisor() {return supervisor;}
	
	public void SetClient(Client client)
	{
		this.client = client;
		SetUsingClient(true);
	}
	
	
}
