package commands;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import commands.MakeDirectory;
import filesystem.FilterBy;
import filesystem.InvalidArgumentException;
import filesystem.VirtualFileSystem;
import path.Path;

public class MakeDirectoryTest {
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

	command.execute(options, arguments);
    }

    @Test
    public void execute_NewDirectory_DirectoryAddedToFileSystem()
	    throws InvalidPathException, InvalidArgumentException, IOException {
	arguments.add("/home/dir1");

	command.execute(options, arguments);
	String[] expectedResult = { "dir1" };
	assertArrayEquals(expectedResult, fs.getDirectoryContent("/home", FilterBy.DEFAULT).toArray());
    }

    @Test
    public void execute_NewDirectoryWithRelativePath_DirectoryAddedToFileSystem()
	    throws InvalidArgumentException, IOException {
	arguments.add("dir1");

	command.execute(options, arguments);
	String[] expectedResult = { "dir1" };
	assertArrayEquals(expectedResult, fs.getDirectoryContent("/home", FilterBy.DEFAULT).toArray());
    }

    @Test(expected = FileAlreadyExistsException.class)
    public void execute_MakeExistingDirectory_ThrowFileAlreadyExistsException()
	    throws InvalidArgumentException, IOException {
	arguments.add("/home");

	command.execute(options, arguments);
    }

    @Test(expected = InvalidArgumentException.class)
    public void execute_MakeDirectoryWithNonExistentPath_ThrowInvalidPathException()
	    throws InvalidArgumentException, IOException {
	arguments.add("/home/dir1/dir2");

	command.execute(options, arguments);
    }
}
