package commands;

import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.util.List;
import java.util.Set;

import commands.exception.InvalidArgumentException;
import filesystem.exceptions.NotEnoughMemoryException;

public interface Command {
    public static final String INVALID_ARGUMENT_MESSAGE = "Invalid argument";
    public static final String INVALID_OPTION_MESSAGE = "Invalid option";
    
    public String execute(List<String> arguments, Set<String> options) throws InvalidArgumentException,
	    FileAlreadyExistsException, FileNotFoundException, NotEnoughMemoryException;
}
