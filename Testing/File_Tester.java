package Testing;
import Main.File;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;

public class File_Tester {
	@Test
	public void setName_test() {
		File file = new File("filename", "filetype");
		file.SetName("cats");
		System.out.println("Testing SetName() with parameters: ('cats')");
		System.out.println(file.GetName());
		assertTrue(file.GetName()=="cats");
	}
	
	@Test
	public void setType_test() {
		File file = new File("filename", "filetype");
		file.SetType("txt");
		System.out.println("Testing SetType() with parameters: ('txt')");
		System.out.println(file.GetType());
		assertTrue(file.GetType()=="txt");
	}
}