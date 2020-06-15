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
    private List<String> arguments;
    private Set<String> options;

    @Before
    public void init() {
	command = new PrintWorkingDirectory(new Path());
	arguments = new ArrayList<>();
	options = new HashSet<>();
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
	String expectedResult = "/home/";
	String actualResult = command.execute(arguments, options);
	assertEquals(expectedResult, actualResult);
    }
}
