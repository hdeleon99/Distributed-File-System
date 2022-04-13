import java.util.*;

public class Server
{
	private static Log log = new Log(); //Eager singleton to ensure only 1 log | "static" shares this log across the entire system
	private File[] hiddenFiles;
	private File[] unhiddenFiles;
	private static Client[] clientList = new Client[7]; //Eager Singleton to ensure only 1 list of clients | "static" shares the list across the entire server
	
	public Server() {}
	
	public boolean IsPartOfServer(Client client) {}
	
	public void ReadLog() {}
	public void AppendLog(String action, Date date) {}
	public void ClearLog() {}
	
	public void ReadFileList(boolean hidden) {}
	public File GetFile(String fileName) {}
	public void AppendFileList(File file) {}
	public void DeleteFromFileList(String fileName) {}
	
	public void ClearClientList() {}
	public void DeleteFromClientList(Client client) {}
	
	public void AppendClientList(Client client) {}
}
