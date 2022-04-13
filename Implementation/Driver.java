import java.util.*;

public class Driver
{
	public static void main(String[] args)
	{
		//Create all variables needed to start, like in a facade
		User user = new User("Marvel2022", "Avengers");
		Node node = new Node("Miles", user, 2000);
		Client client = user.GetClient();
	}
}
