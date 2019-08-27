package fileSystem.parser;

import java.util.List;

public interface Parser {
	abstract boolean hasNextLine();
	
	abstract String getCommand(List<String> options, List<String> arguments);
	
	abstract void close();
}
