package commands;

import java.io.IOException;
import java.util.List;

import filesystem.DirectoryContentController;
import filesystem.FilterBy;
import filesystem.exceptions.InvalidArgumentException;
import path.Path;

public class ListDirectoryContent implements Command {
    private DirectoryContentController fileSystem;
    private Path currentDirectory;

    public ListDirectoryContent(DirectoryContentController fileSystem, Path currentDirectory) {
	this.fileSystem = fileSystem;
	this.currentDirectory = currentDirectory;
    }

    @Override
    public String execute(List<String> arguments, List<String> options) throws InvalidArgumentException, IOException {
	FilterBy flag = validateOptions(options);

	int size = arguments.size();

	if (size == 0) {
	    return appendDirectoryContent(fileSystem.getDirectoryContent(currentDirectory.getCurrentDirectory(), flag));
	}

	StringBuilder result = new StringBuilder();
	for (int i = 0; i < size - 1; i++) {
	    result.append(appendDirectoryContent(
		    fileSystem.getDirectoryContent(currentDirectory.getAbsolutePath(arguments.get(i)), flag)));
	    result.append(System.lineSeparator());
	}
	result.append(appendDirectoryContent(
		fileSystem.getDirectoryContent(currentDirectory.getAbsolutePath(arguments.get(size - 1)), flag)));

	return result.toString();
    }

    private FilterBy validateOptions(List<String> options) throws InvalidArgumentException {
	FilterBy flag = FilterBy.DEFAULT;

	for (String option : options) {
	    if (!option.equals("-sortedDesc")) {
		throw new InvalidArgumentException("Invalid option");
	    }
	    flag = FilterBy.SIZE_DESCENDING;
	}

	return flag;
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
