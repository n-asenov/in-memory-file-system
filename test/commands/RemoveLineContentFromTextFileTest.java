package commands;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import commands.RemoveLineContentFromTextFile;
import filesystem.AbstractFileSystem;
import filesystem.InvalidArgumentException;
import filesystem.NotEnoughMemoryException;
import filesystem.VirtualFileSystem;
import path.Path;

public class RemoveLineContentFromTextFileTest {
    private RemoveLineContentFromTextFile command;
    private AbstractFileSystem fileSystem;
    private List<String> options;
    private List<String> arguments;

    @Before
    public void init() {
	fileSystem = new VirtualFileSystem();
	command = new RemoveLineContentFromTextFile(fileSystem, new Path());
	options = new ArrayList<String>();
	arguments = new ArrayList<String>();
    }

    @Test(expected = InvalidArgumentException.class)
    public void execute_InvalidOption_ThrowInvalidArgumentException() throws InvalidArgumentException, IOException {
	options.add("-invalid");

	command.execute(options, arguments);
    }

    @Test(expected = InvalidArgumentException.class)
    public void execute_TooMuchArguments_ThrowInvalidArgumentException() throws InvalidArgumentException, IOException {
	arguments.add("f1");
	arguments.add("1-5");
	arguments.add("6-10");

	command.execute(options, arguments);
    }

    @Test(expected = InvalidArgumentException.class)
    public void execute_NotEnoughArguments_ThrowInvalidArgumentException()
	    throws InvalidArgumentException, IOException {
	command.execute(options, arguments);
    }

    @Test(expected = InvalidArgumentException.class)
    public void execute_InvalidInterval_ThrowInvalidArgumentException() throws InvalidArgumentException, IOException {
	arguments.add("f1");
	arguments.add("1-5-10");

	command.execute(options, arguments);
    }

    @Test(expected = InvalidArgumentException.class)
    public void execute_WrongPathToTextFile_ThrowInvalidArgumentExcepiton()
	    throws InvalidArgumentException, IOException {
	arguments.add("/home/dir1/f1");
	arguments.add("1-5");

	command.execute(options, arguments);
    }

    @Test(expected = FileNotFoundException.class)
    public void execute_TextFileDoesNotExists_ThrowFileNotFoundException()
	    throws InvalidArgumentException, IOException {
	arguments.add("/home/f1");
	arguments.add("1-5");

	command.execute(options, arguments);
    }

    @Test(expected = FileNotFoundException.class)
    public void execute_FileIsDirectory_ThrowFileNotFoundException() throws InvalidArgumentException, IOException {
	arguments.add("/home");
	arguments.add("1-5");

	command.execute(options, arguments);
    }

    @Test(expected = InvalidArgumentException.class)
    public void execute_WrongInterval_ThrowInvalidArgumentException() throws InvalidArgumentException, IOException {
	arguments.add("f1");
	arguments.add("asdas-asdasd");

	command.execute(options, arguments);
    }

    @Test
    public void execute_TextFile_ClearTextFileContent()
	    throws InvalidArgumentException, NotEnoughMemoryException, IOException {
	String absolutePath = "/home/f1";
	fileSystem.createTextFile(absolutePath);

	int lines = 10;
	for (int i = 1; i <= lines; i++) {
	    fileSystem.writeToTextFile(absolutePath, i, "hello", false);
	}

	arguments.add(absolutePath);
	arguments.add("1-" + lines);

	assertNull(command.execute(options, arguments));
	assertEquals("", fileSystem.getTextFileContent(absolutePath));
    }
}
