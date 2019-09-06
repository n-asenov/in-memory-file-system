package fileSystem.commands;

import java.nio.file.FileAlreadyExistsException;
import java.nio.file.InvalidPathException;
import java.util.List;

import fileSystem.Path;
import fileSystem.fs.AbstractFileSystem;

public class MakeDirectory implements Command {
	private AbstractFileSystem fs;
	private Path currentDirectory;
	
	public MakeDirectory(AbstractFileSystem fs, Path currentDirectory) {
		this.fs = fs;
		this.currentDirectory = currentDirectory;
	}

	@Override
	public String execute(List<String> options, List<String> arguments)
			throws InvalidPathException, FileAlreadyExistsException, IllegalArgumentException {
		validateOptions(options);

		for (String argument : arguments) {
			fs.makeDirectory(currentDirectory.getAbsolutePath(argument));	
		}
		
		return null;
	}
	
	private void validateOptions(List<String> options) throws IllegalArgumentException {
		if (options.size() != 0) {
			throw new IllegalArgumentException("Invalid option");
		}
	}
}		
