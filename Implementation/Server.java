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
	private static List<File> hiddenFiles = new ArrayList<File>();
	private static List<File> unhiddenFiles = new ArrayList<File>();
	private static String [] logActions = {"Exited system : userID ->", "Uploaded file :", "Downloaded file :",
											"Viewed log : userID ->", "Removed file "};
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		ServerSocket server = null;
		try {
			server = new ServerSocket(1234);
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
			boolean supervisor = rqst.getUser().GetSupervisor();
			int pos;
			String logMsg = "";
			switch(rqst.getRequestType()) {
			case 0:// exit
					rqst.setLoggedIn(false);
					rqst.setRequestStatus(true);
					logMsg += rqst.getUser().getID();
				break;
			case 1:// upload file
					if(supervisor && hidden) { AppendFileList(rqst.getFile(),true); }
					else { AppendFileList(rqst.getFile(), false); }
					logMsg += rqst.getFileName();
					rqst.setRequestStatus(true);
					break;
			case 2:// request file
					pos = FilePos(rqst.getFileName(), rqst.getFileType(), hidden);
					if(pos != -1) {
						if(supervisor && hidden) { rqst.setFile(hiddenFiles.get(pos)); }
						else { rqst.setFile(unhiddenFiles.get(pos)); }
						logMsg += rqst.getFileName();
						rqst.setRequestStatus(true);
					}
					break;
			case 3:// view log
				if(supervisor) { rqst.setLog(log); }
				rqst.setRequestStatus(true);
				logMsg += rqst.getUser().getID();
				break;
			case 4:// remove file
				if(supervisor) {
					pos = FilePos(rqst.getFileName(), rqst.getFileType(), hidden);
					if(pos != -1) {
						DeleteFromFileList(pos, hidden);
						logMsg += rqst.getFileName();
						rqst.setRequestStatus(true);
					}
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
			// if successful request and not clearing log and shutting server down -> append log
			if(rqst.getRequestStatus() && rqst.getRequestType() != 5 && rqst.getRequestType() != 6) {
				AppendLog(logActions[rqst.getRequestType()] + logMsg, new Date());
			}
			// if failed request -> send error message
			else if(!rqst.getRequestStatus()) { rqst.setErrStatus(true); }
			return rqst;
		}

		public void AppendLog(String action, Date date) { log.AppendLog(action, date); }
		public void ClearLog() { log.ClearLog(); }
		
		public void AppendFileList(File file, boolean hidden) {
			if(hidden) { hiddenFiles.add(file); }
			else { unhiddenFiles.add(file); }
		}
		
		public void DeleteFromFileList(int pos, boolean hidden) {	
			if(hidden) { hiddenFiles.remove(pos); }
			else { unhiddenFiles.remove(pos); }
		}
		
		public int FilePos(String fileName, String fileType, boolean hidden) {
			if(hidden) {
				for(int i = 0; i < hiddenFiles.size(); i++) {
					if(fileName.equals(hiddenFiles.get(i).GetName()) && fileType.equals(hiddenFiles.get(i).GetType())) { return i; }
				}
			}
			else {
				for(int i = 0; i < unhiddenFiles.size(); i++) {
					if(fileName.equals(unhiddenFiles.get(i).GetName()) && fileType.equals(unhiddenFiles.get(i).GetType())) { return i; }
				}
			}
			return -1;// -1 means file is not present
		}
		
		public boolean getShutDown() { return shutdown; }
	}
}
