package commands;

import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import filesystem.VirtualFileSystem;
import filesystem.exceptions.InvalidArgumentException;
import filesystem.exceptions.NotEnoughMemoryException;
import parser.CommandParser;
import path.Path;

public class Pipe implements Command {
    private static final String PIPE_COMMAND = "|";

    private CommandFactory factory;
    private CommandParser parser;

    public Pipe(VirtualFileSystem fileSystem, Path currentDirectory) {
	factory = new CommandFactory(fileSystem, currentDirectory);
	parser = new CommandParser();
    }

    @Override
    public String execute(List<String> arguments, Set<String> options) throws InvalidArgumentException,
	    FileAlreadyExistsException, FileNotFoundException, NotEnoughMemoryException {
	validateArguments(arguments);
	validateOptions(options);

	return executeCommands(arguments);
    }

    private void validateArguments(List<String> arguments) throws InvalidArgumentException {
	if (arguments.isEmpty()) {
	    throw new InvalidArgumentException(INVALID_ARGUMENT_MESSAGE);
	}

	boolean isFirstArgumentPipeCommand = arguments.get(0).equals(PIPE_COMMAND);
	boolean isLastArgumentPipeCommand = arguments.get(arguments.size() - 1).equals(PIPE_COMMAND);

	if (isFirstArgumentPipeCommand || isLastArgumentPipeCommand) {
	    throw new InvalidArgumentException(INVALID_ARGUMENT_MESSAGE);
	}
    }

    private void validateOptions(Set<String> options) throws InvalidArgumentException {
	if (!options.isEmpty()) {
	    throw new InvalidArgumentException(INVALID_OPTION_MESSAGE);
	}
    }

    private String executeCommands(List<String> arguments) throws InvalidArgumentException, FileAlreadyExistsException,
	    FileNotFoundException, NotEnoughMemoryException {
	List<String> currentCommand = new ArrayList<>();
	String lastCommandResult = "";

	for (String argument : arguments) {
	    if (!argument.equals(PIPE_COMMAND)) {
		currentCommand.add(argument);
	    } else {
		addLastCommandResultToCurrentCommand(currentCommand, lastCommandResult);
		lastCommandResult = executeCommand(currentCommand);
		currentCommand.clear();
	    }
	}

	addLastCommandResultToCurrentCommand(currentCommand, lastCommandResult);
	return executeCommand(currentCommand);
    }

    private void addLastCommandResultToCurrentCommand(List<String> currentCommand, String lastCommandResult) {
	if (!lastCommandResult.equals("")) {
	    String[] arguments = lastCommandResult.split(" ");
	    for (String argument : arguments) {
		currentCommand.add(argument);
	    }
	}
    }

    private String executeCommand(List<String> command) throws InvalidArgumentException, FileAlreadyExistsException,
	    FileNotFoundException, NotEnoughMemoryException {
	String commandName = parser.getCommandName(command);
	Command commandExecutor = factory.getCommand(commandName);
	List<String> commandArguments = parser.getCommandArguments(command);
	Set<String> commnadOptions = parser.getCommandOptions(command);

	return commandExecutor.execute(commandArguments, commnadOptions);
    }
}
