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

	@Test(expected = IllegalArgumentException.class)
	public void execute_MakeDirectoryWithOption_ThrowIllegalArgumentException()
			throws InvalidPathException, IllegalArgumentException, FileAlreadyExistsException {
		options.add("-l");
		arguments.add("/home/dir1");

		command.execute(options, arguments);
	}

	@Test
	public void execute_NewDirectory_DirectoryAddedToFileSystem() throws InvalidPathException,
			FileAlreadyExistsException, IllegalArgumentException, NotDirectoryException, FileNotFoundException {
		arguments.add("/home/dir1");

		command.execute(options, arguments);

		assertEquals("dir1 ", fs.getDirectoryContent("/home", FilterBy.DEFAULT));
	}

	@Test
	public void execute_NewDirectoryWithRelativePath_DirectoryAddedToFileSystem()
			throws NotDirectoryException, FileNotFoundException, FileAlreadyExistsException, IllegalArgumentException {
		arguments.add("dir1");

		command.execute(options, arguments);

		assertEquals("dir1 ", fs.getDirectoryContent("/home", FilterBy.DEFAULT));
	}

	@Test(expected = FileAlreadyExistsException.class)
	public void execute_MakeExistingDirectory_ThrowFileAlreadyExistsException()
			throws InvalidPathException, FileAlreadyExistsException, IllegalArgumentException {
		arguments.add("/home");

		command.execute(options, arguments);
	}

	@Test(expected = InvalidPathException.class)
	public void execute_MakeDirectoryWithNonExistentPath_ThrowInvalidPathException()
			throws InvalidPathException, FileAlreadyExistsException, IllegalArgumentException {
		arguments.add("/home/dir1/dir2");

		command.execute(options, arguments);
	}
}
