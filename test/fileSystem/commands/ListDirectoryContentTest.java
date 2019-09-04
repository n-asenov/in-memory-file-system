package fileSystem.commands;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import fileSystem.Path;
import fileSystem.fs.FileSystem;
import fileSystem.output.ConsoleOutput;

public class ListDirectoryContentTest {
	private ListDirectoryContent command;
	private FileSystem fs;
	private List<String> options;
	private List<String> arguments;

	@Before
	public void init() throws FileAlreadyExistsException {
		fs = new FileSystem();		
		fs.makeDirectory("/home/dir1");
		fs.makeDirectory("/home/dir1/dir2");
		fs.makeDirectory("/home/dir1/dir3");
		command = new ListDirectoryContent(fs, new Path(), new ConsoleOutput());
		options = new ArrayList<String>();
		arguments = new ArrayList<String>();
	}

	@Test(expected = IllegalArgumentException.class)
	public void execute_CommandWithInvalidOptions_ThrowIllegalArgumentException()
			throws NotDirectoryException, IllegalArgumentException, FileNotFoundException {
		options.add("al");

		command.execute(options, arguments);
	}

	@Test
	public void execute_listEmptyDirectory_ReturnEmptyString()
			throws NotDirectoryException, IllegalArgumentException, FileNotFoundException {
		arguments.add("/home/dir1/dir2");

		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));

		command.execute(options, arguments);

		assertEquals("", outContent.toString());

		System.setOut(System.out);
	}

	@Test
	public void execute_listTwoDirectories_ReturnContetnOfDirectories()
			throws NotDirectoryException, IllegalArgumentException, FileNotFoundException {
		arguments.add("/");
		arguments.add("/home");

		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));

		command.execute(options, arguments);

		assertEquals("home " + System.lineSeparator() + "dir1 " + System.lineSeparator(), outContent.toString());

		System.setOut(System.out);
	}

	@Test
	public void execute_listDirectoryWithSortedDescOption_ReturnContentSortedDesc()
			throws NotDirectoryException, IllegalArgumentException, FileNotFoundException {
		options.add("-sortedDesc");
		arguments.add("/");

		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));

		command.execute(options, arguments);

		assertEquals("home " + System.lineSeparator(), outContent.toString());

		System.setOut(System.out);
	}

	@Test
	public void execute_listDirectoryWithRelativePath_ReturnContentOfDirectory()
			throws NotDirectoryException, IllegalArgumentException, FileNotFoundException {
		arguments.add("dir1");

		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));

		command.execute(options, arguments);

		assertEquals("dir2 dir3 " + System.lineSeparator(), outContent.toString());

		System.setOut(System.out);
	}

	@Test
	public void execute_listDirectoryWithNoArguments_ReturnContentOfCurrentDirectory()
			throws NotDirectoryException, IllegalArgumentException, FileNotFoundException {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));

		command.execute(options, arguments);

		assertEquals("dir1 " + System.lineSeparator(), outContent.toString());

		System.setOut(System.out);
	}
}
