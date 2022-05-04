package Main;
import java.util.*;
public interface Storage
{
	public List<File> GetFileList();
	public void AppendFileList(File file);
	public void DeleteFromFileList(String fileName, String fileType);
	public boolean FileListContains(File file);
	public File GetFile(String name, String type);
	public void PrintFileList();
}
