package fileSystem.commands;

import java.util.List;

import fileSystem.fs.FileSystem;
import fileSystem.fs.FilterBy;

public class ListDirectoryContent implements Command {
	private FileSystem fs;

	public ListDirectoryContent(FileSystem fs) {
		this.fs = fs;
	}

	@Override
	public String execute(List<String> options, List<String> arguments) {
		FilterBy flag = FilterBy.DEFAULT;

		for (String option : options) {
			if(!option.equals("sortedDesc")) {
				return "Invalid option!";
			}
			flag = FilterBy.SIZE_DESCENDING;
		}
		
		StringBuilder result = new StringBuilder();

		for (String directory : arguments) {
			result.append(fs.getDirectoryContent(directory, flag));
			result.append(System.lineSeparator());
		}

		return result.toString();
	}
}
