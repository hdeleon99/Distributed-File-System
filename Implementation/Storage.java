package Main;

public interface Storage
{
	public File[] GetFileList();
	public void AppendFileList(File file);
	public void DeleteFromFileList(String fileName, String fileType);
	public boolean FileListContains(File file);
	public File GetFile(String name, String type);
	public int GetNumFiles();
}
