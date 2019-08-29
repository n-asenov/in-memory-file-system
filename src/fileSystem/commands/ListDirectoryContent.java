package fileSystem.commands;

import java.util.List;

import fileSystem.fs.AbstractFileSystem;
import fileSystem.fs.FilterBy;

public class ListDirectoryContent implements Command {
	private AbstractFileSystem fs;

	public ListDirectoryContent(AbstractFileSystem fs) {
		this.fs = fs;
	}

	@Override
	public String execute(List<String> options, List<String> arguments) {
		FilterBy flag = FilterBy.DEFAULT;

		for (String option : options) {
			if (!option.equals("-sortedDesc")) {
				return "Invalid option!";
			}
			flag = FilterBy.SIZE_DESCENDING;
		}

		StringBuilder result = new StringBuilder();
		int size = arguments.size() - 1;
		
		for (int i = 0; i < size; i++) {
			result.append(fs.getDirectoryContent(arguments.get(i), flag));
			result.append(System.lineSeparator());
		}

		result.append(fs.getDirectoryContent(arguments.get(size), flag));

		return result.toString();
	}
}
