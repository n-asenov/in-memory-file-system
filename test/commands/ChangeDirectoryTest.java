package commands;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import filesystem.VirtualFileSystem;
import filesystem.exceptions.InvalidArgumentException;
import path.Path;

public class ChangeDirectoryTest {
    private VirtualFileSystem fileSystem;
    private Path path;
    private ChangeDirectory command;
    private List<String> arguments;
    private Set<String> options;

    @Before
    public void init() {
	fileSystem = new VirtualFileSystem();
	path = new Path();
	command = new ChangeDirectory(fileSystem, path);
	arguments = new ArrayList<>();
	options = new HashSet<>();
    }

    @Test(expected = InvalidArgumentException.class)
    public void execute_InvalidOption_ThrowInvalidArgumentException()
	    throws InvalidArgumentException, FileNotFoundException {
	options.add("-a");

	command.execute(arguments, options);
    }

    @Test(expected = InvalidArgumentException.class)
    public void execute_InvalidArguments_ThrowInvalidArgumentException()
	    throws InvalidArgumentException, FileNotFoundException {
	arguments.add("/home");
	arguments.add("/dir1");

	command.execute(arguments, options);
    }

    @Test(expected = FileNotFoundException.class)
    public void execute_ChangeDirectoryToNonExistentDirectory_ThrowFileNotFoundExcepiton()
	    throws InvalidArgumentException, FileNotFoundException {
	arguments.add("/home/dir1");

	command.execute(arguments, options);
    }

    @Test(expected = FileNotFoundException.class)
    public void execute_ChangeDirectoryToTextFile_ThrowNotDirectoryException()
	    throws InvalidArgumentException, FileNotFoundException, FileAlreadyExistsException {
	fileSystem.createTextFile("/home/f1");

	arguments.add("f1");

	command.execute(arguments, options);
    }

    @Test
    public void execute_RelativePathToGoBackToParentDirectory_ReturnToParentDirectory()
	    throws InvalidArgumentException, FileNotFoundException {
	arguments.add("..");

	command.execute(arguments, options);

	assertEquals("We should go back to root directory from home directory", "/", path.getCurrentDirectory());
    }

    @Test
    public void execute_ChangeDirectoryToCurrentDirectory_CurrentDirectoryDoesntChange()
	    throws InvalidArgumentException, FileNotFoundException {
	arguments.add(".");

	command.execute(arguments, options);

	assertEquals("/home/", path.getCurrentDirectory());
    }

    @Test
    public void execute_ChangeDirectoryWithNoArguments_ReturnToHomeDirectory()
	    throws FileNotFoundException, NotDirectoryException, InvalidArgumentException {
	path.setCurrentDirectory("/");

	command.execute(arguments, options);

	assertEquals("/home/", path.getCurrentDirectory());
    }
}
