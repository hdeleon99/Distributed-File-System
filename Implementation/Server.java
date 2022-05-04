package dfs_project;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;


public class Server {
	private static Log log = new Log(); //Eager singleton to ensure only 1 log | "static" shares this log across the entire system
	private static List<Node> nodes = new ArrayList<Node>();
	private static String [] logActions = {"Exited system : userID ->", "Uploaded file :", "Downloaded file :",
											"Viewed log : userID ->", "Removed file ", "Signed up : Device Name ->"};
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		ServerSocket server = null;
		try {
			server = new ServerSocket(1230);
			server.setReuseAddress(true);
			boolean shutdown = false;
			while (!shutdown) {
				Socket client = server.accept();
				System.out.println("New client connected" + client.getInetAddress().getHostAddress());
				ClientHandler clientSock = new ClientHandler(client);
				new Thread(clientSock).start();
				shutdown = clientSock.getShutDown();
			}
		}
		catch (IOException e) { e.printStackTrace(); }
		finally {
			if (server != null) {
				try { server.close(); }
				catch (IOException e) { e.printStackTrace(); }
			}
		}
	}
	// ClientHandler class
	private static class ClientHandler implements Runnable {
		private final Socket clientSocket;
		private boolean shutdown = false;
		// Constructor
		public ClientHandler(Socket socket){ this.clientSocket = socket; }

		public void run(){	
				
			try {
				InputStream inputStream = clientSocket.getInputStream();
				ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
				Request rqst = (Request) objectInputStream.readObject();
				rqst = handleRequest(rqst);
				OutputStream outputStream = clientSocket.getOutputStream();
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
				objectOutputStream.writeObject(rqst);
			}
			catch (IOException | ClassNotFoundException e) { e.printStackTrace(); }
			finally {
				try { clientSocket.close(); }
				catch (IOException e) { e.printStackTrace(); }
			}
		}
			
		public Request handleRequest(Request rqst){
			boolean hidden = rqst.getHidden();
			boolean supervisor = rqst.getNode().GetCurrentUser().GetSupervisor();
			String logMsg = "";
			// making sure that the node exists in the node list by default
			if(!containsNode(rqst.getNode())) {
				nodes.add(rqst.getNode());
				logMsg += logActions[5] + rqst.getNode().GetName() + "\n";
			}
			int currentPos = NodePos(rqst.getNode());
			int nodePos_file = -1;
			// if downloading file or removing file -> get node position for file
			if(rqst.getRequestType() == 2 || rqst.getRequestType() == 4) {
				nodePos_file = containsFile(rqst.getFile(), hidden);
			}
			
			switch(rqst.getRequestType()) {
			case 0:// exit
				rqst.setLoggedIn(false);
				rqst.setRequestStatus(true);
				logMsg += rqst.getNode().GetCurrentUser().GetUserID();
				break;
			case 1:// upload file
				AppendFileList(rqst.getFile(), currentPos, hidden, supervisor);
				logMsg += rqst.getFileName();
				rqst.setRequestStatus(true);
				break;
			case 2:// request file
				if(nodePos_file != -1) {
					rqst.setFile(getFile(nodePos_file, hidden, rqst.getFileName(), rqst.getFileType()));
					logMsg += rqst.getFileName();
					rqst.setRequestStatus(true);
				}
				break;
			case 3:// view log
				if(supervisor) { rqst.setLog(log); }
				rqst.setRequestStatus(true);
				logMsg += rqst.getNode().GetCurrentUser().GetUserID();
				break;
			case 4:// remove file
				if(supervisor && nodePos_file != -1) {
					DeleteFromFileList(nodePos_file, hidden, rqst.getFileName(), rqst.getFileType());
					logMsg += rqst.getFileName();
					rqst.setRequestStatus(true);
				}
				break;
			case 5:// clearing log
				ClearLog();
				rqst.setRequestStatus(true);
				break;
			case 6:// shutting down server
				rqst.setLoggedIn(false);
				rqst.setRequestStatus(true);
				shutdown = true;
				break;
			}
			// if successful request and not clearing log and not shutting server down -> append log
			if(rqst.getRequestStatus() && rqst.getRequestType() != 5 && rqst.getRequestType() != 6) {
				AppendLog(logActions[rqst.getRequestType()] + logMsg);
			}
			// if failed request -> send error message
			else if(!rqst.getRequestStatus()) { rqst.setErrStatus(true); }
			return rqst;
		}

		public void AppendLog(String action) { log.AppendLog(action, new Date()); }
		public void ClearLog() { log.ClearLog(); }
		
		public void AppendFileList(File file, int pos, boolean hidden, boolean supervisor) {
			if(hidden && supervisor) {
				nodes.get(pos).GetHiddenStorage().AppendFileList(file);
			}
			else {
				nodes.get(pos).GetUnhiddenStorage().AppendFileList(file);
			}
		}
		
		public void DeleteFromFileList(int pos, boolean hidden, String fileName, String fileType) {
			if(hidden) {
				nodes.get(pos).GetHiddenStorage().DeleteFromFileList(fileName, fileType);
			}
			else {
				nodes.get(pos).GetUnhiddenStorage().DeleteFromFileList(fileName, fileType);
			}
		}
		
		public int NodePos(Node node) { return nodes.indexOf(node); }
		
		public boolean getShutDown() { return shutdown; }
		
		public int containsFile(File file, boolean hidden) {
			if(hidden) {
				for(int i = 0; i < nodes.size(); i++) {
					if(nodes.get(i).GetHiddenStorage().FileListContains(file)) { return i; }
				}
			}
			for(int i = 0; i < nodes.size(); i++) {
				if(nodes.get(i).GetUnhiddenStorage().FileListContains(file)) { return i; }
			}
			return -1;
		}
		
		public File getFile(int pos, boolean hidden, String fileName, String fileType) {
			if(hidden) {
				return nodes.get(pos).GetHiddenStorage().GetFile(fileName, fileType);
			}
			return nodes.get(pos).GetUnhiddenStorage().GetFile(fileName,fileType);
		}
		
		public boolean containsNode(Node node) {
			for(int i = 0; i < nodes.size(); i++) {
				if(node.GetName().equals(nodes.get(i).GetName())) { return true; }
			}
			return false;
		}
	}
}
