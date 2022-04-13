
public interface Storage
{
	public File[] GetFileList();
	public void AppendFileList(File file);
	public void DeleteFromFileList(String fileName);
}
