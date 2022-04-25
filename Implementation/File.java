
public class File
{
	private String name;
	private String type;
	
	public File(String name, String type)
	{
		this.name = name;
		this.type = type;
	}
	
	public String GetName() {return name;}
	public void SetName(String name) {this.name = name;}
	
	public String GetType() {return type;}
	public void SetType(String type) {this.type = type;}
}
