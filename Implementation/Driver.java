package Main;
import java.util.*;

public class Driver
{
	public static void main(String[] args)
	{
		//Create all variables needed to start, like in a facade
		User user1 = new User("Marvel2022", "Avengers");
		User user2 = new User("Smanny", "Cake");
		User user3 = new User("Isaiah", "Client");
		User user4 = new User("Moises", "Server");
		User user5 = new User("Miles", "Extra");
		User user6 = new User("Smith", "Professor");
		User user7 = new User("CSU - East Bay", "College");
		User user8 = new User("Sasuke", "Naruto");
		Node node = new Node("Software", user1);
		File file1 = new File("SuperHero", "txt");
		
		//Node tests
		//node.SetCurrentUser(user2);
		System.out.println(node.GetName());
		System.out.println('\n');
		
		System.out.println("Current User: " + node.GetCurrentUser().GetUserID() + '\n');
		
		//File tests
		node.GetUnhiddenStorage().AppendFileList(file1);
		System.out.println(node.GetUnhiddenStorage().GetNumFiles());
		System.out.println(node.GetUnhiddenStorage().GetFileList()[0].GetName());
		System.out.println(node.GetUnhiddenStorage().GetFileList()[0].GetType());
	}
}
