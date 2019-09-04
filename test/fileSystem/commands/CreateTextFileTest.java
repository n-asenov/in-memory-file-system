package fileSystem.commands;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.InvalidPathException;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import fileSystem.Path;
import fileSystem.fs.FileSystem;
import fileSystem.fs.FilterBy;

public class CreateTextFileTest {
	private CreateTextFile command;
	private FileSystem fs;
	private List<String> options;
	private List<String> arguments;
	
	@Before
	public void init() throws FileAlreadyExistsException {
		fs = new FileSystem();
		command = new CreateTextFile(fs, new Path());
		options = new ArrayList<String>();
		arguments = new ArrayList<String>();
	}

	@Test(expected = IllegalArgumentException.class)
	public void execute_CommandWithOption_ThrowIllegalArgumentException()
			throws InvalidPathException, FileAlreadyExistsException, IllegalArgumentException {
		options.add("-l");
		arguments.add("/home/f1");

		command.execute(options, arguments);
	}

	@Test(expected = FileAlreadyExistsException.class)
	public void execute_CreateAlreadyExistingFile_ThrowFileAlreadyExistsException()
			throws InvalidPathException, IllegalArgumentException, FileAlreadyExistsException {
		arguments.add("/home");

		command.execute(options, arguments);
	}

	@Test(expected = InvalidPathException.class)
	public void execute_CreateTextFileWithWrongAbsolutePath_ThrowInvalidPathException()
			throws InvalidPathException, FileAlreadyExistsException, IllegalArgumentException {
		arguments.add("/home/dir1/f1");

		command.execute(options, arguments);
	}

	@Test
	public void execute_CreateNewTextFile_TextFileAddedToFileSystem() throws InvalidPathException,
			FileAlreadyExistsException, IllegalArgumentException, NotDirectoryException, FileNotFoundException {
		arguments.add("/home/f1");

		command.execute(options, arguments);

		assertEquals("f1 ", fs.getDirectoryContent("/home", FilterBy.DEFAULT));
	}

	@Test
	public void execute_CreateTextFileWithRelativePath_TextFileAddedToFileSystem() throws InvalidPathException,
			FileAlreadyExistsException, IllegalArgumentException, NotDirectoryException, FileNotFoundException {
		arguments.add("f1");

		command.execute(options, arguments);

		assertEquals("f1 ", fs.getDirectoryContent("/home", FilterBy.DEFAULT));
	}

	@Test
	public void execute_CreateSeveralTextFiles_TextFilesAddedToFileSystem() throws InvalidPathException,
			FileAlreadyExistsException, IllegalArgumentException, NotDirectoryException, FileNotFoundException {
		arguments.add("f1");
		arguments.add("f2");
		arguments.add("f3");
		arguments.add("/f1");

		command.execute(options, arguments);

		assertEquals("f1 f2 f3 ", fs.getDirectoryContent("/home", FilterBy.DEFAULT));
		assertEquals("f1 home ", fs.getDirectoryContent("/", FilterBy.DEFAULT));
	}
}
