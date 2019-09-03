package fileSystem.commands;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fileSystem.Path;
import fileSystem.fs.FileSystem;
import fileSystem.output.ConsoleOutput;

public class ListDirectoryContentTest {

	@Test(expected = IllegalArgumentException.class)
	public void execute_CommandWithInvalidOptions_ThrowIllegalArgumentException()
			throws NotDirectoryException, IllegalArgumentException, FileNotFoundException, FileAlreadyExistsException {
		ListDirectoryContent ls = new ListDirectoryContent(new FileSystem(), new Path(), new ConsoleOutput());

		List<String> options = new ArrayList<String>();
		options.add("al");
		List<String> arguments = new ArrayList<String>();
		arguments.add("/home");

		ls.execute(options, arguments);
	}

	@Test
	public void execute_listEmptyDirectory_ReturnEmptyString()
			throws NotDirectoryException, IllegalArgumentException, FileNotFoundException, FileAlreadyExistsException {
		ListDirectoryContent ls = new ListDirectoryContent(new FileSystem(), new Path(), new ConsoleOutput());

		List<String> options = new ArrayList<String>();
		List<String> arguments = new ArrayList<String>();
		arguments.add("/home");

		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));

		ls.execute(options, arguments);

		assertEquals("", outContent.toString());

		System.setOut(System.out);
	}

	@Test
	public void execute_listTwoDirectories_ReturnContetnOfDirectories()
			throws NotDirectoryException, IllegalArgumentException, FileNotFoundException, FileAlreadyExistsException {
		ListDirectoryContent ls = new ListDirectoryContent(new FileSystem(), new Path(), new ConsoleOutput());

		List<String> options = new ArrayList<String>();
		List<String> arguments = new ArrayList<String>();
		arguments.add("/");
		arguments.add("/home");

		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));

		ls.execute(options, arguments);

		assertEquals("home " + System.lineSeparator() + System.lineSeparator(), outContent.toString());

		System.setOut(System.out);
	}

	@Test
	public void execute_listDirectoryWithSortedDescOption_ReturnContentSortedDesc()
			throws NotDirectoryException, IllegalArgumentException, FileNotFoundException, FileAlreadyExistsException {
		ListDirectoryContent ls = new ListDirectoryContent(new FileSystem(), new Path(), new ConsoleOutput());

		List<String> options = new ArrayList<String>();
		options.add("-sortedDesc");
		List<String> arguments = new ArrayList<String>();
		arguments.add("/");

		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));

		ls.execute(options, arguments);

		assertEquals("home " + System.lineSeparator(), outContent.toString());

		System.setOut(System.out);

	}

	@Test
	public void execute_listDirectoryWithRelativePath_ReturnContentOfDirectory()
			throws NotDirectoryException, IllegalArgumentException, FileNotFoundException, FileAlreadyExistsException {
		FileSystem fs = new FileSystem();
		fs.makeDirectory("/home/dir1");
		fs.makeDirectory("/home/dir1/dir2");
		fs.makeDirectory("/home/dir1/dir3");

		ListDirectoryContent ls = new ListDirectoryContent(fs, new Path(), new ConsoleOutput());

		List<String> options = new ArrayList<String>();
		List<String> arguments = new ArrayList<String>();
		arguments.add("dir1");

		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));

		ls.execute(options, arguments);

		assertEquals("dir2 dir3 " + System.lineSeparator(), outContent.toString());

		System.setOut(System.out);
	}

	@Test
	public void execute_listDirectoryWithNoArguments_ReturnContentOfCurrentDirectory()
			throws NotDirectoryException, IllegalArgumentException, FileNotFoundException, FileAlreadyExistsException {
		ListDirectoryContent ls = new ListDirectoryContent(new FileSystem(), new Path(), new ConsoleOutput());

		List<String> options = new ArrayList<String>();
		List<String> arguments = new ArrayList<String>();

		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));

		ls.execute(options, arguments);

		assertEquals("", outContent.toString());

		System.setOut(System.out);
	}
}
