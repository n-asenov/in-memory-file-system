package commands;

import java.io.IOException;
import java.util.List;

import filesystem.InvalidArgumentException;
import filesystem.NotEnoughMemoryException;

public interface Command {
    public String execute(List<String> options, List<String> arguments)
	    throws InvalidArgumentException, IOException, NotEnoughMemoryException;
}
