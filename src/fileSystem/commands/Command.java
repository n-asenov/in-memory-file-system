package fileSystem.commands;

import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.InvalidPathException;
import java.nio.file.NotDirectoryException;
import java.util.List;

public interface Command {
	public void execute(List<String> options, List<String> arguments)
			throws NotDirectoryException, IllegalArgumentException, InvalidPathException, FileAlreadyExistsException, FileNotFoundException;
}
