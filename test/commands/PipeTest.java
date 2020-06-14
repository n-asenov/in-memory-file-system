package commands;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import filesystem.VirtualFileSystem;
import filesystem.exceptions.InvalidArgumentException;
import filesystem.exceptions.NotEnoughMemoryException;
import path.Path;

public class PipeTest {
    private VirtualFileSystem fileSystem;
    private Pipe command;
    private List<String> options;
    private List<String> arguments;

    @Before
    public void init() {
	fileSystem = new VirtualFileSystem();
	command = new Pipe(fileSystem, new Path());
	options = new ArrayList<String>();
	arguments = new ArrayList<String>();
    }

    @Test(expected = InvalidArgumentException.class)
    public void execute_InvalidOption_ThrowInvalidArgumentException()
	    throws InvalidArgumentException, NotEnoughMemoryException, IOException {
	options.add("-l");

	command.execute(arguments, options);
    }

    @Test(expected = InvalidArgumentException.class)
    public void execute_InvalidArgument_ThrowInvalidArgumentException()
	    throws InvalidArgumentException, NotEnoughMemoryException, IOException {
	arguments.add("|");

	command.execute(arguments, options);
    }

    @Test
    public void execute_NoPipe_ReturnResultOfCommand()
	    throws InvalidArgumentException, NotEnoughMemoryException, IOException {
	arguments.add("ls");
	arguments.add("/");

	assertEquals("home ", command.execute(arguments, options));
    }

    @Test
    public void execute_CommandLineWithPipe_ReturnResultOfLastCommand()
	    throws InvalidArgumentException, NotEnoughMemoryException, IOException {
	fileSystem.makeDirectory("/home/dir1");
	fileSystem.makeDirectory("/home/dir2");
	fileSystem.makeDirectory("/home/dir3");

	arguments.add("ls");
	arguments.add("|");
	arguments.add("wc");

	assertEquals("3", command.execute(arguments, options));
    }
}
