package commands;

import java.io.IOException;
import java.util.List;

import filesystem.VirtualFileSystem;
import filesystem.exceptions.InvalidArgumentException;
import path.Path;

public class RemoveTextFile implements Command {
    private VirtualFileSystem fileSystem;
    private Path currentDirectory;

    public RemoveTextFile(VirtualFileSystem fileSystem, Path currentDirectory) {
	this.fileSystem = fileSystem;
	this.currentDirectory = currentDirectory;
    }

    @Override
    public String execute(List<String> arguments, List<String> options) throws InvalidArgumentException, IOException {
	validateOptions(options);

	for (String argument : arguments) {
	    fileSystem.removeTextFile(currentDirectory.getAbsolutePath(argument));
	}

	return null;
    }

    private void validateOptions(List<String> options) throws InvalidArgumentException {
	if (options.size() != 0) {
	    throw new InvalidArgumentException("Invalid option");
	}
    }
}
