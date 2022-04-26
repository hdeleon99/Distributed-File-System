import java.io.*;
import java.net.*;
import java.util.*;



public class Client
{
	
	
	
	private static Server server = null; //Static means this variable is shared across all "Client" instances
	

	public Server AccessServer() // Lazy singleton to ensure every "Client" instance only has 1 "server" instance
	{
		if (server == null) {server = new Server();}
		return server;
	}
	
	public void CloseServer()
	{
		//Remove this client from the server's "client list"
		//If the client list is 0 after above operation, close server
	}
	
	
	// ERROR MESSAGE NEEDS TO BE HANDLED: SENDERRORMESSAGE
	// NEED REQUESTYPE FOR CLEARING THE LOG (CASE 5)
	// 
	public static void main(String[] args) throws UnknownHostException, IOException {
		try 
		{
			User user = new User();
			Scanner scanner = new Scanner(System.in);
			Request request = new Request();
			Socket socket = new Socket();
			Node node;
			
			String nodeName = "";
			// ask user for name of their computer, pass it into initialized node object
			System.out.println("Enter the name of your system: ");
			nodeName = scanner.nextLine();
			// initialize node with nodeName, but no user
			node = new Node(nodeName);
			
			
			String username = "";
			String password = "";		
			
		
			System.out.println("Login: Enter a username: ");
			username = scanner.nextLine();
					
			// then prompt for password
			System.out.println("Enter a password");
			password = scanner.nextLine();
			
			if(node.checkUserExists(username)) {
				System.out.println("Account found in system, logging in as... " + username);
				for(int i = 0; i < node.GetNumUsers(); i++) {
					if(node.GetUserList()[i].GetUserID() == username && node.GetUserList()[i].GetPassword() == password) {
						user = node.GetUserList()[i];
					}
				}
			}
			else{
				System.out.println("Account not found in system, creating new account...");
				user.setUserID(username);
				user.setUserPass(password);
				// new user created, so add it to node list
				node.AppendUserList(user);
				
			}
			
			node.SetCurrentUser(user);
			
			
			// pass new user in to request object, and mark them as logged in
			request.setUser(user);
			request.setLoggedIn(true);
			
			while(request.isLoggedIn()) {
				request.setErrStatus(false);
				socket = new Socket("localhost", 1239);
				
				request = getRequest(request, node, scanner, user);
				
				OutputStream outputStream = socket.getOutputStream();                  
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
				objectOutputStream.writeObject(request);

				InputStream inputStream = socket.getInputStream();                 
				ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
				request = (Request) objectInputStream.readObject();
				System.out.println("Request status: " + request.getRequestStatus());
				//taskManager(request, node, scanner, user);
				// need to check for error message boolean right HERE !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				if (request.getErrorStatus()) {
					request.printErrMsg();
				}
				inputStream.close();
			}
			scanner.close();
			socket.close();
			
			
		} catch (Exception e) { e.printStackTrace();} 
		
		
			

}
	

	private static Request getRequest(Request request, Node node, Scanner scanner, User user) {
		
		int requestType;
		if(user.GetSupervisor()) {
			
			
			System.out.print("Would you like to: add file to server (1), request"
					+ " a file (2), view the log (3), remove a file (4), clear the log(5), create new user (8) "
					+ "switch user (9), or exit the program (0)?");
			// get the request type (integer)
			requestType = scanner.nextInt();
			//
				
			// follow up question 
			request.setRequestType(requestType);
				
			request = taskManager(request, node, scanner, user);
		}
		else if(!user.GetSupervisor()) {
			System.out.print("Would you like to: add file to server (1), request"
					+ " a file (2), create new user (8), switch user (9), or exit the program (0)?");
			requestType = scanner.nextInt();
				
				
			request.setRequestType(requestType);
				
			request = taskManager(request, node, scanner, user);
		}
		return request;
	}
	

	private static Request taskManager(Request request, Node node, Scanner scanner, User user) {
		String hiddenStatus;
		
		//	if request status is true, then this is a request from the server
		//		so handle request as being from server
		//      ex: we can print that file has been successfully uploaded to DFS
		//      ex: or, if requestType is 3 and requestStatus is true, then we can call the printLog function
		//	else if request status not true, then this is a request being sent from the client
		//  	so do the code below (prompt user for hidden/unhidden 
		

		if(request.getRequestType() == 0) {
			request.setLoggedIn(false);
			user = null;
		}

		else if (request.getRequestType() == 1 && !request.getRequestStatus()) {
			File file;
			String name;
			String type;
			System.out.println("Enter the name of the file: ");
			scanner.nextLine();
			name = scanner.nextLine();
			
			
			
			
			System.out.println("Enter the file type: ");
			scanner.nextLine();
			type = scanner.nextLine();
			
			file = new File(name, type);
			// hidden or unhidden?
			
			
			boolean run = true;
			while(run) {
				if(request.getUser().GetSupervisor()) {
					System.out.println("Would you like this file to be hidden or unhidden?");
					
					hiddenStatus = scanner.nextLine();
					if(hiddenStatus.equalsIgnoreCase("hidden")) {
						request.setHidden(true);
						run = false;
					}
					else if(hiddenStatus.equalsIgnoreCase("unhidden")) {
						request.setHidden(false);
						run = false;
					}
					
					else {
						System.out.println("Invalid input, try again");
						hiddenStatus = scanner.nextLine();
					}
				}
				run = false;
			}
			

			request.setFile(file);
		}
		
		else if (request.getRequestType() == 2) {
			File requestedFile;
			String name;

			System.out.println("Enter the name of the file to request: ");
			scanner.nextLine();
			name = scanner.nextLine();
			
			if(request.getUser().GetSupervisor()) {
				System.out.println("Is this file hidden or unhidden?");
				
				scanner.nextLine();
				hiddenStatus = scanner.nextLine();
				
				if(hiddenStatus.equalsIgnoreCase("hidden")) {
					request.setHidden(true);
				}
				else if(hiddenStatus.equalsIgnoreCase("unhidden")) {
					request.setHidden(false);
				}
			}
			
			System.out.println("Enter the file type: ");
			scanner.nextLine();
			String type = scanner.nextLine();

			requestedFile = new File(name, type);
			request.setFile(requestedFile);
		}
		
		else if (request.getRequestType() == 3 && request.getRequestStatus()) {
			// CALL
			request.getLog().printLog();
		}
		
		// create a new user and add it to the node list of users
		else if (request.getRequestType() == 8) {
			 // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			System.out.println("Enter a new username: ");
			scanner.nextLine();
			String username = scanner.nextLine();
			
			// then prompt for password
			System.out.println("Enter a new password");
			scanner.nextLine();
			String password = scanner.nextLine();
			user = new User(username, password);
			
			node.AppendUserList(user);
			System.out.println("New user created: " + username);
		}
		// log in with different user
		else if (request.getRequestType() == 9) {
			System.out.println("Enter username: ");
			scanner.nextLine();
			String username = scanner.nextLine();
			
			// then prompt for password
			System.out.println("Enter password");
			scanner.nextLine();
			String password = scanner.nextLine();
			
			boolean run = true;
			while(run) {
				if(node.checkUserAndPass(username, password)) {
					System.out.println("Account found! Now logged in as " + username);
					run = false;
					
					request.setUser(user);
				}
				else {
					System.out.println("Account not found... try again");
					System.out.println("Enter a username or enter (quit): ");
					username = scanner.nextLine();
					if(username.equalsIgnoreCase("quit")){
						run = false;
					}
					// then prompt for password
					System.out.println("Enter a password");
					password = scanner.nextLine();
				}
			}
			
			
		}

		return request;
	}
	
	
	
}
