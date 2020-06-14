package commands;

import static org.junit.Assert.assertArrayEquals;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import filesystem.File;
import filesystem.VirtualFileSystem;
import filesystem.exceptions.InvalidArgumentException;
import path.Path;

public class MakeDirectoryTest {
    private static final Comparator<File> DEFAULT = (f1, f2) -> f1.getName().compareTo(f2.getName());
    
    private MakeDirectory command;
    private VirtualFileSystem fs;
    private List<String> options;
    private List<String> arguments;

    @Before
    public void init() {
	fs = new VirtualFileSystem();
	command = new MakeDirectory(fs, new Path());
	options = new ArrayList<String>();
	arguments = new ArrayList<String>();
    }

    @Test(expected = InvalidArgumentException.class)
    public void execute_MakeDirectoryWithOption_ThrowIllegalArgumentException()
	    throws InvalidArgumentException, IOException {
	options.add("-l");
	arguments.add("/home/dir1");

	command.execute(arguments, options);
    }

    @Test
    public void execute_NewDirectory_DirectoryAddedToFileSystem()
	    throws InvalidPathException, InvalidArgumentException, IOException {
	arguments.add("/home/dir1");

	command.execute(arguments, options);
	String[] expectedResult = { "dir1" };
	assertArrayEquals(expectedResult, fs.getDirectoryContent("/home", DEFAULT).toArray());
    }

    @Test
    public void execute_NewDirectoryWithRelativePath_DirectoryAddedToFileSystem()
	    throws InvalidArgumentException, IOException {
	arguments.add("dir1");

	command.execute(arguments, options);
	String[] expectedResult = { "dir1" };
	assertArrayEquals(expectedResult, fs.getDirectoryContent("/home", DEFAULT).toArray());
    }

    @Test(expected = FileAlreadyExistsException.class)
    public void execute_MakeExistingDirectory_ThrowFileAlreadyExistsException()
	    throws InvalidArgumentException, IOException {
	arguments.add("/home");

	command.execute(arguments, options);
    }

    @Test(expected = InvalidArgumentException.class)
    public void execute_MakeDirectoryWithNonExistentPath_ThrowInvalidPathException()
	    throws InvalidArgumentException, IOException {
	arguments.add("/home/dir1/dir2");

	command.execute(arguments, options);
    }
}
