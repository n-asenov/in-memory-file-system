package commands;

import java.nio.file.FileAlreadyExistsException;
import java.util.List;

import filesystem.TextFileController;
import filesystem.exceptions.InvalidArgumentException;
import path.Path;

public class CreateTextFile implements Command {
    private TextFileController fileSystem;
    private Path currentDirectory;

    public CreateTextFile(TextFileController fileSystem, Path currentDirectory) {
	this.fileSystem = fileSystem;
	this.currentDirectory = currentDirectory;
    }

    @Override
    public String execute(List<String> options, List<String> arguments)
	    throws InvalidArgumentException, FileAlreadyExistsException {
	validateOptions(options);

	for (String argument : arguments) {
	    fileSystem.createTextFile(currentDirectory.getAbsolutePath(argument));
	}

	return "";
    }

    private void validateOptions(List<String> options) throws InvalidArgumentException {
	if (!options.isEmpty()) {
	    throw new InvalidArgumentException("Invalid option");
	}
    }
}
