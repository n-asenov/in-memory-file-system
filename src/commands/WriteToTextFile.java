package commands;

import java.io.FileNotFoundException;
import java.util.List;

import filesystem.VirtualFileSystem;
import filesystem.exceptions.InvalidArgumentException;
import filesystem.exceptions.NotEnoughMemoryException;
import path.Path;

public class WriteToTextFile implements Command {
    private VirtualFileSystem fileSystem;
    private Path currentDirectory;

    public WriteToTextFile(VirtualFileSystem fileSystem, Path currentDirectory) {
	this.fileSystem = fileSystem;
	this.currentDirectory = currentDirectory;
    }

    @Override
    public String execute(List<String> arguments, List<String> options)
	    throws NotEnoughMemoryException, InvalidArgumentException, FileNotFoundException {
	boolean overwrite = false;

	for (String option : options) {
	    validateOption(option);
	    overwrite = true;
	}

	validateArguments(arguments);

	String absolutePath = currentDirectory.getAbsolutePath(arguments.get(0));
	int line = getLine(arguments);
	String lineContent = getLineContent(arguments);

	if (overwrite) {
	    fileSystem.writeToTextFile(absolutePath, line, lineContent);
	} else {
	    fileSystem.appendToTextFile(absolutePath, line, lineContent);
	}

	return null;
    }

    private void validateOption(String option) throws InvalidArgumentException {
	if (!option.equals("-overwrite")) {
	    throw new InvalidArgumentException("Invalid option");
	}
    }

    private void validateArguments(List<String> arguments) throws InvalidArgumentException {
	if (arguments.size() < 3) {
	    throw new InvalidArgumentException("Commmand expects more arguments");
	}
    }

    private int getLine(List<String> arguments) throws InvalidArgumentException {
	int line = 0;

	try {
	    line = Integer.parseInt(arguments.get(1));
	} catch (NumberFormatException e) {
	    throw new InvalidArgumentException("Second argument must be a integer");
	}

	return line;
    }

    private String getLineContent(List<String> arguments) {
	StringBuilder lineContent = new StringBuilder();
	int size = arguments.size() - 1;

	for (int i = 2; i < size; i++) {
	    lineContent.append(arguments.get(i)).append(" ");
	}
	lineContent.append(arguments.get(size));

	return lineContent.toString();
    }
}
