package commands;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import commands.PrintWorkingDirectory;
import filesystem.exceptions.InvalidArgumentException;
import path.Path;

public class PrintWorkingDirectoryTest {
    private PrintWorkingDirectory command;
    private List<String> options;
    private List<String> arguments;

    @Before
    public void init() {
	command = new PrintWorkingDirectory(new Path());
	options = new ArrayList<String>();
	arguments = new ArrayList<String>();
    }

    @Test(expected = InvalidArgumentException.class)
    public void execute_InvalidOption_ThrowInvalidArgumentException() throws InvalidArgumentException {
	options.add("-c");

	command.execute(options, arguments);
    }

    @Test(expected = InvalidArgumentException.class)
    public void execute_InvalidArgument_ThrowInvalidArgumentException() throws InvalidArgumentException {
	arguments.add("/home");

	command.execute(options, arguments);
    }

    @Test
    public void execute_NoOptionsAndArguments_ReturnCurrentDirectory() throws InvalidArgumentException {
	assertEquals("/home/", command.execute(options, arguments));
    }

}
