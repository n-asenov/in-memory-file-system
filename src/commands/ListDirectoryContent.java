package commands;

import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import filesystem.DirectoryContentController;
import filesystem.File;
import filesystem.exceptions.InvalidArgumentException;
import path.Path;

public class ListDirectoryContent implements Command {
    private static final Comparator<File> DEFAULT = (f1, f2) -> f1.getName().compareTo(f2.getName());
    private static final Comparator<File> SIZE_DESCENDING = (f1, f2) -> Integer.compare(f2.getSize(), f1.getSize());
    
    private DirectoryContentController fileSystem;
    private Path currentDirectory;

    public ListDirectoryContent(DirectoryContentController fileSystem, Path currentDirectory) {
	this.fileSystem = fileSystem;
	this.currentDirectory = currentDirectory;
    }

    @Override
    public String execute(List<String> arguments, Set<String> options) throws InvalidArgumentException, FileNotFoundException {
	Comparator<File> comparator = getComparator(options);
	int size = arguments.size();

	if (size == 0) {
	    return appendDirectoryContent(fileSystem.getDirectoryContent(currentDirectory.getCurrentDirectory(), comparator));
	}

	StringBuilder result = new StringBuilder();
	for (int i = 0; i < size - 1; i++) {
	    result.append(appendDirectoryContent(
		    fileSystem.getDirectoryContent(currentDirectory.getAbsolutePath(arguments.get(i)), comparator)));
	    result.append(System.lineSeparator());
	}
	result.append(appendDirectoryContent(
		fileSystem.getDirectoryContent(currentDirectory.getAbsolutePath(arguments.get(size - 1)), comparator)));

	return result.toString();
    }

    private Comparator<File> getComparator(Set<String> options) throws InvalidArgumentException {
	Comparator<File> comparator = DEFAULT;

	for (String option : options) {
	    if (!option.equals("-sortedDesc")) {
		throw new InvalidArgumentException("Invalid option");
	    }
	    
	    comparator = SIZE_DESCENDING;
	}

	return comparator;
    }

    private String appendDirectoryContent(List<String> content) {
	StringBuilder result = new StringBuilder();

	for (String fileName : content) {
	    result.append(fileName);
	    result.append(" ");
	}

	return result.toString();
    }
}
