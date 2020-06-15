package commands;

import java.util.List;
import java.util.Set;

import filesystem.exceptions.InvalidArgumentException;
import path.Path;

public class PrintWorkingDirectory implements Command {
    private Path currentDirectory;

    public PrintWorkingDirectory(Path currentDirectory) {
	this.currentDirectory = currentDirectory;
    }

    @Override
    public String execute(List<String> arguments, Set<String> options) throws InvalidArgumentException {
	validateArguments(arguments);
	validateOptions(options);

	return currentDirectory.getCurrentDirectory();
    }
    
    private void validateArguments(List<String> arguments) throws InvalidArgumentException {
	if (!arguments.isEmpty()) {
	    throw new InvalidArgumentException(INVALID_ARGUMENT_MESSAGE);
	}
    }

    private void validateOptions(Set<String> options) throws InvalidArgumentException {
	if (!options.isEmpty()) {
	    throw new InvalidArgumentException(INVALID_OPTION_MESSAGE);
	}
    }
}
