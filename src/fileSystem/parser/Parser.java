package fileSystem.parser;

import java.util.List;

public interface Parser {
	abstract boolean hasNextLine();
	
	abstract List<String> getCommandLine();
	
	abstract void close();
}
