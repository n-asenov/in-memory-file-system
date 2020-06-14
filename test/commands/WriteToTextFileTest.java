package commands;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
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

public class WriteToTextFileTest {
    private WriteToTextFile command;
    private VirtualFileSystem fs;
    private Set<String> options;
    private List<String> arguments;

    @Before
    public void init() {
	fs = new VirtualFileSystem();
	try {
	    fs.createTextFile("/home/f1");
	} catch (FileAlreadyExistsException | InvalidArgumentException e) {
	    throw new IllegalArgumentException();
	}

	command = new WriteToTextFile(fs, new Path());
	options = new HashSet<>();
	arguments = new ArrayList<String>();
    }

    @Test(expected = InvalidArgumentException.class)
    public void execute_CommandWithInvalidOption_ThrowIllegalArgumentException()
	    throws NotEnoughMemoryException, InvalidArgumentException, IOException {
	options.add("append");

	command.execute(arguments, options);
    }

    @Test(expected = InvalidArgumentException.class)
    public void execute_CommandWithInvalidNumberOfArguments_ThrowIllegalArgumentException()
	    throws NotEnoughMemoryException, InvalidArgumentException, IOException {
	arguments.add("/home/f1");

	command.execute(arguments, options);
    }

    @Test(expected = InvalidArgumentException.class)
    public void execute_CommnadWithWrongSecondArgument_ThrowIllegalArgumentException()
	    throws NotEnoughMemoryException, InvalidArgumentException, IOException {
	arguments.add("/home/f1");
	arguments.add("Hello");
	arguments.add("Hello, World!");

	command.execute(arguments, options);
    }

    @Test
    public void execute_ValidCommandWithNoOption_WriteContentToTextFile()
	    throws NotEnoughMemoryException, InvalidArgumentException, IOException {
	arguments.add("/home/f1");
	arguments.add("1");
	arguments.add("Hello, World!");

	command.execute(arguments, options);

	assertEquals("Hello, World!", fs.getTextFileContent("/home/f1"));
    }

    @Test
    public void execute_ValidCommandWithOverwriteOption_WriteContentToTextFile()
	    throws NotEnoughMemoryException, InvalidArgumentException, IOException {
	options.add("-overwrite");
	arguments.add("/home/f1");
	arguments.add("1");
	arguments.add("Hello, World!");

	command.execute(arguments, options);

	assertEquals("Hello, World!", fs.getTextFileContent("/home/f1"));
    }

    @Test
    public void execute_ValidCommandWithRelativePath_WriteContentToTextFile()
	    throws NotEnoughMemoryException, InvalidArgumentException, IOException {
	arguments.add("f1");
	arguments.add("1");
	arguments.add("Hello, World!");

	command.execute(arguments, options);

	assertEquals("Hello, World!", fs.getTextFileContent("/home/f1"));
    }
}
