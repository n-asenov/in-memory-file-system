package fileSystem.commands;

import java.util.List;

import fileSystem.Path;
import fileSystem.fs.AbstractFileSystem;

public class PrintTextFileContent implements Command {
	private AbstractFileSystem fs;
	private Path currentDirectory;

	public PrintTextFileContent(AbstractFileSystem fs, Path currentDirectory) {
		this.fs = fs;
		this.currentDirectory = currentDirectory;
	}

	@Override
	public String execute(List<String> options, List<String> arguments) {
		if (options.size() != 0) {
			return "Invalid option!";
		}

		StringBuilder result = new StringBuilder();

		for (String file : arguments) {
			result.append(fs.getTextFileContent(currentDirectory.getAbsolutePath(file)));
		}

		return result.toString();
	}
}
