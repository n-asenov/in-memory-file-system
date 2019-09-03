package fileSystem.commands;

import java.io.FileNotFoundException;
import java.nio.file.InvalidPathException;
import java.util.List;

import fileSystem.Path;
import fileSystem.fs.AbstractFileSystem;
import fileSystem.output.Output;

public class PrintTextFileContent implements Command {
	private AbstractFileSystem fs;
	private Path currentDirectory;
	private Output output;

	public PrintTextFileContent(AbstractFileSystem fs, Path currentDirectory, Output output) {
		this.fs = fs;
		this.currentDirectory = currentDirectory;
		this.output = output;
	}

	@Override
	public void execute(List<String> options, List<String> arguments)
			throws IllegalArgumentException, InvalidPathException, FileNotFoundException {
		if (options.size() != 0) {
			throw new IllegalArgumentException("Invalid option");
		}

		for (String file : arguments) {
			output.print(fs.getTextFileContent(currentDirectory.getAbsolutePath(file)));
		}
	}
}
