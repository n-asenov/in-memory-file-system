package fileSystem.commands;

import java.util.HashMap;

import fileSystem.Path;
import fileSystem.fs.AbstractFileSystem;

public class CommandFactory {
	private HashMap<String, Command> commands;

	public CommandFactory(AbstractFileSystem fileSystem, Path currentDirectory) {
		commands = new HashMap<String, Command>();
		commands.put("cd", new ChangeDirectory(fileSystem, currentDirectory));
		commands.put("create_file", new CreateTextFile(fileSystem, currentDirectory));
		commands.put("ls", new ListDirectoryContent(fileSystem, currentDirectory));
		commands.put("mkdir", new MakeDirectory(fileSystem, currentDirectory));
		commands.put("cat", new PrintTextFileContent(fileSystem, currentDirectory));
		commands.put("write", new WriteToTextFile(fileSystem, currentDirectory));
		commands.put("rm", new RemoveTextFile(fileSystem, currentDirectory));
		commands.put("remove", new RemoveLineContentFromTextFile(fileSystem, currentDirectory));
		commands.put("pwd", new PrintWorkingDirectory(currentDirectory));
	}

	public Command make(String command) {
		return commands.get(command);
	}
}
