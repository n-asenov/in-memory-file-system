package commands;

import java.io.FileNotFoundException;
import java.util.List;

import filesystem.TextFileController;
import filesystem.exceptions.InvalidArgumentException;
import path.Path;

public class RemoveTextFile implements Command {
    private TextFileController fileSystem;
    private Path currentDirectory;

    public RemoveTextFile(TextFileController fileSystem, Path currentDirectory) {
	this.fileSystem = fileSystem;
	this.currentDirectory = currentDirectory;
    }

    @Override
    public String execute(List<String> arguments, List<String> options) throws InvalidArgumentException, FileNotFoundException {
	validateOptions(options);

	for (String argument : arguments) {
	    fileSystem.removeTextFile(currentDirectory.getAbsolutePath(argument));
	}

	return "";
    }

    private void validateOptions(List<String> options) throws InvalidArgumentException {
	if (!options.isEmpty()) {
	    throw new InvalidArgumentException("Invalid option");
	}
    }
}
