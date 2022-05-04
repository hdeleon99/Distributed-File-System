package Main;
import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.FlowLayout;
import javax.swing.*;

public class Client
{
		
	public Client(){
		
	}
	

	public static void main(String[] args) throws UnknownHostException, IOException {
		try 
		{
			
			User user;
			Scanner scanner = new Scanner(System.in);
			Request request = new Request();
			Socket socket = new Socket();
			Node node;
			
			//String nodeName = "";
			// ask user for name of their computer, pass it into initialized node object
			String nodeName = JOptionPane.showInputDialog("Enter the name of your system: ");
			
			String username = JOptionPane.showInputDialog("Enter a username: ");
			String password = JOptionPane.showInputDialog("Enter a password: ");		
			
			
			JOptionPane.showMessageDialog(null, "Logged in as " + username);
			
			user = new User(username, password);
			
			node = new Node(nodeName, user);
			
			request.setNode(node);
			// pass new user in to request object, and mark them as logged in
			request.getNode().SetCurrentUser(user);
			request.setLoggedIn(true);
			
			while(request.isLoggedIn()) {
				request.setErrStatus(false);
				request.setRequestStatus(false);
				socket = new Socket("localhost", 1245);
				
				request = getRequest(request, node, scanner, user);
				
				OutputStream outputStream = socket.getOutputStream();                  
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
				objectOutputStream.writeObject(request);

				InputStream inputStream = socket.getInputStream();                 
				ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
				request = (Request) objectInputStream.readObject();
				System.out.println("Request status: " + request.getRequestStatus());
				readRequestFromServer(request);
				if (request.getErrorStatus()) {
					JOptionPane.showMessageDialog(null, request.printErrMsg());
				}
				inputStream.close();
			}
			// set node.currentUser(null);
			scanner.close();
			socket.close();
			
			
		} catch (Exception e) { e.printStackTrace();} 

}
	private static void readRequestFromServer(Request request) {
		if(request.getRequestStatus()) {
			if(request.getRequestType() == 2) {
				JOptionPane.showMessageDialog(null, "The file" + request.getFileName() + " was found! " + "Request type: " + request.getRequestType() + ", Request status: " + request.getRequestStatus());
			}
			
		}
		else if(!request.getRequestStatus()) {
			
			if(request.getRequestType() == 2) {
				JOptionPane.showMessageDialog(null, "The file" + request.getFileName() + " was not found. " + "Request type: " + request.getRequestType() + ", Request status: " + request.getRequestStatus());
			}
			
		}
	}
	private static Request getRequest(Request request, Node node, Scanner scanner, User user) {
		
		
		if(user.GetSupervisor()) {
			
			// get the request type (integer)
			String[] superCommands = { "Logout",
									"Add file",
									"Search for file",
									"View the log",
									"Remove a file",
									"Clear the log",
									"Exit"};
			// -1 is a placeholder value for the do/while loop
			int requestType = -1;
			
			do {
				requestType = JOptionPane.showOptionDialog(null, "Select an option", 
						"Distributed File System", 
						JOptionPane.YES_NO_CANCEL_OPTION, 
						JOptionPane.QUESTION_MESSAGE, 
						null, 
						superCommands, 
						superCommands[superCommands.length-1]);
				
				switch (requestType) 
				{
					case 0: request.setRequestType(requestType); // logout
					case 1: request.setRequestType(requestType); // add file
					case 2: request.setRequestType(requestType); // get file
					case 3: request.setRequestType(requestType); // view log
					case 4: request.setRequestType(requestType); // remove file
					case 5: request.setRequestType(requestType); // clear log
					case 6: request.setRequestType(requestType); // exit client and server
				}
				request = requestHelper(request, node, scanner, user);
				
			} while(requestType == -1);
	

			// follow up question 
			
		}
		else if(!user.GetSupervisor()) {
			String[] stdCommands = 
					{"Logout",
					"Add file",
					"Search for file",
					};
			int requestType = -1;

			do {
				requestType = JOptionPane.showOptionDialog(null, "Select an option", 
						"Distributed File System", 
						JOptionPane.YES_NO_CANCEL_OPTION, 
						JOptionPane.QUESTION_MESSAGE, 
						null, 
						stdCommands, 
						stdCommands[stdCommands.length-1]);

				switch (requestType) 
				{
					case 0: request.setRequestType(requestType); // logout
					case 1: request.setRequestType(requestType); // add file
					case 2: request.setRequestType(requestType); // get file
					
				}
				
				request = requestHelper(request, node, scanner, user);

			} while(requestType == -1);

				
			
		}
		return request;
	}
	

	private static Request requestHelper(Request request, Node node, Scanner scanner, User user) {
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
			String name = JOptionPane.showInputDialog("Enter the name of the file to add: ");
			String type = JOptionPane.showInputDialog("Enter the file type: " );
			
			file = new File(name, type);

			boolean run = true;
			while(run) {
				if(request.getNode().GetCurrentUser().GetSupervisor()) {

					hiddenStatus = JOptionPane.showInputDialog("Will this file be hidden or unhidden? ");
					if(hiddenStatus.equalsIgnoreCase("hidden")) {
						request.setHidden(true);
						run = false;
					}
					else if(hiddenStatus.equalsIgnoreCase("unhidden")) {
						request.setHidden(false);
						run = false;
					}
					
					else {
						hiddenStatus = JOptionPane.showInputDialog("Invalid input. Will this file be hidden or unhidden? ");
					}
				}
				run = false;
			}
			

			request.setFile(file);
		}
		
		else if (request.getRequestType() == 2) {
			File requestedFile; 
			String name = JOptionPane.showInputDialog("Enter the name of the file to request: ");;
			
			boolean run = true;
			while(run) {
				if(request.getNode().GetCurrentUser().GetSupervisor()) {
					
					hiddenStatus = JOptionPane.showInputDialog("Is this file hidden or unhidden? ");
					if(hiddenStatus.equalsIgnoreCase("hidden")) {
						request.setHidden(true);
						run = false;
					}
					else if(hiddenStatus.equalsIgnoreCase("unhidden")) {
						request.setHidden(false);
						run = false;
					}
					
					else {
						hiddenStatus = JOptionPane.showInputDialog("Invalid input. Will this file be hidden or unhidden? ");
					}
				}
				run = false;
			}
			
			String type = JOptionPane.showInputDialog("Enter the file type: " );

			requestedFile = new File(name, type);
			request.setFile(requestedFile);
		}
		
		else if (request.getRequestType() == 3) {
			// CALL
			JOptionPane.showMessageDialog(null, request.getLog().printLog());
		}
		
		else if (request.getRequestType() == 4) {
			File removeFile; 
			String name = JOptionPane.showInputDialog("Enter the name of the file to remove: ");;
			
			boolean run = true;
			while(run) {
				if(request.getNode().GetCurrentUser().GetSupervisor()) {
					
					hiddenStatus = JOptionPane.showInputDialog("Is this file hidden or unhidden? ");
					if(hiddenStatus.equalsIgnoreCase("hidden")) {
						request.setHidden(true);
						run = false;
					}
					else if(hiddenStatus.equalsIgnoreCase("unhidden")) {
						request.setHidden(false);
						run = false;
					}
					
					else {
						hiddenStatus = JOptionPane.showInputDialog("Invalid input. Will this file be hidden or unhidden? ");
					}
				}
				run = false;
			}
			
			String type = JOptionPane.showInputDialog("Enter the file type: " );

			removeFile = new File(name, type);
			request.setFile(removeFile);
			
		}

		return request;
	}

}