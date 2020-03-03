package fileSystem.fs;

import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.NotDirectoryException;
import java.util.LinkedList;
import java.util.List;

public class VirtualFileSystem implements AbstractFileSystem {
	public static final int CAPACITY = 1000;
	private int usedMemory;
	private LinkedList<File> deletedFiles;
	private Directory root;

	public VirtualFileSystem() {
		root = new Directory("/");
		try {
			root.addFile(new Directory("home", root));
		} catch (FileAlreadyExistsException e) {
			throw new IllegalArgumentException("Should not reach here");
		}
		usedMemory = 0;
		deletedFiles = new LinkedList<File>();
	}

	public int getUsedMemory() {
		return usedMemory;
	}

	@Override
	public void makeDirectory(String absolutePath) throws FileAlreadyExistsException, InvalidArgumentException {
		Directory workDirectory = goToWorkDirectory(absolutePath);

		workDirectory.addFile(new Directory(getCurrentFile(absolutePath), workDirectory));
	}

	@Override
	public void createTextFile(String absolutePath) throws FileAlreadyExistsException, InvalidArgumentException {
		Directory workDirectory = goToWorkDirectory(absolutePath);

		workDirectory.addFile(new TextFile(getCurrentFile(absolutePath)));
	}

	@Override
	public String getTextFileContent(String absolutePath) throws FileNotFoundException, InvalidArgumentException {
		Directory workDirectory = goToWorkDirectory(absolutePath);

		File file = workDirectory.getFile(getCurrentFile(absolutePath));

		if (file == null) {
			throw new FileNotFoundException("Text file doesn't exists");
		}

		if (file.isDirectory()) {
			throw new FileNotFoundException("File is directory");
		}

		TextFile textFile = (TextFile) file;

		return textFile.getContent();
	}

	@Override
	public void writeToTextFile(String absolutePath, int line, String content, boolean overwrite)
			throws FileNotFoundException, NotEnoughMemoryException, InvalidArgumentException {
		Directory workDirectory = goToWorkDirectory(absolutePath);

		File file = workDirectory.getFile(getCurrentFile(absolutePath));

		if (file == null) {
			throw new FileNotFoundException("Text file doesn't exists");
		}

		if (file.isDirectory()) {
			throw new FileNotFoundException("File is directory");
		}

		TextFile textFile = (TextFile) file;

		if (!freeEnoughSpace(textFile, line, content, overwrite)) {
			throw new NotEnoughMemoryException("Not enough memory");
		}

		textFile.write(line, content, overwrite);
	}

	@Override
	public List<String> getDirectoryContent(String absolutePath, FilterBy option)
			throws NotDirectoryException, FileNotFoundException, InvalidArgumentException {
		Directory workDirectory = goToWorkDirectory(absolutePath);

		File file = workDirectory.getFile(getCurrentFile(absolutePath));

		if (file == null) {
			throw new FileNotFoundException("Directory doesn't exists");
		}

		if (file.isTextFile()) {
			throw new NotDirectoryException(file.getName());
		}

		Directory directory = (Directory) file;

		return directory.getContent(option);
	}

	@Override
	public boolean isDirectory(String absolutePath) throws FileNotFoundException, InvalidArgumentException {
		Directory workDirectory = goToWorkDirectory(absolutePath);

		File file = workDirectory.getFile(getCurrentFile(absolutePath));

		if (file == null) {
			throw new FileNotFoundException("Directory doesn't exist");
		}

		return file.isDirectory();
	}

	@Override
	public void removeTextFile(String absolutePath) throws FileNotFoundException, InvalidArgumentException {
		Directory workDirectory = goToWorkDirectory(absolutePath);

		deletedFiles.addLast(workDirectory.removeTextFile(getCurrentFile(absolutePath)));
	}

	@Override
	public void removeContentFromLinesInTextFile(String absolutePath, int start, int end)
			throws FileNotFoundException, InvalidArgumentException {
		Directory workDirectory = goToWorkDirectory(absolutePath);

		File file = workDirectory.getFile(getCurrentFile(absolutePath));

		if (file == null) {
			throw new FileNotFoundException("Text file doesn't exists");
		}

		if (file.isDirectory()) {
			throw new FileNotFoundException("File is directory");
		}

		TextFile textFile = (TextFile) file;
		textFile.removeContentFromLines(start, end);
	}

	@Override
	public Integer getWordCount(String absolutePath) throws FileNotFoundException, InvalidArgumentException {
		Directory workDirectory = goToWorkDirectory(absolutePath);

		File file = workDirectory.getFile(getCurrentFile(absolutePath));

		if (file == null) {
			throw new FileNotFoundException("Text file doesn't exists");
		}

		if (file.isDirectory()) {
			throw new FileNotFoundException("File is directory");
		}

		TextFile textFile = (TextFile) file;
		return textFile.getNumberOfWords();
	}

	@Override
	public Integer getLineCount(String absolutePath) throws FileNotFoundException, InvalidArgumentException {
		Directory workDirectory = goToWorkDirectory(absolutePath);

		File file = workDirectory.getFile(getCurrentFile(absolutePath));

		if (file == null) {
			throw new FileNotFoundException("Text file doesn't exists");
		}

		if (file.isDirectory()) {
			throw new FileNotFoundException("File is directory");
		}

		TextFile textFile = (TextFile) file;
		return textFile.getNumberOfLines();
	}

	private Directory goToWorkDirectory(String absolutePath) throws InvalidArgumentException {
		Directory workDirectory = root;

		String[] pathSpliteedByDirectories = absolutePath.split("/");

		if (pathSpliteedByDirectories.length != 0) {
			for (int i = 1; i < pathSpliteedByDirectories.length - 1; i++) {
				File next = workDirectory.getFile(pathSpliteedByDirectories[i]);

				if (next == null || !next.isDirectory()) {
					throw new InvalidArgumentException("Path: " + absolutePath + " " + "doesn't exists");
				}

				workDirectory = (Directory) next;
			}
		}

		return workDirectory;
	}

	private String getCurrentFile(String absolutePath) {
		String[] path = absolutePath.split("/");

		if (path.length == 0) {
			return ".";
		}

		return path[path.length - 1];
	}

	private boolean freeEnoughSpace(TextFile file, int line, String newContent, boolean overwrite)
			throws NotEnoughMemoryException {
		int sizeToAdd = newContent.length();

		if (file.isEmptyLine(line)) {
			sizeToAdd += 1;
		}

		if (overwrite) {
			sizeToAdd -= file.getLineSize(line);
		}

		while (!deletedFiles.isEmpty() && usedMemory + sizeToAdd > CAPACITY) {
			File deletedFile = deletedFiles.removeFirst();
			usedMemory -= deletedFile.getSize();
		}

		if (usedMemory + sizeToAdd > CAPACITY) {
			return false;
		}

		usedMemory += sizeToAdd;
		return true;
	}
}