package fileSystem.commands;

import java.io.FileNotFoundException;
import java.util.List;

import fileSystem.Path;
import fileSystem.fs.AbstractFileSystem;
import fileSystem.fs.InvalidArgumentException;

public class RemoveLineContentFromTextFile implements Command {
	private AbstractFileSystem fileSystem;
	private Path currentDirectory;

	public RemoveLineContentFromTextFile(AbstractFileSystem fileSystem, Path currentDirectory) {
		this.fileSystem = fileSystem;
		this.currentDirectory = currentDirectory;
	}

	@Override
	public String execute(List<String> options, List<String> arguments)
			throws FileNotFoundException, InvalidArgumentException {
		validateOptions(options);
		validateArguments(arguments);

		String[] interval = arguments.get(1).split("-");
		if (interval.length != 2) {
			throw new InvalidArgumentException("Wrong interval");
		}

		String file = currentDirectory.getAbsolutePath(arguments.get(0));
		int start = Integer.parseInt(interval[0]);
		int end = Integer.parseInt(interval[1]);

		fileSystem.removeContentFromLinesInTextFile(file, start, end);

		return null;
	}

	private void validateOptions(List<String> options) throws InvalidArgumentException {
		if (options.size() != 0) {
			throw new InvalidArgumentException("Invalid option");
		}
	}

	private void validateArguments(List<String> arguments) throws InvalidArgumentException {
		if (arguments.size() != 2) {
			throw new InvalidArgumentException("Command should receive 2 arguments");
		}
	}

}
