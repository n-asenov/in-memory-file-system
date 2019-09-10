package fileSystem.commands;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import fileSystem.Path;
import fileSystem.fs.FileSystem;
import fileSystem.fs.InvalidArgumentException;

public class ListDirectoryContentTest {
	private ListDirectoryContent command;
	private FileSystem fs;
	private List<String> options;
	private List<String> arguments;

	@Before
	public void init() throws FileAlreadyExistsException, InvalidArgumentException {
		fs = new FileSystem();
		fs.makeDirectory("/home/dir1");
		fs.makeDirectory("/home/dir1/dir2");
		fs.makeDirectory("/home/dir1/dir3");
		command = new ListDirectoryContent(fs, new Path());
		options = new ArrayList<String>();
		arguments = new ArrayList<String>();
	}

	@Test(expected = InvalidArgumentException.class)
	public void execute_CommandWithInvalidOptions_ThrowIllegalArgumentException()
			throws NotDirectoryException, FileNotFoundException, InvalidArgumentException {
		options.add("al");

		command.execute(options, arguments);
	}

	@Test
	public void execute_listEmptyDirectory_ReturnEmptyString()
			throws NotDirectoryException, FileNotFoundException, InvalidArgumentException {
		arguments.add("/home/dir1/dir2");

		assertEquals("", command.execute(options, arguments));
	}

	@Test
	public void execute_listTwoDirectories_ReturnContetnOfDirectories()
			throws NotDirectoryException, FileNotFoundException, InvalidArgumentException {
		arguments.add("/");
		arguments.add("/home");

		assertEquals("home " + System.lineSeparator() + "dir1 ", command.execute(options, arguments));
	}

	@Test
	public void execute_listDirectoryWithSortedDescOption_ReturnContentSortedDesc()
			throws NotDirectoryException, FileNotFoundException, InvalidArgumentException {
		options.add("-sortedDesc");
		arguments.add("/");

		assertEquals("home ", command.execute(options, arguments));
	}

	@Test
	public void execute_listDirectoryWithRelativePath_ReturnContentOfDirectory()
			throws NotDirectoryException, FileNotFoundException, InvalidArgumentException {
		arguments.add("dir1");

		assertEquals("dir2 dir3 ", command.execute(options, arguments));
	}

	@Test
	public void execute_listDirectoryWithNoArguments_ReturnContentOfCurrentDirectory()
			throws NotDirectoryException, FileNotFoundException, InvalidArgumentException {
		assertEquals("dir1 ", command.execute(options, arguments));
	}
}
