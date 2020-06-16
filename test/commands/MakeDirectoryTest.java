package commands;

import static org.junit.Assert.assertArrayEquals;

import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import commands.exception.InvalidArgumentException;
import filesystem.File;
import filesystem.VirtualFileSystem;
import path.Path;

public class MakeDirectoryTest {
    private static final Comparator<File> DEFAULT = (f1, f2) -> f1.getName().compareTo(f2.getName());

    private VirtualFileSystem fileSystem;
    private MakeDirectory command;
    private List<String> arguments;
    private Set<String> options;

    @Before
    public void init() {
	fileSystem = new VirtualFileSystem();
	command = new MakeDirectory(fileSystem, new Path());
	arguments = new ArrayList<>();
	options = new HashSet<>();
    }

    @Test(expected = InvalidArgumentException.class)
    public void execute_MakeDirectoryWithOption_ThrowIllegalArgumentException()
	    throws InvalidArgumentException, FileAlreadyExistsException {
	arguments.add("/home/dir1");
	options.add("-l");

	command.execute(arguments, options);
    }

    @Test
    public void execute_NewDirectory_DirectoryAddedToFileSystem()
	    throws InvalidArgumentException, FileAlreadyExistsException, FileNotFoundException {
	arguments.add("/home/dir1");

	command.execute(arguments, options);
	
	String[] expectedResult = { "dir1" };
	List<String> directoryContent = fileSystem.getDirectoryContent("/home", DEFAULT);
	String[] actualResult = directoryContent.toArray(new String[0]);
	assertArrayEquals(expectedResult, actualResult);
    }

    @Test
    public void execute_NewDirectoryWithRelativePath_DirectoryAddedToFileSystem()
	    throws InvalidArgumentException, FileAlreadyExistsException, FileNotFoundException {
	arguments.add("dir1");

	command.execute(arguments, options);
	
	String[] expectedResult = { "dir1" };
	List<String> directoryContent = fileSystem.getDirectoryContent("/home", DEFAULT);
	String[] actualResult = directoryContent.toArray(new String[0]);
	assertArrayEquals(expectedResult, actualResult);
    }

    @Test(expected = FileAlreadyExistsException.class)
    public void execute_MakeExistingDirectory_ThrowFileAlreadyExistsException()
	    throws InvalidArgumentException, FileAlreadyExistsException {
	arguments.add("/home");

	command.execute(arguments, options);
    }

    @Test(expected = InvalidArgumentException.class)
    public void execute_MakeDirectoryWithNonExistentPath_ThrowInvalidArgumentException()
	    throws InvalidArgumentException, FileAlreadyExistsException {
	arguments.add("/home/dir1/dir2");

	command.execute(arguments, options);
    }
}
