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
	deletedFiles = new ArrayDeque<File>();
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
    public void writeToTextFile(String absolutePath, int line, String content, boolean overwrite)
	    throws FileNotFoundException, NotEnoughMemoryException, InvalidArgumentException {
	Directory workDirectory = goToWorkDirectory(absolutePath);

	File file = workDirectory.getFile(getCurrentFile(absolutePath));

	if (file == null) {
	    throw new FileNotFoundException("Text file doesn't exists");
	}

	if (file instanceof Directory) {
	    throw new FileNotFoundException("File is directory");
	}

	TextFile textFile = (TextFile) file;

	if (!freeEnoughSpace(textFile, line, content, overwrite)) {
	    throw new NotEnoughMemoryException("Not enough memory");
	}

	if (overwrite) {
	    textFile.overwrite(line, content);
	} else {
	    textFile.append(line, content);
	}
    }

    @Override
    public void removeContentFromLinesInTextFile(String absolutePath, int start, int end)
	    throws FileNotFoundException, InvalidArgumentException {
	Directory workDirectory = goToWorkDirectory(absolutePath);

	File file = workDirectory.getFile(getCurrentFile(absolutePath));

	if (file == null) {
	    throw new FileNotFoundException("Text file doesn't exists");
	}

	if (file instanceof Directory) {
	    throw new FileNotFoundException("File is directory");
	}

	TextFile textFile = (TextFile) file;
	textFile.removeContentFromLines(start, end);
    }

    @Override
    public String getTextFileContent(String absolutePath) throws FileNotFoundException, InvalidArgumentException {
	Directory workDirectory = goToWorkDirectory(absolutePath);

	File file = workDirectory.getFile(getCurrentFile(absolutePath));

	if (file == null) {
	    throw new FileNotFoundException("Text file doesn't exists");
	}

	if (file instanceof Directory) {
	    throw new FileNotFoundException("File is directory");
	}

	TextFile textFile = (TextFile) file;

	return textFile.getContent();
    }

    @Override
    public Integer getWordCount(String absolutePath) throws FileNotFoundException, InvalidArgumentException {
	Directory workDirectory = goToWorkDirectory(absolutePath);

	File file = workDirectory.getFile(getCurrentFile(absolutePath));

	if (file == null) {
	    throw new FileNotFoundException("Text file doesn't exists");
	}

	if (file instanceof Directory) {
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

	if (file instanceof Directory) {
	    throw new FileNotFoundException("File is directory");
	}

	TextFile textFile = (TextFile) file;
	return textFile.getNumberOfLines();
    }

    @Override
    public void makeDirectory(String absolutePath) throws FileAlreadyExistsException, InvalidArgumentException {
	Directory workDirectory = goToWorkDirectory(absolutePath);

	workDirectory.addFile(new Directory(getCurrentFile(absolutePath), workDirectory));
    }

    @Override
    public List<String> getDirectoryContent(String absolutePath, FilterBy option)
	    throws NotDirectoryException, FileNotFoundException, InvalidArgumentException {
	Directory workDirectory = goToWorkDirectory(absolutePath);

	File file = workDirectory.getFile(getCurrentFile(absolutePath));

	if (file == null) {
	    throw new FileNotFoundException("Directory doesn't exists");
	}

	if (file instanceof TextFile) {
	    throw new NotDirectoryException(file.getName());
	}

	Directory directory = (Directory) file;

	return directory.getContent(getComparator(option));
    }

    private Comparator<File> getComparator(FilterBy option) {
	if (option == FilterBy.SIZE_DESCENDING) {
	    return (f1, f2) -> Integer.compare(f2.getSize(), f1.getSize());
	}

	// Default sort
	return (f1, f2) -> f1.getName().compareTo(f2.getName());
    }

    @Override
    public boolean isDirectory(String absolutePath) throws FileNotFoundException, InvalidArgumentException {
	Directory workDirectory = goToWorkDirectory(absolutePath);

	File file = workDirectory.getFile(getCurrentFile(absolutePath));

	if (file == null) {
	    throw new FileNotFoundException("Directory doesn't exist");
	}

	return file instanceof Directory;
    }

    private Directory goToWorkDirectory(String absolutePath) throws InvalidArgumentException {
	Directory workDirectory = root;

	String[] pathSpliteedByDirectories = absolutePath.split("/");

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

	while (!deletedFiles.isEmpty() && usedMemory + sizeToAdd > MEMORY_CAPACITY) {
	    File deletedFile = deletedFiles.removeFirst();
	    usedMemory -= deletedFile.getSize();
	}

	if (usedMemory + sizeToAdd > MEMORY_CAPACITY) {
	    return false;
	}

	usedMemory += sizeToAdd;
	return true;
    }
}