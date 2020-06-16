package commands;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import commands.exception.InvalidArgumentException;
import filesystem.VirtualFileSystem;
import filesystem.exceptions.NotEnoughMemoryException;
import path.Path;

public class PipeTest {
    private VirtualFileSystem fileSystem;
    private Pipe command;
    private List<String> arguments;
    private Set<String> options;

    @Before
    public void init() {
	fileSystem = new VirtualFileSystem();
	command = new Pipe(fileSystem, new Path());
	options = new HashSet<>();
	arguments = new ArrayList<>();
    }

    @Test(expected = InvalidArgumentException.class)
    public void execute_InvalidOption_ThrowInvalidArgumentException() throws InvalidArgumentException,
	    FileAlreadyExistsException, FileNotFoundException, NotEnoughMemoryException {
	arguments.add("command");
	options.add("-l");

	command.execute(arguments, options);
    }

    @Test(expected = InvalidArgumentException.class)
    public void execute_InvalidArgument_ThrowInvalidArgumentException() throws InvalidArgumentException,
	    NotEnoughMemoryException, FileAlreadyExistsException, FileNotFoundException {
	arguments.add("|");

	command.execute(arguments, options);
    }

    @Test
    public void execute_NoPipe_ReturnResultOfCommand() throws InvalidArgumentException, NotEnoughMemoryException,
	    FileAlreadyExistsException, FileNotFoundException {
	arguments.add("ls");
	arguments.add("/");

	assertEquals("home ", command.execute(arguments, options));
    }

    @Test
    public void execute_CommandLineWithPipe_ReturnResultOfLastCommand() throws InvalidArgumentException,
	    NotEnoughMemoryException, FileAlreadyExistsException, FileNotFoundException {
	fileSystem.makeDirectory("/home/dir1");
	fileSystem.makeDirectory("/home/dir2");
	fileSystem.makeDirectory("/home/dir3");

	arguments.add("ls");
	arguments.add("|");
	arguments.add("wc");

	assertEquals("3", command.execute(arguments, options));
    }
}
