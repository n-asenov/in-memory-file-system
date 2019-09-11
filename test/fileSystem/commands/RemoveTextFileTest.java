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
import fileSystem.fs.AbstractFileSystem;
import fileSystem.fs.FileSystem;
import fileSystem.fs.FilterBy;
import fileSystem.fs.InvalidArgumentException;

public class RemoveTextFileTest {
	private RemoveTextFile command;
	private AbstractFileSystem fileSystem;
	private List<String> options;
	private List<String> arguments;

	@Before
	public void init() throws FileAlreadyExistsException {
		fileSystem = new FileSystem();
		command = new RemoveTextFile(fileSystem, new Path());
		options = new ArrayList<String>();
		arguments = new ArrayList<String>();
	}

	@Test(expected = InvalidArgumentException.class)
	public void execute_InvalidOption_ThrowInvalidArgumentException()
			throws FileNotFoundException, InvalidArgumentException {
		options.add("-invalid");

		command.execute(options, arguments);
	}

	@Test(expected = InvalidArgumentException.class)
	public void execute_WrongPathToFile_ThrowInvalidArgumentException()
			throws FileNotFoundException, InvalidArgumentException {
		arguments.add("/home/dir1/f1");

		command.execute(options, arguments);
	}

	@Test(expected = FileNotFoundException.class)
	public void execute_TextFileDoesNotExists_ThrowFileNotFoudException()
			throws FileNotFoundException, InvalidArgumentException {
		arguments.add("/home/f1");

		command.execute(options, arguments);
	}

	@Test(expected = FileNotFoundException.class)
	public void execute_FileIsDirectory_ThrowFileNotFoundException()
			throws FileNotFoundException, InvalidArgumentException {
		arguments.add("/home");

		command.execute(options, arguments);
	}

	@Test
	public void execute_TextFile_RemoveTextFileFromDirectory()
			throws FileAlreadyExistsException, InvalidArgumentException, FileNotFoundException, NotDirectoryException {
		fileSystem.createTextFile("/home/f1");
		arguments.add("/home/f1");

		assertNull(command.execute(options, arguments));
		assertEquals("", fileSystem.getDirectoryContent("/home", FilterBy.DEFAULT));
	}

	@Test
	public void execute_SeveralTextFiles_RemoveAllTextFiles() throws FileAlreadyExistsException, InvalidArgumentException, FileNotFoundException, NotDirectoryException {
		int numberOfFiles = 10;

		for (int i = 0; i < numberOfFiles; i++) {
			String fileName = "/home/f" + i;
			fileSystem.createTextFile(fileName);
			arguments.add(fileName);
		}
		
		assertNull(command.execute(options, arguments));
		assertEquals("", fileSystem.getDirectoryContent("/home", FilterBy.DEFAULT));
	}
}
