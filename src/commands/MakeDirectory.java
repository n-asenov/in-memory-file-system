package commands;

import java.nio.file.FileAlreadyExistsException;
import java.util.List;
import java.util.Set;

import filesystem.DirectoryController;
import filesystem.exceptions.InvalidArgumentException;
import path.Path;

public class MakeDirectory implements Command {
    private DirectoryController fileSystem;
    private Path currentDirectory;

    public MakeDirectory(DirectoryController fileSystem, Path currentDirectory) {
	this.fileSystem = fileSystem;
	this.currentDirectory = currentDirectory;
    }

    @Override
    public String execute(List<String> arguments, Set<String> options)
	    throws InvalidArgumentException, FileAlreadyExistsException {
	validateOptions(options);

	for (String argument : arguments) {
	    fileSystem.makeDirectory(currentDirectory.getAbsolutePath(argument));
	}

	return "";
    }

    private void validateOptions(Set<String> options) throws InvalidArgumentException {
	if (!options.isEmpty()) {
	    throw new InvalidArgumentException("Invalid option");
	}
    }
}
