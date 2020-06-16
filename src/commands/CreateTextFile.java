package commands;

import java.nio.file.FileAlreadyExistsException;
import java.util.List;
import java.util.Set;

import commands.exception.InvalidArgumentException;
import filesystem.TextFileController;
import path.Path;

public class CreateTextFile implements Command {
    private TextFileController fileSystem;
    private Path currentDirectory;

    public CreateTextFile(TextFileController fileSystem, Path currentDirectory) {
	this.fileSystem = fileSystem;
	this.currentDirectory = currentDirectory;
    }

    @Override
    public String execute(List<String> arguments, Set<String> options)
	    throws InvalidArgumentException, FileAlreadyExistsException {
	validateOptions(options);

	for (String argument : arguments) {
	    fileSystem.createTextFile(currentDirectory.getAbsolutePath(argument));
	}

	return "";
    }

    private void validateOptions(Set<String> options) throws InvalidArgumentException {
	if (!options.isEmpty()) {
	    throw new InvalidArgumentException(INVALID_OPTION_MESSAGE);
	}
    }
}
