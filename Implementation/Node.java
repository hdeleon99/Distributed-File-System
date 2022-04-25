public class Node
{
	private String name; //Name of this node
	private Hidden_Storage hiddenStorage = new Hidden_Storage();
	private Unhidden_Storage unhiddenStorage = new Unhidden_Storage();
	private User currentUser; //User that the node is currently logged in as
	private static User[] userList = new User[7]; //List of users that are currently apart of the file system
	private static int numUsers = 0; //Number of users currently in "userList"
	
	public Node(String name, User currentUser)
	{
		this.name = name;
		this.currentUser = currentUser;
		AppendUserList(currentUser);
	}
	
	public String GetName() {return name;}
	
	public Hidden_Storage GetHiddenStorage() {return hiddenStorage;}
	public Unhidden_Storage GetUnhiddenStorage() {return unhiddenStorage;}
	
	public User GetCurrentUser() {return currentUser;}
	public void SetCurrentUser(User user)
	{
		//Makes sure the user doesn't have any client software open before changing the current user of this node
		if (!user.GetUsingClient())
		{
			currentUser = user;
			System.out.println("Successfully set this node's current user");
		}
		else
		{
			System.out.println("This user is currently using client software, must disconnect from server before changing users");
			return;
		}
		AppendUserList(user);
	}
	
	private boolean UserEqualsUser(User user1, User user2) //Defines users being equal to each other, in other words they're the same
	{
		String userID1 = user1.GetUserID();
		String password1 = user1.GetPassword();
		String userID2 = user2.GetUserID();
		String password2 = user2.GetPassword();
		
		if (userID1.equals(userID2) && password1.equals(password2)) {return true;}
		return false;
	}
	
	public User[] GetUserList()
	{
		return userList;
	}
	
	public void AppendUserList(User user)
	{
		if (UserListContains(user)) {return;} //If "userList" already contains the user
		if (numUsers == userList.length) //If "userList" is full, double it's size
		{
			User tmp[] = new User[2 * numUsers];
		    System.arraycopy(userList, 0, tmp, 0, numUsers); 
		    userList = tmp;
		    tmp = null;
		}
		userList[numUsers] = user;
		++numUsers;
	}
	
	public void DeleteFromUserList(User user)
	{
		int location = -1; //Location of possible deletion
		for (int i = 0; i < numUsers; i++)
		{
			if (UserEqualsUser(user, userList[i])) {location = i; break;}
		}
		if (location == -1) {return;} //If the user isn't apart of the user list
		if (user.GetUsingClient()) //If "user" is currently using any client software
		{
			//Close this user's client software
			user.SetClient(null);
		}
		if (UserEqualsUser(currentUser, user)) {currentUser = null;} //If the user being deleted is the current user
		for (int i = location; i < numUsers - 1; i++) {userList[i] = userList[i + 1];} //Delete this user from list
		--numUsers;
	}
	
	private boolean UserListContains(User user)
	{
		for (int i = 0; i < numUsers; i++)
		{
			if (UserEqualsUser(user, userList[i])) {return true;}
		}
		return false;
	}
	
	public int GetNumUsers() {return numUsers;}
	
	
}
