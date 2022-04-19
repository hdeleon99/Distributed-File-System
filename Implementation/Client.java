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
	
//	public void LogIn(User user)
//	{
//		this.user = user;
//		loggedIn = true;
//	}
//	public void LogOut()
//	{
//		user = null;
//		loggedIn = false;
//	}
//	
//	public boolean getLoggedIn() {
//		return loggedIn;
//	}
//	
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
		Request request = new Request();
		int requestType;
		try(Socket socket = new Socket("localhost", 1234)) {
			// LOGIN
			// prompt user for username first
			System.out.println("Enter a username: ");
			String username = scanner.nextLine();
			
			// then prompt for password
			System.out.println("Enter a password");
			String password = scanner.nextLine();
			
			//
			// CREATE NEW USER WITH USERNAME AND PASSWORD
			user = new User(username, password);
			// pass new user in to request object, and mark them as logged in
			request.setUser(user);
			request.setLoggedIn(true);
			
			dataInputStream = new DataInputStream(socket.getInputStream());
			dataOutputStream = new DataOutputStream(socket.getOutputStream());
			
			// check if user is supervisor, present them with their options
			if(user.GetSupervisor()) {
				// while the user associated with this request is marked as logged in
				while(request.isLoggedIn()) {
					System.out.print("Would you like to: upload a file(1), request"
							+ " a file (2), view the log (3), remove a file (4), "
							+ "remove a client from the system (5), or exit (0)?");
					// get the request type (integer)
					requestType = scanner.nextInt();
					request.setRequestType(requestType);
				}
			}
			// if user is not supervisor, show them their options
			else if(!user.GetSupervisor()) {
				while(request.isLoggedIn()) {
					
					System.out.print("Would you like to: upload a file(1), request"
							+ " a file (2), or exit (0)?");
					requestType = scanner.nextInt();
					request.setRequestType(requestType);
					//dataOutputStream.writeUTF(userTask);
					// call taskManager here
					taskManager(request);
					
					
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	// performs the actions of a request
	private static void taskManager(Request request) {
		Scanner scan = new Scanner(System.in);
		if (request.getRequestType() == 0) {
			request.setLoggedIn(false);
		}
		else if (request.getRequestType() == 1) {
			File file;
			String name;
			String path;
			
			System.out.println("Enter the name of the file: ");
			name = scan.nextLine();
			System.out.println("Enter the path of the file: ");
			path = scan.nextLine();
			file = new File(name, path);
			request.setFile(file);
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
