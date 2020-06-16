package parser;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CommandParser {
    private static final int COMMAND_NAME_POSITION = 1;
    private static final String OPTION_REGEX = "-\\w+|--\\w+";

    private static final String PIPE = "|";
    
    public String getCommandName(List<String> command) {
	if (command.isEmpty()) {
	    return "";
	}
	
	if (command.contains(PIPE)) {
	    return PIPE;
	}
	
	int commandNameIndex = COMMAND_NAME_POSITION - 1;
	return command.get(commandNameIndex);
    }

    public Set<String> getCommandOptions(List<String> command) {
	if (command.contains(PIPE) ) {
	    return new HashSet<>();
	}
	
	return command.stream()
		.skip(COMMAND_NAME_POSITION)
		.filter(argument -> argument.matches(OPTION_REGEX))
		.collect(Collectors.toSet());
    }

    public List<String> getCommandArguments(List<String> command) {
	if (command.contains(PIPE)) {
	    return command;
	}
	
	return command.stream()
		.skip(COMMAND_NAME_POSITION)
		.filter(argument -> !argument.matches(OPTION_REGEX))
		.collect(Collectors.toList());
    }
}
