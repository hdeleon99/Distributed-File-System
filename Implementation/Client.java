
import java.io.*;
import java.net.*;
import java.util.*;

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
		
		try(Socket socket = new Socket("localhost", 1234)) {
			dataInputStream = new DataInputStream(socket.getInputStream());
			dataOutputStream = new DataOutputStream(socket.getOutputStream());
			
			// user opens client and wants to send a file
			while(true) {
				
				System.out.print("Would you like to: upload, receive, or exit? ");
				String userTask = scanner.nextLine();
				dataOutputStream.writeUTF(userTask);
				// call taskManager here
				if(userTask.equalsIgnoreCase("exit")) {
					break;
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private static void taskManager(String task) {
		// if task == upload
		// code to upload (send) a file to the server and DFS system
		// file will need to be sent to server
		// file will need to be added to storage
		// if task == receive
		// code to ask server for a file
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
