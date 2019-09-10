package fileSystem.commands;

import java.io.IOException;
import java.util.List;

import fileSystem.fs.InvalidArgumentException;
import fileSystem.fs.NotEnoughMemoryException;

public interface Command {
	public String execute(List<String> options, List<String> arguments)
			throws InvalidArgumentException, IOException, NotEnoughMemoryException;
}
