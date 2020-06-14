package parser;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CommandParser {
    private static final int COMMAND_NAME_INDEX = 1;
    private static final String OPTION_REGEX = "-\\w+|--\\w+";

    public String getCommandName(List<String> command) {
	if (command.isEmpty()) {
	    return "";
	}

	return command.get(0);
    }

    public Set<String> getCommandOptions(List<String> command) {
	return command.stream()
		.skip(COMMAND_NAME_INDEX)
		.filter(argument -> argument.matches(OPTION_REGEX))
		.collect(Collectors.toSet());
    }

    public List<String> getCommandArguments(List<String> command) {
	return command.stream()
		.skip(COMMAND_NAME_INDEX)
		.filter(argument -> !argument.matches(OPTION_REGEX))
		.collect(Collectors.toList());
    }
}
