package commands;

import java.io.IOException;
import java.util.List;

import filesystem.AbstractFileSystem;
import filesystem.InvalidArgumentException;
import path.Path;

public class CreateTextFile implements Command {
	private AbstractFileSystem fileSystem;
	private Path currentDirectory;

	public CreateTextFile(AbstractFileSystem fileSystem, Path currentDirectory) {
		this.fileSystem = fileSystem;
		this.currentDirectory = currentDirectory;
	}

	@Override
	public String execute(List<String> options, List<String> arguments)
			throws InvalidArgumentException, IOException {
		validateOptions(options);
		
		for (String argument : arguments) {
			fileSystem.createTextFile(currentDirectory.getAbsolutePath(argument));
		}
		
		return null;
	}
	
	private void validateOptions(List<String> options) throws InvalidArgumentException {
		if (options.size() != 0) {
			throw new InvalidArgumentException("Invalid option");
		}
	}
}