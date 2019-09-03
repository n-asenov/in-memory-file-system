package fileSystem.commands;

import java.io.FileNotFoundException;
import java.nio.file.InvalidPathException;
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
	public void execute(List<String> options, List<String> arguments)
			throws IllegalArgumentException, InvalidPathException, FileNotFoundException {
		if (options.size() != 0) {
			throw new IllegalArgumentException("Invalid option");
		}

		int size = arguments.size();

		if (size == 0) {
			currentDirectory.setCurrentDirectory(null);
			return;
		}

		if (size > 1) {
			throw new IllegalArgumentException("Invalid number of arguments");
		}

		String newCurrentDirectory = currentDirectory.getAbsolutePath(arguments.get(0));

		if (fs.isDirectory(newCurrentDirectory)) {
			currentDirectory.setCurrentDirectory(newCurrentDirectory);
		}
	}
}
