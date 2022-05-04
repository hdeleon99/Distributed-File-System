package Testing;

import Main.Node;
import Main.Unhidden_Storage;
import Main.User;
import Main.File;
import Main.Hidden_Storage;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;

public class StorageTester
{

	@Test
	public void testAppendFile()
	{
		User user = new User("SMiles", "Naruto");
		Node node = new Node("GOAT", user);
		File file = new File("File", "txt");
		
		node.GetUnhiddenStorage().AppendFileList(file);
		node.GetHiddenStorage().AppendFileList(file);
		System.out.println("Unhidden Name: " + node.GetUnhiddenStorage().GetFileList().get(0).GetName());
		System.out.println("Unhidden Type: " + node.GetUnhiddenStorage().GetFileList().get(0).GetType());
		System.out.println("Hidden Name: " + node.GetHiddenStorage().GetFileList().get(0).GetName());
		System.out.println("Hidden Type: " + node.GetHiddenStorage().GetFileList().get(0).GetType());
		System.out.println("Unhidden Number of Files: " + node.GetUnhiddenStorage().GetFileList().size());
		System.out.println("Hidden number of Files:: " + node.GetHiddenStorage().GetFileList().size());
		
		assertTrue(node.GetUnhiddenStorage().GetFileList().size() == 1 && node.GetHiddenStorage().GetFileList().size() == 1);
	}
	
	@Test
	public void testFileListsGrows()
	{
		User user = new User("Miles", "Naruto");
		Node node = new Node("GOAT", user);
		Unhidden_Storage unhidden = node.GetUnhiddenStorage();
		Hidden_Storage hidden = node.GetHiddenStorage();
		File file1 = new File("Avengers", "txt");
		File file2 = new File("Avengers: Age of Ultron", "txt");
		File file3 = new File("Avengers: Infinity War", "txt");
		File file4 = new File("Avengers: Endgame", "txt");
		File file5 = new File("Star Wars: The Force Awakens", "txt");
		File file6 = new File("Star Wars: The Last Jedi", "txt");
		File file7 = new File("Star Wars: The Rise of Skywalker", "txt");
		File file8 = new File("Frozen", "txt");
		
		unhidden.AppendFileList(file1);
		unhidden.AppendFileList(file2);
		unhidden.AppendFileList(file3);
		unhidden.AppendFileList(file4);
		unhidden.AppendFileList(file5);
		unhidden.AppendFileList(file6);
		unhidden.AppendFileList(file7);
		unhidden.AppendFileList(file8);
		
		hidden.AppendFileList(file1);
		hidden.AppendFileList(file2);
		hidden.AppendFileList(file3);
		hidden.AppendFileList(file4);
		hidden.AppendFileList(file5);
		hidden.AppendFileList(file6);
		hidden.AppendFileList(file7);
		hidden.AppendFileList(file8);
		
		System.out.println("Number of unhidden files: " + unhidden.GetFileList().size());
		System.out.println("Number of hidden files: " + hidden.GetFileList().size());
		assertTrue(unhidden.GetFileList().size() == 8 && hidden.GetFileList().size() == 8);
	}
	
	@Test
	public void testFileListContains()
	{
		User user = new User("Miles", "Naruto");
		Node node = new Node("GOAT", user);
		Unhidden_Storage unhidden = node.GetUnhiddenStorage();
		Hidden_Storage hidden = node.GetHiddenStorage();
		File file = new File("Avengers", "txt");
		
		unhidden.AppendFileList(file);
		hidden.AppendFileList(file);
		assertTrue(unhidden.FileListContains(file) && hidden.FileListContains(file));
	}
	
	@Test
	public void testGetFile()
	{
		User user = new User("Miles", "Naruto");
		Node node = new Node("GOAT", user);
		Unhidden_Storage unhidden = node.GetUnhiddenStorage();
		Hidden_Storage hidden = node.GetHiddenStorage();
		File file = new File("Avengers", "txt");
		
		unhidden.AppendFileList(file);
		hidden.AppendFileList(file);
		File temp1 = unhidden.GetFile("Avengers", "txt");
		File temp2 = hidden.GetFile("Avengers", "txt");
		assertTrue(unhidden.FileEqualsFile(file, temp1) && hidden.FileEqualsFile(file, temp2));
	}

}
