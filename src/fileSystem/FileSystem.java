package fileSystem;

public class FileSystem {
	private Directory root;

	public FileSystem() {
		root = new Directory("/");
		root.addSubDirectory("home");
	}

	public void makeDirectory(String absolutePath) {
		StringBuilder newDirectoryName = new StringBuilder();

		Directory workDirectory = goToWorkDirectory(absolutePath, newDirectoryName);

		if (workDirectory != null) {
			workDirectory.addSubDirectory(newDirectoryName.toString());
		}
	}

	public void createFile(String absolutePath) {
		StringBuilder newFileName = new StringBuilder();

		Directory workDirectory = goToWorkDirectory(absolutePath, newFileName);

		if (workDirectory != null) {
			workDirectory.addFile(newFileName.toString());
		}
	}

	public void printFileContent(String absolutePath) {
		StringBuilder file = new StringBuilder();

		Directory workDirectory = goToWorkDirectory(absolutePath, file);

		if (workDirectory != null) {
			if (workDirectory.getFiles().get(file.toString()) == null) {
				System.out.println("File doesn't exists!");
				return;
			}

			workDirectory.getFiles().get(file.toString()).printContent();
		}
	}

	public void writeToFile(String absolutePath, int lineNumber, String newContent, boolean overwrite) {
		StringBuilder file = new StringBuilder();

		Directory workDirectory = goToWorkDirectory(absolutePath, file);

		if (workDirectory != null) {
			if (workDirectory.getFiles().get(file.toString()) == null) {
				System.out.println("File doesn't exists!");
				return;
			}

			workDirectory.getFiles().get(file.toString()).write(lineNumber, newContent, overwrite);
		}
	}

	public void listDirectoryContent(String absolutePath, FilterBy option) {
		StringBuilder directoryToList = new StringBuilder();

		Directory workDirectory = goToWorkDirectory(absolutePath, directoryToList);

		if (workDirectory != null) {
			if (workDirectory.getSubDirectories().get(directoryToList.toString()) == null) {
				System.out.println("Directory doesn't exists!");
				return;
			}

			workDirectory.getSubDirectories().get(directoryToList.toString()).listContent(option);
		}
	}

	private Directory goToWorkDirectory(String absolutePath, StringBuilder current) {
		String[] pathSplittedByDirectories = absolutePath.split("/");

		Directory workDirectory = root;

		for (int i = 1; i < pathSplittedByDirectories.length - 1; i++) {
			workDirectory = workDirectory.getSubDirectories().get(pathSplittedByDirectories[i]);
			if (workDirectory == null) {
				System.out.println("Path doesn't exists!");
				return null;
			}
		}

		current.replace(0, current.length(), pathSplittedByDirectories[pathSplittedByDirectories.length - 1]);

		return workDirectory;
	}
}