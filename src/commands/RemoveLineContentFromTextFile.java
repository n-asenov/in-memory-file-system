package commands;

import java.io.IOException;
import java.util.List;

import filesystem.VirtualFileSystem;
import filesystem.exceptions.InvalidArgumentException;
import path.Path;

public class RemoveLineContentFromTextFile implements Command {
    private VirtualFileSystem fileSystem;
    private Path currentDirectory;

    public RemoveLineContentFromTextFile(VirtualFileSystem fileSystem, Path currentDirectory) {
	this.fileSystem = fileSystem;
	this.currentDirectory = currentDirectory;
    }

    @Override
    public String execute(List<String> arguments, List<String> options) throws InvalidArgumentException, IOException {
	validateOptions(options);
	validateArguments(arguments);

	int[] interval = validateInterval(arguments.get(1).split("-"));

	String file = currentDirectory.getAbsolutePath(arguments.get(0));

	fileSystem.removeContentFromLinesInTextFile(file, interval[0], interval[1]);

	return null;
    }

    private void validateOptions(List<String> options) throws InvalidArgumentException {
	if (options.size() != 0) {
	    throw new InvalidArgumentException("Invalid option");
	}
    }

    private int[] validateInterval(String[] interval) throws InvalidArgumentException {
	if (interval.length != 2) {
	    throw new InvalidArgumentException("Wrong interval");
	}

	int[] result = new int[2];

	try {
	    result[0] = Integer.parseInt(interval[0]);
	    result[1] = Integer.parseInt(interval[1]);
	} catch (NumberFormatException e) {
	    throw new InvalidArgumentException("Interval must have 2 integers");
	}

	return result;
    }

    private void validateArguments(List<String> arguments) throws InvalidArgumentException {
	if (arguments.size() != 2) {
	    throw new InvalidArgumentException("Command should receive 2 arguments");
	}
    }

}
