package commands;

import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import commands.exception.InvalidArgumentException;
import filesystem.DirectoryContentController;
import filesystem.File;
import path.Path;

public class ListDirectoryContent implements Command {
    private static final String SIZE_DESCDENDING_OPTION = "-sortedDesc";
    private static final Comparator<File> DEFAULT = (f1, f2) -> f1.getName().compareTo(f2.getName());
    private static final Comparator<File> SIZE_DESCENDING = (f1, f2) -> Integer.compare(f2.getSize(), f1.getSize());

    private DirectoryContentController fileSystem;
    private Path currentDirectory;

    public ListDirectoryContent(DirectoryContentController fileSystem, Path currentDirectory) {
	this.fileSystem = fileSystem;
	this.currentDirectory = currentDirectory;
    }

    @Override
    public String execute(List<String> arguments, Set<String> options)
	    throws InvalidArgumentException, FileNotFoundException {
	validateOptions(options);

	Comparator<File> fileComparator = getFileComparator(options);
	boolean listCurrentDirectoryContent = arguments.isEmpty();

	if (listCurrentDirectoryContent) {
	    return getCurrentDirectoryContent(fileComparator);
	}
	
	return getDirectoriesContent(arguments, fileComparator);
    }

    private void validateOptions(Set<String> options) throws InvalidArgumentException {
	for (String option : options) {
	    if (!option.equals(SIZE_DESCDENDING_OPTION)) {
		throw new InvalidArgumentException(INVALID_OPTION_MESSAGE);
	    }
	}
    }

    private Comparator<File> getFileComparator(Set<String> options) {
	if (options.contains(SIZE_DESCDENDING_OPTION)) {
	    return SIZE_DESCENDING;
	}

	return DEFAULT;
    }

    private String getCurrentDirectoryContent(Comparator<File> fileComparator)
	    throws InvalidArgumentException, FileNotFoundException {
	String currentDirectoryPath = currentDirectory.getCurrentDirectory();
	List<String> currentDirectoryContent = fileSystem.getDirectoryContent(currentDirectoryPath, fileComparator);
	return getDirectoryContent(currentDirectoryContent);
    }

    private String getDirectoriesContent(List<String> arguments, Comparator<File> fileComparator)
	    throws InvalidArgumentException, FileNotFoundException {
	StringBuilder directoriesContent = new StringBuilder();
	int directoriesCount = arguments.size() - 1;

	for (int i = 0; i <= directoriesCount; i++) {
	    String directoryPath = currentDirectory.getAbsolutePath(arguments.get(i));
	    List<String> directoryContent = fileSystem.getDirectoryContent(directoryPath, fileComparator);
	    directoriesContent.append(getDirectoryContent(directoryContent));

	    if (i != directoriesCount) {
		directoriesContent.append(System.lineSeparator());
	    }
	}

	return directoriesContent.toString();
    }

    private String getDirectoryContent(List<String> content) {
	final String fileSeparator = " ";
	StringBuilder directoryContent = new StringBuilder();

	for (String fileName : content) {
	    directoryContent.append(fileName);
	    directoryContent.append(fileSeparator);
	}

	return directoryContent.toString();
    }
}
