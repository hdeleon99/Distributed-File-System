
public class File
{
	private String name;
	private String path;
	private float size;
	private String type;
	
	public File(String name, String path, float size, String type)
	{
		this.name = name;
		this.path = path;
		this.size = size;
		this.type = type;
	}
	
	public String GetName() {return name;}
	public void SetName(String name) {this.name = name;}
	
	public String GetPath() {return path;}
	public void SetPath(String path) {this.path = path;}
	
	public float GetSize() {return size;}
	public void SetSize(float size) {this.size = size;}
	
	public String GetType() {return type;}
	public void SetType(String type) {this.type = type;}
}
