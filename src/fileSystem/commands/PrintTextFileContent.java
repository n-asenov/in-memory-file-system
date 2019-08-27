package fileSystem.commands;

import java.util.List;

import fileSystem.fs.FileSystem;

public class PrintTextFileContent implements Command {
	private FileSystem fs;

	public PrintTextFileContent(FileSystem fs) {
		this.fs = fs;
	}

	@Override
	public String execute(List<String> options, List<String> arguments) {
		if (options.size() != 0) {
			return "Invalid option";
		}

		StringBuilder result = new StringBuilder();

		for (String file : arguments) {
			result.append(fs.getTextFileContent(file));
		}

		return result.toString();
	}
}
