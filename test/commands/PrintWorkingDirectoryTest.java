package commands;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import filesystem.exceptions.InvalidArgumentException;
import path.Path;

public class PrintWorkingDirectoryTest {
    private PrintWorkingDirectory command;
    private Set<String> options;
    private List<String> arguments;

    @Before
    public void init() {
	command = new PrintWorkingDirectory(new Path());
	options = new HashSet<String>();
	arguments = new ArrayList<String>();
    }

    @Test(expected = InvalidArgumentException.class)
    public void execute_InvalidOption_ThrowInvalidArgumentException() throws InvalidArgumentException {
	options.add("-c");

	command.execute(arguments, options);
    }

    @Test(expected = InvalidArgumentException.class)
    public void execute_InvalidArgument_ThrowInvalidArgumentException() throws InvalidArgumentException {
	arguments.add("/home");

	command.execute(arguments, options);
    }

    @Test
    public void execute_NoOptionsAndArguments_ReturnCurrentDirectory() throws InvalidArgumentException {
	assertEquals("/home/", command.execute(arguments, options));
    }

}
