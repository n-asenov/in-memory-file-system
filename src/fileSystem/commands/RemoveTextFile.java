package fileSystem.commands;

import java.io.FileNotFoundException;
import java.util.List;

import fileSystem.Path;
import fileSystem.fs.AbstractFileSystem;
import fileSystem.fs.InvalidArgumentException;

public class RemoveTextFile implements Command {
	private AbstractFileSystem fileSystem;
	private Path currentDirectory;

	public RemoveTextFile(AbstractFileSystem fileSystem, Path currentDirectory) {
		this.fileSystem = fileSystem;
		this.currentDirectory = currentDirectory;
	}

	@Override
	public String execute(List<String> options, List<String> arguments)
			throws FileNotFoundException, InvalidArgumentException {
		validateOptions(options);

		for (String argument : arguments) {
			fileSystem.removeTextFile(currentDirectory.getAbsolutePath(argument));
		}

		return null;
	}

	private void validateOptions(List<String> options) throws InvalidArgumentException {
		if (options.size() != 0) {
			throw new InvalidArgumentException("Invalid option");
		}
	}
}
