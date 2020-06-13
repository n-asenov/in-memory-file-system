package commands;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import commands.ListDirectoryContent;
import filesystem.VirtualFileSystem;
import filesystem.exceptions.InvalidArgumentException;
import path.Path;

public class ListDirectoryContentTest {
    private ListDirectoryContent command;
    private VirtualFileSystem fs;
    private List<String> options;
    private List<String> arguments;

    @Before
    public void init() {
	fs = new VirtualFileSystem();
	try {
	    fs.makeDirectory("/home/dir1");
	    fs.makeDirectory("/home/dir1/dir2");
	    fs.makeDirectory("/home/dir1/dir3");
	} catch (FileAlreadyExistsException | InvalidArgumentException e) {
	    throw new IllegalArgumentException();
	}
	command = new ListDirectoryContent(fs, new Path());
	options = new ArrayList<String>();
	arguments = new ArrayList<String>();
    }

    @Test(expected = InvalidArgumentException.class)
    public void execute_CommandWithInvalidOptions_ThrowIllegalArgumentException()
	    throws InvalidArgumentException, IOException {
	options.add("al");

	command.execute(options, arguments);
    }

    @Test
    public void execute_listEmptyDirectory_ReturnEmptyString() throws InvalidArgumentException, IOException {
	arguments.add("/home/dir1/dir2");

	assertEquals("", command.execute(options, arguments));
    }

    @Test
    public void execute_listTwoDirectories_ReturnContetnOfDirectories() throws InvalidArgumentException, IOException {
	arguments.add("/");
	arguments.add("/home");

	assertEquals("home " + System.lineSeparator() + "dir1 ", command.execute(options, arguments));
    }

    @Test
    public void execute_listDirectoryWithSortedDescOption_ReturnContentSortedDesc()
	    throws InvalidArgumentException, IOException {
	options.add("-sortedDesc");
	arguments.add("/");

	assertEquals("home ", command.execute(options, arguments));
    }

    @Test
    public void execute_listDirectoryWithRelativePath_ReturnContentOfDirectory()
	    throws InvalidArgumentException, IOException {
	arguments.add("dir1");

	assertEquals("dir2 dir3 ", command.execute(options, arguments));
    }

    @Test
    public void execute_listDirectoryWithNoArguments_ReturnContentOfCurrentDirectory()
	    throws InvalidArgumentException, IOException {
	assertEquals("dir1 ", command.execute(options, arguments));
    }
}
