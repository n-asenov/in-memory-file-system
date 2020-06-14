package commands;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import filesystem.VirtualFileSystem;
import filesystem.exceptions.InvalidArgumentException;
import filesystem.exceptions.NotEnoughMemoryException;
import path.Path;

public class RemoveContentFromTextFileTest {
    private RemoveContentFromTextFile command;
    private VirtualFileSystem fileSystem;
    private Set<String> options;
    private List<String> arguments;

    @Before
    public void init() {
	fileSystem = new VirtualFileSystem();
	command = new RemoveContentFromTextFile(fileSystem, new Path());
	options = new HashSet<>();
	arguments = new ArrayList<String>();
    }

    @Test(expected = InvalidArgumentException.class)
    public void execute_InvalidOption_ThrowInvalidArgumentException() throws InvalidArgumentException, IOException {
	options.add("-invalid");

	command.execute(arguments, options);
    }

    @Test(expected = InvalidArgumentException.class)
    public void execute_TooMuchArguments_ThrowInvalidArgumentException() throws InvalidArgumentException, IOException {
	arguments.add("f1");
	arguments.add("1-5");
	arguments.add("6-10");

	command.execute(arguments, options);
    }

    @Test(expected = InvalidArgumentException.class)
    public void execute_NotEnoughArguments_ThrowInvalidArgumentException()
	    throws InvalidArgumentException, IOException {
	command.execute(arguments, options);
    }

    @Test(expected = InvalidArgumentException.class)
    public void execute_InvalidInterval_ThrowInvalidArgumentException() throws InvalidArgumentException, IOException {
	arguments.add("f1");
	arguments.add("1-5-10");

	command.execute(arguments, options);
    }

    @Test(expected = InvalidArgumentException.class)
    public void execute_WrongPathToTextFile_ThrowInvalidArgumentExcepiton()
	    throws InvalidArgumentException, IOException {
	arguments.add("/home/dir1/f1");
	arguments.add("1-5");

	command.execute(arguments, options);
    }

    @Test(expected = FileNotFoundException.class)
    public void execute_TextFileDoesNotExists_ThrowFileNotFoundException()
	    throws InvalidArgumentException, IOException {
	arguments.add("/home/f1");
	arguments.add("1-5");

	command.execute(arguments, options);
    }

    @Test(expected = FileNotFoundException.class)
    public void execute_FileIsDirectory_ThrowFileNotFoundException() throws InvalidArgumentException, IOException {
	arguments.add("/home");
	arguments.add("1-5");

	command.execute(arguments, options);
    }

    @Test(expected = InvalidArgumentException.class)
    public void execute_WrongInterval_ThrowInvalidArgumentException() throws InvalidArgumentException, IOException {
	arguments.add("f1");
	arguments.add("asdas-asdasd");

	command.execute(arguments, options);
    }

    @Test
    public void execute_TextFile_ClearTextFileContent()
	    throws InvalidArgumentException, NotEnoughMemoryException, IOException {
	String absolutePath = "/home/f1";
	fileSystem.createTextFile(absolutePath);

	int lines = 10;
	for (int i = 1; i <= lines; i++) {
	    fileSystem.writeToTextFile(absolutePath, i, "hello");
	}

	arguments.add(absolutePath);
	arguments.add("1-" + lines);

	command.execute(arguments, options);
	assertEquals("", fileSystem.getTextFileContent(absolutePath));
    }
}
