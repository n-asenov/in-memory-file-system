package fileSystem.commands;

import java.nio.file.FileAlreadyExistsException;
import java.util.List;

import fileSystem.Path;
import fileSystem.fs.AbstractFileSystem;
import fileSystem.fs.InvalidArgumentException;

public class CreateTextFile implements Command {
	private AbstractFileSystem fs;
	private Path currentDirectory;

	public CreateTextFile(AbstractFileSystem fs, Path currentDirectory) {
		this.fs = fs;
		this.currentDirectory = currentDirectory;
	}

	@Override
	public String execute(List<String> options, List<String> arguments)
			throws FileAlreadyExistsException, InvalidArgumentException {
		validateOptions(options);
		
		for (String argument : arguments) {
			fs.createTextFile(currentDirectory.getAbsolutePath(argument));
		}
		
		return null;
	}
	
	private void validateOptions(List<String> options) throws InvalidArgumentException {
		if (options.size() != 0) {
			throw new InvalidArgumentException("Invalid option");
		}
	}
}
