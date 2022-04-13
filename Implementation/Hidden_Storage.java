
public class Hidden_Storage implements Storage
{
	private File[] fileList = new File[7]; //Base line storage is 7
	
	public File[] GetFileList() {return fileList;}
	public void AppendFileList(File file) {}
	public void DeleteFromFileList(String fileName) {}
}
