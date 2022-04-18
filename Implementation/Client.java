import java.io.*;
import java.net.*;
import java.util.*;


// sources for help: 
// https://heptadecane.medium.com/file-transfer-via-java-sockets-e8d4f30703a5
public class Client
{
	private boolean loggedIn = false;
	private User user;
	private static Server server = null; //Static means this variable is shared across all "Client" instances
	
//	public Client() {}
	
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
	
	public boolean getLoggedIn() {
		return loggedIn;
	}
	
	public Server AccessServer() //Lazy singleton to ensure every "Client" instance only has 1 "server" instance
	{
		if (server == null) {server = new Server();}
		return server;
	}
	public void CloseServer()
	{
		//Remove this client from the server's "client list"
		//If the client list is 0 after above operation, close server
	}
	
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		
		DataOutputStream dataOutputStream = null;
		DataInputStream dataInputStream = null;
		Scanner scanner = new Scanner(System.in);
		User user;
		try(Socket socket = new Socket("localhost", 1234)) {
			// prompt user for username first
			System.out.println("Enter a username: ");
			String username = scanner.nextLine();
			
			// then prompt for password
			System.out.println("Enter a password");
			String password = scanner.nextLine();
			
			// new user created
			user = new User(username, password);
			// mark user as logged in
			user.setLoginStatus(true);
			
			dataInputStream = new DataInputStream(socket.getInputStream());
			dataOutputStream = new DataOutputStream(socket.getOutputStream());
			
			// user opens client and wants to send a file
			// user opens client and wants to send a file
			// if user is supervisor
			// 		while(){ show supervisor options which include: upload, receive, 
			// 		delete file from system, remove user from server, view event history)
			// else if user is not supervisor
			//		while(){ show user options including: upload, receive, or exit
			while(user.getLoginStatus()) {
				
				System.out.print("Would you like to: upload, receive, or exit? ");
				String userTask = scanner.nextLine();
				//dataOutputStream.writeUTF(userTask);
				// call taskManager here
				taskManager(userTask, user);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private static void taskManager(String task, User user) {
		// if task == upload
		// code to upload (send) a file to the server and DFS system
		// file will need to be sent to server
		// file will need to be added to storage
		// if task == receive
		// code to ask server for a file
		if (task.equalsIgnoreCase("exit")) {
			user.setLoginStatus(false);
		}
	}
	// Function to send the file from the client, to the server
	private static void sendFile(String path) throws Exception{
		int bytes = 0;
		File file;
		// will need to get file name
		
		FileInputStream fileInputStream = new FileInputStream(file);
		
		
		
	}
	public void ReadFile() {}
	public File CreateFile() {}
	public void WriteToFile(File file) {}
	public void AddFileToServer(File file) {}
	public void CopyFile(File file) {}
	public void DeleteFileFromServer(File file) {}
	public File[] GetAllHiddenFiles() {}
	public File[] GetAllUnhiddenFiles() {}
}
