package fileSystem.commands;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.List;

import fileSystem.fs.NotEnoughMemoryException;

public interface Command {
	public String execute(List<String> options, List<String> arguments)
			throws IllegalArgumentException, InvalidPathException, IOException, NotEnoughMemoryException;
}
