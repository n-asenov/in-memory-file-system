package fileSystem.commands;

import java.util.List;

public interface Command {
	public void execute(List<String> options, List<String> arguments);
}
