package fileSystem.commands;

import java.util.List;

public interface Command {
	public String execute(List<String> options, List<String> arguments);
}
