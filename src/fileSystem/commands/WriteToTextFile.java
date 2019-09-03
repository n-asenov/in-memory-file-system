package fileSystem.commands;

import java.io.FileNotFoundException;
import java.nio.file.InvalidPathException;
import java.util.List;

import fileSystem.Path;
import fileSystem.fs.AbstractFileSystem;

public class WriteToTextFile implements Command {
	private AbstractFileSystem fs;
	private Path currentDirectory;

	public WriteToTextFile(AbstractFileSystem fs, Path currentDirectory) {
		this.fs = fs;
		this.currentDirectory = currentDirectory;
	}

	@Override
	public void execute(List<String> options, List<String> arguments)
			throws IllegalArgumentException, InvalidPathException, FileNotFoundException {
		boolean overwrite = false;

		for (String option : options) {
			if (!option.equals("-overwrite")) {
				throw new IllegalArgumentException("Invalid option");
			}
			overwrite = true;
		}

		if (arguments.size() < 3) {
			throw new IllegalArgumentException("Commmand expects more arguments");
		}

		if (!isNumber(arguments.get(1))) {
			throw new IllegalArgumentException("Second argument must be a number");
		}

		String absolutePath = currentDirectory.getAbsolutePath(arguments.get(0));
		int line = Integer.parseInt(arguments.get(1));
		StringBuilder lineContent = new StringBuilder();

		for (int i = 2; i < arguments.size(); i++) {
			lineContent.append(arguments.get(i));
			if (i != arguments.size() - 1) {
				lineContent.append(" ");
			}

		}

		fs.writeToTextFile(absolutePath, line, lineContent.toString(), overwrite);
	}

	private boolean isNumber(String number) {
		return number.matches("\\d*");
	}
}
