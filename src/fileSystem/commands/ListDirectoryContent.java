package fileSystem.commands;

import java.io.IOException;
import java.util.List;

import fileSystem.Path;
import fileSystem.fs.AbstractFileSystem;
import fileSystem.fs.FilterBy;
import fileSystem.fs.InvalidArgumentException;

public class ListDirectoryContent implements Command {
	private AbstractFileSystem fs;
	private Path currentDirectory;

	public ListDirectoryContent(AbstractFileSystem fs, Path currentDirectory) {
		this.fs = fs;
		this.currentDirectory = currentDirectory;
	}

	@Override
	public String execute(List<String> options, List<String> arguments)
			throws InvalidArgumentException, IOException {
		FilterBy flag = validateOptions(options);

		int size = arguments.size();

		if (size == 0) {
			return appendDirectoryContent(fs.getDirectoryContent(currentDirectory.getCurrentDirectory(), flag));
		}

		StringBuilder result = new StringBuilder();
		for (int i = 0; i < size - 1; i++) {
			result.append(appendDirectoryContent(
					fs.getDirectoryContent(currentDirectory.getAbsolutePath(arguments.get(i)), flag)));
			result.append(System.lineSeparator());
		}
		result.append(appendDirectoryContent(
				fs.getDirectoryContent(currentDirectory.getAbsolutePath(arguments.get(size - 1)), flag)));

		return result.toString();
	}

	private FilterBy validateOptions(List<String> options) throws InvalidArgumentException {
		FilterBy flag = FilterBy.DEFAULT;

		for (String option : options) {
			if (!option.equals("-sortedDesc")) {
				throw new InvalidArgumentException("Invalid option");
			}
			flag = FilterBy.SIZE_DESCENDING;
		}

		return flag;
	}

	private String appendDirectoryContent(List<String> content) {
		StringBuilder result = new StringBuilder();

		for (String fileName : content) {
			result.append(fileName);
			result.append(" ");
		}

		return result.toString();
	}
}
