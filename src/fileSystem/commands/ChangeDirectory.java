package fileSystem.commands;

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
	public String execute(List<String> options, List<String> arguments) {
		if (options.size() != 0) {
			return "Invalid option!";
		}

		int size = arguments.size();

		if (size == 0) {
			currentDirectory.setCurrentDirectory(null);
			return null;
		}

		if (size > 1) {
			return "Invalid number of arguments!";
		}

		String newCurrentDirectory = currentDirectory.getAbsolutePath(arguments.get(0));

		String result = fs.isDirectory(newCurrentDirectory);

		if (result == null) {
			currentDirectory.setCurrentDirectory(newCurrentDirectory);
		}

		return result;
	}
}
