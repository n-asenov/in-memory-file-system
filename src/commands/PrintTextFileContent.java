package commands;

import java.io.IOException;
import java.util.List;

import filesystem.VirtualFileSystem;
import filesystem.exceptions.InvalidArgumentException;
import path.Path;

public class PrintTextFileContent implements Command {
    private VirtualFileSystem fileSystem;
    private Path currentDirectory;

    public PrintTextFileContent(VirtualFileSystem fileSystem, Path currentDirectory) {
	this.fileSystem = fileSystem;
	this.currentDirectory = currentDirectory;
    }

    @Override
    public String execute(List<String> options, List<String> arguments) throws InvalidArgumentException, IOException {
	validateOptions(options);
	validateArguments(arguments);

	StringBuilder result = new StringBuilder();
	for (String file : arguments) {
	    result.append(fileSystem.getTextFileContent(currentDirectory.getAbsolutePath(file)));
	    result.append(System.lineSeparator());
	}

	result.delete(result.lastIndexOf(System.lineSeparator()), result.length());

	return result.toString();
    }

    private void validateOptions(List<String> options) throws InvalidArgumentException {
	if (options.size() != 0) {
	    throw new InvalidArgumentException("Invalid option");
	}
    }

    private void validateArguments(List<String> arguments) throws InvalidArgumentException {
	if (arguments.isEmpty()) {
	    throw new InvalidArgumentException("Invalid argument");
	}
    }
}
