package fileSystem.fs;

public abstract class File {
	private String name;
	
	protected File(String name) {
		//check for valid name
		this.name = name;
	}
	
	abstract int getSize();
	
	abstract boolean isDirectory();
	
	abstract boolean isTextFile();
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		// check for valid name
		this.name = name;
	}
}
