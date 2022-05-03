package mainpackage;
import java.io.Serializable;

import java.io.Serializable;
import javax.swing.JOptionPane;

public class Unhidden_Storage implements Storage, Serializable
{
	private File[] fileList = new File[7]; //Base line storage is 7
	private int numFiles = 0;
	
	public File[] GetFileList() {return fileList;}
	
	public void AppendFileList(File file)
	{
		if (FileListContains(file)) //If "fileList" already contains the file
		{
			JOptionPane.showMessageDialog(null, "File already exists in Unhidden Storage");
			return;
		}
		if (numFiles == fileList.length) //If "fileList" is full, double it's size
		{
			File tmp[] = new File[2 * numFiles];
		    System.arraycopy(fileList, 0, tmp, 0, numFiles); 
		    fileList = tmp;
		    tmp = null;
		}
		fileList[numFiles] = file;
		++numFiles;
		JOptionPane.showMessageDialog(null, "successfully appended file");
	}
	public void DeleteFromFileList(String fileName, String fileType)
	{
		int location = -1;; //Location of possible deletion
		System.out.println(fileName);
		System.out.println(fileType);
		for (int i = 0; i < numFiles; i++)
		{
			String name = fileList[i].GetName();
			System.out.println(name);
			
			String type = fileList[i].GetType();
			System.out.println(type);
			
			if (fileName.equals(name) && fileType.equals(type)) {location = i; break;}
			System.out.println(location);
		}
		if (location == -1) //If the file isn't apart of the file list
		{
			JOptionPane.showMessageDialog(null, "File doesn't exist in Unhidden Storage");
			return;
		}
		for (int i = location; i < numFiles - 1; i++) {fileList[i] = fileList[i + 1];} //Delete this user from list
		--numFiles;
		JOptionPane.showMessageDialog(null, "Successfully delted file");
	}
	
	public boolean FileListContains(File file)
	{
		for (int i = 0; i < numFiles; i++)
		{
			if (FileEqualsFile(file, fileList[i])) {return true;}
		}
		return false;
	}
	
	public File GetFile(String name, String type)
	{
		for (int i = 0; i < numFiles; i++)
		{
			if (fileList[i].GetName().equals(name) && fileList[i].GetType().equals(type)) {return fileList[i];}
		}
		return null;
	}
	
	public boolean FileEqualsFile(File file1, File file2)
	{
		String name1 = file1.GetName();
		String type1 = file1.GetType();
		String name2 = file2.GetName();
		String type2 = file2.GetType();
		
		if (name1.equals(name2) && type1.equals(type2)) {return true;}
		return false;
	}
	
	public int GetNumFiles() {return numFiles;}
}