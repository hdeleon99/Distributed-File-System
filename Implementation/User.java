package mainpackage;
import java.io.Serializable;

public class User implements Serializable
{
	private String userID;
	private String password;
	private boolean supervisor = false;
	
	public User(String userID, String password)
	{
		this.userID = userID;
		this.password = password;
		if (userID.charAt(0) == 'S') {supervisor = true;} //If 1st character of "userID" == 'S' set "supervisor" = true
	}
	
	public String getID() {return userID;}
	public String GetPassword() {return password;}
	
	public boolean GetSupervisor() {return supervisor;}
	
	public boolean UserEqualsUser(User user1, User user2)
	{
		String ID1 = user1.getID();
		String password1 = user1.GetPassword();
		String ID2 = user2.getID();
		String password2 = user2.GetPassword();
		
		if (ID1.equals(ID2) && password1.equals(password2)) {return true;}
		return false;
	}
}