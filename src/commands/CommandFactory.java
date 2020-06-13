package commands;

import java.util.HashMap;
import java.util.Map;

import filesystem.AbstractFileSystem;
import filesystem.InvalidArgumentException;
import path.Path;

public class CommandFactory {
    private Map<String, Command> commands;

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
	commands.put("wc", new WordCount(fileSystem, currentDirectory));
    }

    public Command make(String command) throws InvalidArgumentException {
	Command result = commands.get(command);

	if (result == null) {
	    throw new InvalidArgumentException("Invalid command");
	}

	return result;
    }
}
