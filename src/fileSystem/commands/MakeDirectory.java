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
	public void execute(List<String> options, List<String> arguments)
			throws InvalidPathException, FileAlreadyExistsException, IllegalArgumentException {
		if (options.size() != 0) {
			throw new IllegalArgumentException("Invalid option");
		}

		for (String argument : arguments) {
			fs.makeDirectory(currentDirectory.getAbsolutePath(argument));	
		}
	}
}		
