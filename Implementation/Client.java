
public class Client
{
	private boolean loggedIn = false;
	private User user;
	private static Server server = null; //Static means this variable is shared across all "Client" instances
	
	public Client() {}
	
	public void LogIn(User user)
	{
		this.user = user;
		loggedIn = true;
	}
	public void LogOut()
	{
		user = null;
		loggedIn = false;
	}
	
	public Server AccessServer() //Lazy singleton to ensure every "Client" instance only has 1 "server" instance
	{
		if (server == null) {server = new Server();}
		return server;
	}
	
	public void DisconnectFromServer()
	{
		//Disconnect this client from the server
	}
	public void CloseServer()
	{
		//If the client list is 0, close the server
	}
	
	public void ReaDFile() {}
	public File CreateFile() {}
	public void WriteToFile(File file) {}
	public void AddFileToServer(File file) {}
	public void CopyFile(File file) {}
	public void DeleteFileFromServer(File file) {}
	public File[] GetAllHiddenFiles() {}
	public File[] GetAllUnhiddenFiles() {}
}
