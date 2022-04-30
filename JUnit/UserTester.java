package Testing;

import Main.User;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;

public class UserTester
{

	@Test
	public void testConstructor()
	{
		User user = new User("Miles", "Vizinau");
		String ID = user.GetUserID();
		String password = user.GetPassword();
		assertTrue(ID.equals("Miles") && password.equals("Vizinau"));
	}
	
	@Test
	public void testSupervisorStatus()
	{
		User user1 = new User("Smanny", "red");
		User user2 = new User("John", "blue");
		assertTrue(user1.GetSupervisor() && !user2.GetSupervisor());
	}
	
	@Test
	public void testUserEqualsUser()
	{
		User user1 = new User("Marvel", "DC");
		User user2 = user1;
		assertTrue(user1.UserEqualsUser(user1, user2));
	}

}
