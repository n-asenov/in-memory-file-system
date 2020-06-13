package commands;

import java.io.IOException;
import java.util.List;

import filesystem.AbstractFileSystem;
import filesystem.exceptions.InvalidArgumentException;
import path.Path;

public class MakeDirectory implements Command {
    private AbstractFileSystem fileSystem;
    private Path currentDirectory;

    public MakeDirectory(AbstractFileSystem fileSystem, Path currentDirectory) {
	this.fileSystem = fileSystem;
	this.currentDirectory = currentDirectory;
    }

    @Override
    public String execute(List<String> options, List<String> arguments) throws InvalidArgumentException, IOException {
	validateOptions(options);

	for (String argument : arguments) {
	    fileSystem.makeDirectory(currentDirectory.getAbsolutePath(argument));
	}

	return null;
    }

    private void validateOptions(List<String> options) throws InvalidArgumentException {
	if (options.size() != 0) {
	    throw new InvalidArgumentException("Invalid option");
	}
    }
}
