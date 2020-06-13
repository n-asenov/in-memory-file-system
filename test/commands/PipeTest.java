package commands;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import commands.Pipe;
import filesystem.AbstractFileSystem;
import filesystem.InvalidArgumentException;
import filesystem.NotEnoughMemoryException;
import filesystem.VirtualFileSystem;
import path.Path;

public class PipeTest {
    private AbstractFileSystem fileSystem;
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

	command.execute(options, arguments);
    }

    @Test(expected = InvalidArgumentException.class)
    public void execute_InvalidArgument_ThrowInvalidArgumentException()
	    throws InvalidArgumentException, NotEnoughMemoryException, IOException {
	arguments.add("|");

	command.execute(options, arguments);
    }

    @Test
    public void execute_NoPipe_ReturnResultOfCommand()
	    throws InvalidArgumentException, NotEnoughMemoryException, IOException {
	arguments.add("ls");
	arguments.add("/");

	assertEquals("home ", command.execute(options, arguments));
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

	assertEquals("3", command.execute(options, arguments));
    }
}
