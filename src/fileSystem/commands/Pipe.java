package fileSystem.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fileSystem.Path;
import fileSystem.fs.AbstractFileSystem;
import fileSystem.fs.InvalidArgumentException;
import fileSystem.fs.NotEnoughMemoryException;
import fileSystem.parser.CommandParser;

public class Pipe implements Command {
	private CommandFactory factory;

	public Pipe(AbstractFileSystem fileSystem, Path currentDirectory) {
		factory = new CommandFactory(fileSystem, currentDirectory);
	}

	@Override
	public String execute(List<String> options, List<String> arguments) throws InvalidArgumentException, NotEnoughMemoryException, IOException {
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

				result = factory.make(CommandParser.getCommandName(command)).execute(CommandParser.getCommandOptions(command),
						CommandParser.getCommandArguments(command));

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
		
		return factory.make(CommandParser.getCommandName(command)).execute(CommandParser.getCommandOptions(command),
				CommandParser.getCommandArguments(command));
	}

	private void validateArguments(List<String> arguments) throws InvalidArgumentException {
		if (arguments.get(0).equals("|") || arguments.get(arguments.size() - 1).equals("|")) {
			throw new InvalidArgumentException("Invalid arguments");
		}
	}

	private void validateOptions(List<String> options) throws InvalidArgumentException {
		if (!options.isEmpty()) {
			throw new InvalidArgumentException("Invalid option");
		}
	}
	
	private List<String> splitResult(String result) {
		return Arrays.asList(result.split(" "));
	}
}
