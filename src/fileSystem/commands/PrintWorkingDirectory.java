package fileSystem.commands;

import java.util.List;

import fileSystem.Path;
import fileSystem.fs.InvalidArgumentException;

public class PrintWorkingDirectory implements Command {
	private Path currentDirectory;
	
	public PrintWorkingDirectory(Path currentDirectory) {
		this.currentDirectory = currentDirectory;
	}
	
	@Override
	public String execute(List<String> options, List<String> arguments) throws InvalidArgumentException {
		validateOptions(options);
		validateArguments(arguments);
		
		return currentDirectory.getCurrentDirectory();
	}

	private void validateOptions(List<String> options) throws InvalidArgumentException {
		if (options.size() != 0) {
			throw new InvalidArgumentException("Invalid option");
		}
	}
	
	private void validateArguments(List<String> arguments) throws InvalidArgumentException {
		if (arguments.size() != 0) {
			throw new InvalidArgumentException("Invalid argument");
		}
	}
}
