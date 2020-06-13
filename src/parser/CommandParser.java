package parser;

import java.util.List;
import java.util.stream.Collectors;

public class CommandParser {
    public String getCommandName(final List<String> command) {
	if (command.isEmpty()) {
	    return null;
	}

	return command.get(0);
    }

    public List<String> getCommandOptions(final List<String> command) {
	return command.stream().skip(1).filter(argument -> argument.matches("-\\w+|--\\w+"))
		.collect(Collectors.toList());
    }

    public List<String> getCommandArguments(final List<String> command) {
	return command.stream().skip(1).filter(argument -> !argument.matches("-\\w+|--\\w+"))
		.collect(Collectors.toList());
    }
}
