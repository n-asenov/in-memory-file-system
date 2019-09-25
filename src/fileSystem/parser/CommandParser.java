package fileSystem.parser;

import java.util.List;
import java.util.stream.Collectors;

public class CommandParser {
	public static String getCommandName(List<String> command) {
		if (command.isEmpty()) {
			return null;
		}
		
		return command.get(0);
	}
	
	public static List<String> getCommandOptions(List<String> command) {
		return command.stream().skip(1).filter(str -> str.matches("-\\w+|--\\w+")).collect(Collectors.toList());
	}
	
	public static List<String> getCommandArguments(List<String> command) {
		return command.stream().skip(1).filter(str -> !str.matches("-\\w+|--\\w+")).collect(Collectors.toList());
	}
}
