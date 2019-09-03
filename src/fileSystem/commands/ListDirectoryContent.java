package fileSystem.commands;

import java.io.FileNotFoundException;
import java.nio.file.NotDirectoryException;
import java.util.List;

import fileSystem.Path;
import fileSystem.fs.AbstractFileSystem;
import fileSystem.fs.FilterBy;
import fileSystem.output.Output;

public class ListDirectoryContent implements Command {
	private AbstractFileSystem fs;
	private Path currentDirectory;
	private Output output;

	public ListDirectoryContent(AbstractFileSystem fs, Path currentDirectory, Output output) {
		this.fs = fs;
		this.currentDirectory = currentDirectory;
		this.output = output;
	}

	@Override
	public void execute(List<String> options, List<String> arguments)
			throws IllegalArgumentException, NotDirectoryException, FileNotFoundException {
		FilterBy flag = FilterBy.DEFAULT;

		for (String option : options) {
			if (!option.equals("-sortedDesc")) {
				throw new IllegalArgumentException("Invalid option");
			}
			flag = FilterBy.SIZE_DESCENDING;
		}

		int size = arguments.size();

		if (size == 0) {
			output.print(fs.getDirectoryContent(currentDirectory.getCurrentDirectory(), flag));
			return;
		}

		StringBuilder result = new StringBuilder();

		for (int i = 0; i < size - 1; i++) {
			result.append(fs.getDirectoryContent(currentDirectory.getAbsolutePath(arguments.get(i)), flag));
			result.append(System.lineSeparator());
		}

		result.append(fs.getDirectoryContent(currentDirectory.getAbsolutePath(arguments.get(size - 1)), flag));

		output.print(result.toString());
	}
}
