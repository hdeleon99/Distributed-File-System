import javax.swing.*;

public class Node
{
	private String name; //Name of this node
	private Hidden_Storage hiddenStorage = new Hidden_Storage();
	private Unhidden_Storage unhiddenStorage = new Unhidden_Storage();
	private User currentUser; //User that the node is currently logged in as
	
	public Node(String name, User currentUser)
	{
		this.name = name;
		this.currentUser = currentUser;
	}
	
	public String GetName() {return name;}
	
	public Hidden_Storage GetHiddenStorage() {return hiddenStorage;}
	public Unhidden_Storage GetUnhiddenStorage() {return unhiddenStorage;}
	
	public User GetCurrentUser() {return currentUser;}
	public void SetCurrentUser(User user)
	{
		currentUser = user;
		JOptionPane.showMessageDialog(null, "Successfully set this node's current user");
	}
		
}
