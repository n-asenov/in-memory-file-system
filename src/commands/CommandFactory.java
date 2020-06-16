package commands;

import java.util.HashMap;
import java.util.Map;

import commands.exception.InvalidArgumentException;
import filesystem.VirtualFileSystem;
import path.Path;

public class CommandFactory {
    private Map<String, Command> commands;

    public CommandFactory(VirtualFileSystem fileSystem, Path currentDirectory) {
	commands = new HashMap<>();
	commands.put("cd", new ChangeDirectory(fileSystem, currentDirectory));
	commands.put("create_file", new CreateTextFile(fileSystem, currentDirectory));
	commands.put("ls", new ListDirectoryContent(fileSystem, currentDirectory));
	commands.put("mkdir", new MakeDirectory(fileSystem, currentDirectory));
	commands.put("cat", new GetTextFileContent(fileSystem, currentDirectory));
	commands.put("write", new WriteToTextFile(fileSystem, currentDirectory));
	commands.put("rm", new RemoveTextFile(fileSystem, currentDirectory));
	commands.put("remove", new RemoveContentFromTextFile(fileSystem, currentDirectory));
	commands.put("pwd", new PrintWorkingDirectory(currentDirectory));
	commands.put("wc", new WordCount(fileSystem, currentDirectory));
    }

    public Command getCommand(String commandName) throws InvalidArgumentException {
	Command command = commands.get(commandName);

	if (command == null) {
	    throw new InvalidArgumentException("Invalid command");
	}

	return command;
    }
}
