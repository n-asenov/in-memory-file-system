package fileSystem.commands;

import java.io.FileNotFoundException;
import java.nio.file.InvalidPathException;
import java.nio.file.NotDirectoryException;
import java.util.List;

import fileSystem.Path;
import fileSystem.fs.AbstractFileSystem;

public class ChangeDirectory implements Command {
	private AbstractFileSystem fs;
	private Path currentDirectory;

	public ChangeDirectory(AbstractFileSystem fs, Path currentDirectory) {
		this.fs = fs;
		this.currentDirectory = currentDirectory;
	}

	@Override
	public String execute(List<String> options, List<String> arguments)
			throws IllegalArgumentException, InvalidPathException, FileNotFoundException, NotDirectoryException {
		validateOptions(options);
		validateArguments(arguments);

		String newCurrentDirectory = null;

		if (arguments.size() != 0) {
			newCurrentDirectory = currentDirectory.getAbsolutePath(arguments.get(0));
			validateNewCurrentDirectory(newCurrentDirectory);
		}

		currentDirectory.setCurrentDirectory(newCurrentDirectory);

		return null;
	}

	private void validateOptions(List<String> options) throws IllegalArgumentException {
		if (options.size() != 0) {
			throw new IllegalArgumentException("Invalid option");
		}
	}

	private void validateArguments(List<String> arguments) throws IllegalArgumentException {
		if (arguments.size() > 1) {
			throw new IllegalArgumentException("Invalid number of arguments");
		}
	}

	private void validateNewCurrentDirectory(String newCurrentDirectory)
			throws NotDirectoryException, InvalidPathException, FileNotFoundException {
		if (!fs.isDirectory(newCurrentDirectory)) {
			throw new NotDirectoryException("File is not a directory");
		}
	}
}
