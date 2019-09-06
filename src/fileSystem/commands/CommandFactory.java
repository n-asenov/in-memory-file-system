package fileSystem.commands;

import fileSystem.Path;
import fileSystem.fs.AbstractFileSystem;

public class CommandFactory {
	private AbstractFileSystem fileSystem;
	private Path currentDirectory;

	public CommandFactory(AbstractFileSystem fileSystem, Path currentDirectory) {
		this.fileSystem = fileSystem;
		this.currentDirectory = currentDirectory;
	}

	public Command make(String command) {
		if (command.equals("cd")) {
			return new ChangeDirectory(fileSystem, currentDirectory);
		}

		if (command.equals("create_file")) {
			return new CreateTextFile(fileSystem, currentDirectory);
		}

		if (command.equals("ls")) {
			return new ListDirectoryContent(fileSystem, currentDirectory);
		}

		if (command.equals("mkdir")) {
			return new MakeDirectory(fileSystem, currentDirectory);
		}

		if (command.equals("cat")) {
			return new PrintTextFileContent(fileSystem, currentDirectory);
		}

		if (command.contentEquals("write")) {
			return new WriteToTextFile(fileSystem, currentDirectory);
		}

		return null;
	}
}
