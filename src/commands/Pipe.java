package commands;

import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import filesystem.VirtualFileSystem;
import filesystem.exceptions.InvalidArgumentException;
import filesystem.exceptions.NotEnoughMemoryException;
import parser.CommandParser;
import path.Path;

public class Pipe implements Command {
    private CommandFactory factory;
    private CommandParser parser;

    public Pipe(VirtualFileSystem fileSystem, Path currentDirectory) {
	factory = new CommandFactory(fileSystem, currentDirectory);
	parser = new CommandParser();
    }

    @Override
    public String execute(List<String> arguments, Set<String> options)
	    throws InvalidArgumentException, NotEnoughMemoryException, FileAlreadyExistsException, FileNotFoundException {
	validateOptions(options);
	validateArguments(arguments);

	List<String> command = new ArrayList<String>();
	String result = null;

	for (String argument : arguments) {
	    if (argument.equals("|")) {
		if (result != null && !result.equals("")) {
		    for (String arg : splitResult(result)) {
			command.add(arg);
		    }
		}

		result = factory.getCommand(parser.getCommandName(command)).execute(parser.getCommandArguments(command),
			parser.getCommandOptions(command));

		command.clear();
	    } else {
		command.add(argument);
	    }
	}

	if (result != null && !result.equals("")) {
	    for (String arg : splitResult(result)) {
		command.add(arg);
	    }
	}

	return factory.getCommand(parser.getCommandName(command)).execute(parser.getCommandArguments(command),
		parser.getCommandOptions(command));
    }

    private void validateArguments(List<String> arguments) throws InvalidArgumentException {
	if (arguments.get(0).equals("|") || arguments.get(arguments.size() - 1).equals("|")) {
	    throw new InvalidArgumentException("Invalid arguments");
	}
    }

    private void validateOptions(Set<String> options) throws InvalidArgumentException {
	if (!options.isEmpty()) {
	    throw new InvalidArgumentException("Invalid option");
	}
    }

    private List<String> splitResult(String result) {
	return Arrays.asList(result.split(" "));
    }
}
