import java.util.Arrays;

public class Node
{
	private String name; //Name of this node
	private float storageSize; //Amount of storage this node has
	Hidden_Storage hiddenStorage = new Hidden_Storage();
	Unhidden_Storage unhiddenStorage = new Unhidden_Storage();
	private User currentUser; //User that the node is currently logged in as
	private static User[] userList = new User[7]; //List of users that are currently apart of the file system
	
	public Node(String name, User currentUser, float storageSize)
	{
		this.name = name;
		this.storageSize = storageSize;
		this.currentUser = currentUser;
		if (!UserListContains(currentUser)) {AppendUserList(currentUser);}
	}
	
	public String GetName() {return name;}
	
	public float GetStorageSize() {return storageSize;}
	public void IncraseStorageSize(float amount) {storageSize += amount;}
	
	public User GetCurrentUser() {return currentUser;}
	public void SetCurrentUser(User currentUser)
	{
		//Makes sure the user doesn't have any client software open, then execute below code
		this.currentUser = currentUser;
	}
	
	private void AppendUserList(User user)
	{
		
	}
	private void DeleteFromUserList(User user)
	{
		
	}
	private boolean UserListContains(User user)
	{
		return true;
	}
	
}
