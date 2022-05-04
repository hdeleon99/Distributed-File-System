package Testing;
import Main.Node;
import Main.User;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;
public class Node_Tester
{

	@Test
	public void testConstructor()
	{
		User user = new User("Miles", "Vizinau");
		Node node = new Node("God", user);
		String name = node.GetName();
		User user2 = node.GetCurrentUser();
		
		assertTrue(name.equals("God") && user.UserEqualsUser(user, user2));
	}
	
	@Test
	public void testSwitchingUsers()
	{
		User user1 = new User("Tony", "Stark");
		User user2 = new User("Steve", "Rogers");
		Node node = new Node("Marvel", user1);
		
		node.SetCurrentUser(user2);
		User tempUser = node.GetCurrentUser();
		assertTrue(tempUser.UserEqualsUser(tempUser, user2));
	}

}