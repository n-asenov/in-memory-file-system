package fileSystem.commands;

import java.io.FileNotFoundException;
import java.util.List;

import fileSystem.Path;
import fileSystem.fs.AbstractFileSystem;
import fileSystem.fs.InvalidArgumentException;
import fileSystem.fs.NotEnoughMemoryException;

public class WriteToTextFile implements Command {
	private AbstractFileSystem fs;
	private Path currentDirectory;

	public WriteToTextFile(AbstractFileSystem fs, Path currentDirectory) {
		this.fs = fs;
		this.currentDirectory = currentDirectory;
	}

	@Override
	public String execute(List<String> options, List<String> arguments)
			throws FileNotFoundException, NotEnoughMemoryException, InvalidArgumentException {
		boolean overwrite = false;

		for (String option : options) {
			validateOption(option);
			overwrite = true;
		}

		validateArguments(arguments);

		String absolutePath = currentDirectory.getAbsolutePath(arguments.get(0));
		int line = getLine(arguments);
		String lineContent = getLineContent(arguments);

		fs.writeToTextFile(absolutePath, line, lineContent, overwrite);

		return null;
	}

	private void validateOption(String option) throws InvalidArgumentException {
		if (!option.equals("-overwrite")) {
			throw new InvalidArgumentException("Invalid option");
		}
	}

	private void validateArguments(List<String> arguments) throws InvalidArgumentException {
		if (arguments.size() < 3) {
			throw new InvalidArgumentException("Commmand expects more arguments");
		}
	}

	private int getLine(List<String> arguments) throws InvalidArgumentException {
		int line = 0;

		try {
			line = Integer.parseInt(arguments.get(1));
		} catch (NumberFormatException e) {
			throw new InvalidArgumentException("Second argument must be a integer");
		}

		return line;
	}

	private String getLineContent(List<String> arguments) {
		StringBuilder lineContent = new StringBuilder();
		int size = arguments.size() - 1;

		for (int i = 2; i < size; i++) {
			lineContent.append(arguments.get(i)).append(" ");
		}
		lineContent.append(arguments.get(size));

		return lineContent.toString();
	}
}
