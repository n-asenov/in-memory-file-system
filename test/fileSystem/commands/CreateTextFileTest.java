package fileSystem.commands;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.InvalidPathException;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fileSystem.Path;
import fileSystem.fs.FileSystem;
import fileSystem.fs.FilterBy;

public class CreateTextFileTest {

	@Test(expected = IllegalArgumentException.class)
	public void execute_CommandWithOption_ThrowIllegalArgumentException()
			throws InvalidPathException, FileAlreadyExistsException, IllegalArgumentException {
		CreateTextFile command = new CreateTextFile(new FileSystem(), new Path());

		List<String> options = new ArrayList<String>();
		options.add("-l");
		List<String> arguments = new ArrayList<String>();
		arguments.add("/home/f1");

		command.execute(options, arguments);
	}

	@Test(expected = FileAlreadyExistsException.class)
	public void execute_CreateAlreadyExistingFile_ThrowFileAlreadyExistsException()
			throws InvalidPathException, FileAlreadyExistsException, IllegalArgumentException {
		CreateTextFile command = new CreateTextFile(new FileSystem(), new Path());
		String fileName = "/home";

		List<String> options = new ArrayList<String>();
		List<String> arguments = new ArrayList<String>();
		arguments.add(fileName);

		command.execute(options, arguments);
	}

	@Test(expected = InvalidPathException.class)
	public void execute_CreateTextFileWithWrongAbsolutePath_ThrowInvalidPathException()
			throws InvalidPathException, FileAlreadyExistsException, IllegalArgumentException {
		CreateTextFile command = new CreateTextFile(new FileSystem(), new Path());
		String file = "/home/dir1/f1";

		List<String> options = new ArrayList<String>();
		List<String> arguments = new ArrayList<String>();
		arguments.add(file);

		command.execute(options, arguments);
	}

	@Test
	public void execute_CreateNewTextFile_TextFileAddedToFileSystem() throws InvalidPathException,
			FileAlreadyExistsException, IllegalArgumentException, NotDirectoryException, FileNotFoundException {
		FileSystem fs = new FileSystem();

		CreateTextFile command = new CreateTextFile(fs, new Path());
		String file = "/home/f1";

		List<String> options = new ArrayList<String>();
		List<String> arguments = new ArrayList<String>();
		arguments.add(file);

		command.execute(options, arguments);

		assertEquals("f1 ", fs.getDirectoryContent("/home", FilterBy.DEFAULT));
	}

	@Test
	public void execute_CreateTextFileWithRelativePath_TextFileAddedToFileSystem() throws InvalidPathException,
			FileAlreadyExistsException, IllegalArgumentException, NotDirectoryException, FileNotFoundException {
		FileSystem fs = new FileSystem();

		CreateTextFile command = new CreateTextFile(fs, new Path());

		List<String> options = new ArrayList<String>();
		List<String> arguments = new ArrayList<String>();
		arguments.add("f1");

		command.execute(options, arguments);

		assertEquals("f1 ", fs.getDirectoryContent("/home", FilterBy.DEFAULT));
	}

	@Test
	public void execute_CreateSeveralTextFiles_TextFilesAddedToFileSystem() throws InvalidPathException,
			FileAlreadyExistsException, IllegalArgumentException, NotDirectoryException, FileNotFoundException {
		FileSystem fs = new FileSystem();

		CreateTextFile command = new CreateTextFile(fs, new Path());

		List<String> options = new ArrayList<String>();
		List<String> arguments = new ArrayList<String>();
		arguments.add("f1");
		arguments.add("f2");
		arguments.add("f3");
		arguments.add("/f1");

		command.execute(options, arguments);

		assertEquals("f1 f2 f3 ", fs.getDirectoryContent("/home", FilterBy.DEFAULT));
		assertEquals("f1 home ", fs.getDirectoryContent("/", FilterBy.DEFAULT));
	}
}
