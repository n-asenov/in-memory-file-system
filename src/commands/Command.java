package commands;

import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.util.List;
import java.util.Set;

import filesystem.exceptions.InvalidArgumentException;
import filesystem.exceptions.NotEnoughMemoryException;

public interface Command {
    public String execute(List<String> arguments, Set<String> options) throws InvalidArgumentException,
	    FileAlreadyExistsException, FileNotFoundException, NotEnoughMemoryException;
}
