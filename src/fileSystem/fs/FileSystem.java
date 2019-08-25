package fileSystem.fs;

import java.util.List;

public class FileSystem implements AbstractFileSystem {
	private Directory root;

	public FileSystem() {
		root = new Directory("/");
		root.addFile(new Directory("home", root));
	}

	@Override
	public String makeDirectory(String absolutePath) {
		Directory workDirectory = goToWorkDirectory(absolutePath);

		if (workDirectory == null) {
			return "Path doesn't exists!";
		}

		return workDirectory.addFile(new Directory(getCurrentFile(absolutePath), workDirectory));
	}

	@Override
	public String createTextFile(String absolutePath) {
		Directory workDirectory = goToWorkDirectory(absolutePath);

		if (workDirectory == null) {
			return "Path doesn't exists!";
		}

		return workDirectory.addFile(new TextFile(getCurrentFile(absolutePath)));
	}

	@Override
	public String getTextFileContent(String absolutePath) {
		Directory workDirectory = goToWorkDirectory(absolutePath);

		if (workDirectory == null) {
			return "Path doesn't exists!";
		}

		File file = workDirectory.getFile(getCurrentFile(absolutePath));

		if (file == null) {
			return "File doesn't exists!";
		}

		if (file.isDirectory()) {
			return "File is directory!";
		}

		TextFile textFile = (TextFile) file;

		return textFile.getContent();
	}

	@Override
	public String writeToTextFile(String absolutePath, int line, String content, boolean overwrite) {
		Directory workDirectory = goToWorkDirectory(absolutePath);

		if (workDirectory == null) {
			return "Path doesn't exists!";
		}

		File file = workDirectory.getFile(getCurrentFile(absolutePath));

		if (file == null) {
			return "File doesn't exists!";
		}

		if (file.isDirectory()) {
			return "File is directory!";
		}

		TextFile textFile = (TextFile) file;
		textFile.write(line, content, overwrite);

		return null;
	}

	@Override
	public List<String> getDirectoryContent(String absolutePath, FilterBy option) {
		Directory workDirectory = goToWorkDirectory(absolutePath);

		if (workDirectory == null) {
			return null;
		}

		File file = workDirectory.getFile(getCurrentFile(absolutePath));

		if (file == null || file.isTextFile()) {
			return null;
		}
		
		Directory directory = (Directory) file;
		
		return directory.getContent(option);
	}

	private Directory goToWorkDirectory(String absolutePath) {
		Directory workDirectory = root;

		String[] pathSpliteedByDirectories = absolutePath.split("/");

		for (int i = 1; i < pathSpliteedByDirectories.length - 1; i++) {
			File next = workDirectory.getFile(pathSpliteedByDirectories[i]);

			if (next == null || !next.isDirectory()) {
				return null;
			}

			workDirectory = (Directory) next;
		}

		return workDirectory;
	}

	private String getCurrentFile(String absolutePath) {
		return absolutePath.substring(absolutePath.lastIndexOf('/') + 1);
	}
}