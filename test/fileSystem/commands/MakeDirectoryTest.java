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
import fileSystem.fs.InvalidArgumentException;

public class MakeDirectoryTest {
	private MakeDirectory command;
	private FileSystem fs;
	private List<String> options;
	private List<String> arguments;

	@Before
	public void init() throws FileAlreadyExistsException {
		fs = new FileSystem();
		command = new MakeDirectory(fs, new Path());
		options = new ArrayList<String>();
		arguments = new ArrayList<String>();
	}

	@Test(expected = InvalidArgumentException.class)
	public void execute_MakeDirectoryWithOption_ThrowIllegalArgumentException()
			throws FileAlreadyExistsException, InvalidArgumentException {
		options.add("-l");
		arguments.add("/home/dir1");

		command.execute(options, arguments);
	}

	@Test
	public void execute_NewDirectory_DirectoryAddedToFileSystem() throws InvalidPathException,
			FileAlreadyExistsException, NotDirectoryException, FileNotFoundException, InvalidArgumentException {
		arguments.add("/home/dir1");

		command.execute(options, arguments);

		assertEquals("dir1 ", fs.getDirectoryContent("/home", FilterBy.DEFAULT));
	}

	@Test
	public void execute_NewDirectoryWithRelativePath_DirectoryAddedToFileSystem()
			throws NotDirectoryException, FileNotFoundException, FileAlreadyExistsException, InvalidArgumentException {
		arguments.add("dir1");

		command.execute(options, arguments);

		assertEquals("dir1 ", fs.getDirectoryContent("/home", FilterBy.DEFAULT));
	}

	@Test(expected = FileAlreadyExistsException.class)
	public void execute_MakeExistingDirectory_ThrowFileAlreadyExistsException()
			throws FileAlreadyExistsException, InvalidArgumentException {
		arguments.add("/home");

		command.execute(options, arguments);
	}

	@Test(expected = InvalidArgumentException.class)
	public void execute_MakeDirectoryWithNonExistentPath_ThrowInvalidPathException()
			throws  FileAlreadyExistsException, InvalidArgumentException{
		arguments.add("/home/dir1/dir2");

		command.execute(options, arguments);
	}
}
