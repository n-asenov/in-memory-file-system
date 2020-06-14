package filesystem;

import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.NotDirectoryException;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;

import filesystem.exceptions.InvalidArgumentException;
import filesystem.exceptions.NotEnoughMemoryException;

public class VirtualFileSystem implements TextFileController, TextFileContentController, TextFileStatistics,
	DirectoryController, DirectoryContentController, DirectoryValidator {
    public static final int MEMORY_CAPACITY = 1000;
    public static final String PATH_SEPARATOR = "/";

    private Directory root;
    private Deque<File> deletedFiles;
    private int usedMemory;

    public VirtualFileSystem() {
	root = new Directory("/");
	try {
	    root.addFile(new Directory("home", root));
	} catch (FileAlreadyExistsException e) {
	    throw new IllegalArgumentException("Should not reach here");
	}
	usedMemory = 0;
	deletedFiles = new ArrayDeque<>();
    }

    public int getUsedMemory() {
	return usedMemory;
    }

    @Override
    public void createTextFile(String absolutePath) throws InvalidArgumentException, FileAlreadyExistsException {
	Directory workDirectory = goToWorkDirectory(absolutePath);

	workDirectory.addFile(new TextFile(getCurrentFile(absolutePath)));
    }

    @Override
    public void removeTextFile(String absolutePath) throws FileNotFoundException, InvalidArgumentException {
	Directory workDirectory = goToWorkDirectory(absolutePath);

	deletedFiles.addLast(workDirectory.removeTextFile(getCurrentFile(absolutePath)));
    }

    @Override
    public void writeToTextFile(String absolutePath, int line, String content)
	    throws FileNotFoundException, NotEnoughMemoryException, InvalidArgumentException {
	TextFile textFile = getTextFile(absolutePath);

	usedMemory -= textFile.getLineSize(line);

	freeEnoughSpace(textFile, line, content);

	textFile.overwrite(line, content);
    }

    @Override
    public void appendToTextFile(String absolutePath, int line, String content)
	    throws FileNotFoundException, InvalidArgumentException, NotEnoughMemoryException {
	TextFile textFile = getTextFile(absolutePath);

	freeEnoughSpace(textFile, line, content);

	textFile.append(line, content);
    }
    
    private TextFile getTextFile(String absolutePath) throws InvalidArgumentException, FileNotFoundException {
	Directory workDirectory = goToWorkDirectory(absolutePath);

	File file = workDirectory.getFile(getCurrentFile(absolutePath));

	if (file == null) {
	    throw new FileNotFoundException("Text file doesn't exists");
	}

	if (file instanceof Directory) {
	    throw new FileNotFoundException("File is directory");
	}

	return (TextFile) file;
    }
    
    private void freeEnoughSpace(TextFile file, int line, String newContent) throws NotEnoughMemoryException {
	int sizeToAdd = newContent.length();

	if (file.isEmptyLine(line)) {
	    sizeToAdd += 1;
	}

	while (!deletedFiles.isEmpty() && usedMemory + sizeToAdd > MEMORY_CAPACITY) {
	    File deletedFile = deletedFiles.removeFirst();
	    usedMemory -= deletedFile.getSize();
	}

	if (usedMemory + sizeToAdd > MEMORY_CAPACITY) {
	    throw new NotEnoughMemoryException("Not enough memory");
	}

	usedMemory += sizeToAdd;
    }

    @Override
    public void removeContentFromLinesInTextFile(String absolutePath, int start, int end)
	    throws FileNotFoundException, InvalidArgumentException {
	TextFile textFile = getTextFile(absolutePath);

	textFile.removeContentFromLines(start, end);
    }

    @Override
    public String getTextFileContent(String absolutePath) throws FileNotFoundException, InvalidArgumentException {
	TextFile textFile = getTextFile(absolutePath);

	return textFile.getContent();
    }

    @Override
    public Integer getWordCount(String absolutePath) throws FileNotFoundException, InvalidArgumentException {
	TextFile textFile = getTextFile(absolutePath);

	return textFile.getNumberOfWords();
    }

    @Override
    public Integer getLineCount(String absolutePath) throws FileNotFoundException, InvalidArgumentException {
	TextFile textFile = getTextFile(absolutePath);

	return textFile.getNumberOfLines();
    }

    @Override
    public void makeDirectory(String absolutePath) throws FileAlreadyExistsException, InvalidArgumentException {
	Directory workDirectory = goToWorkDirectory(absolutePath);

	workDirectory.addFile(new Directory(getCurrentFile(absolutePath), workDirectory));
    }

    @Override
    public List<String> getDirectoryContent(String absolutePath, Comparator<File> comparator)
	    throws NotDirectoryException, FileNotFoundException, InvalidArgumentException {
	File file = getDirectory(absolutePath);

	if (file instanceof TextFile) {
	    throw new NotDirectoryException(file.getName());
	}

	Directory directory = (Directory) file;

	return directory.getContent(comparator);
    }

    @Override
    public boolean isDirectory(String absolutePath) throws FileNotFoundException, InvalidArgumentException {
	File file = getDirectory(absolutePath);

	return file instanceof Directory;
    }
    
    private File getDirectory(String absolutePath) throws FileNotFoundException, InvalidArgumentException {
	Directory workDirectory = goToWorkDirectory(absolutePath);

	File file = workDirectory.getFile(getCurrentFile(absolutePath));

	if (file == null) {
	    throw new FileNotFoundException("Directory doesn't exist");
	}
	
	return file;
    }

    private Directory goToWorkDirectory(String absolutePath) throws InvalidArgumentException {
	Directory workDirectory = root;

	String[] pathSpliteedByDirectories = absolutePath.split(PATH_SEPARATOR);

	if (pathSpliteedByDirectories.length != 0) {
	    for (int i = 1; i < pathSpliteedByDirectories.length - 1; i++) {
		File next = workDirectory.getFile(pathSpliteedByDirectories[i]);

		if (next == null || !(next instanceof Directory)) {
		    throw new InvalidArgumentException("Path: " + absolutePath + " " + "doesn't exists");
		}

		workDirectory = (Directory) next;
	    }
	}

	return workDirectory;
    }

    private String getCurrentFile(String absolutePath) {
	String[] filePath = absolutePath.split(PATH_SEPARATOR);

	if (filePath.length == 0) {
	    String currentDirectory = ".";
	    return currentDirectory;
	}

	return filePath[filePath.length - 1];
    }

}