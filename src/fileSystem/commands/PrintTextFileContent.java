package fileSystem.commands;

import java.io.FileNotFoundException;
import java.nio.file.InvalidPathException;
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
	public String execute(List<String> options, List<String> arguments)
			throws IllegalArgumentException, InvalidPathException, FileNotFoundException {
		if (options.size() != 0) {
			throw new IllegalArgumentException("Invalid option");
		}

		StringBuilder result = new StringBuilder();
	
		for (String file : arguments) {
			result.append(fs.getTextFileContent(currentDirectory.getAbsolutePath(file)));
			result.append(System.lineSeparator());
		}
		
		result.delete(result.lastIndexOf(System.lineSeparator()), result.length());
		
		return result.toString();
	}
}
