package fileSystem;

public class Path {
	private String homeDirectory;
	private String currentDirectory;

	public Path() {
		homeDirectory = "/home";
		currentDirectory = "/home";
	}

	public String getCurrentDirectory() {
		return currentDirectory;
	}

	public void setCurrentDirectory(String currentDirectory) {
		if (currentDirectory == null) {
			this.currentDirectory = homeDirectory;
		} else {
			this.currentDirectory = currentDirectory;
		}
	}

	public String getAbsolutePath(String relativePath) {
		if (relativePath.indexOf('/') == 0) { 
			return relativePath;
		}
		
		return currentDirectory + "/" + relativePath;
	}
}
