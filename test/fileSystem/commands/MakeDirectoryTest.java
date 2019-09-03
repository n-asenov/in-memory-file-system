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

public class MakeDirectoryTest {

	@Test(expected = IllegalArgumentException.class)
	public void execute_MakeDirectoryWithOption_ThrowIllegalArgumentException()
			throws InvalidPathException, FileAlreadyExistsException, IllegalArgumentException {
		MakeDirectory mkdir = new MakeDirectory(new FileSystem(), new Path());

		List<String> options = new ArrayList<String>();
		options.add("-l");
		List<String> arguments = new ArrayList<String>();
		arguments.add("/home/dir1");

		mkdir.execute(options, arguments);
	}

	@Test
	public void execute_NewDirectory_DirectoryAddedToFileSystem() throws InvalidPathException,
			FileAlreadyExistsException, IllegalArgumentException, NotDirectoryException, FileNotFoundException {
		FileSystem fs = new FileSystem();

		MakeDirectory mkdir = new MakeDirectory(fs, new Path());

		List<String> options = new ArrayList<String>();
		List<String> arguments = new ArrayList<String>();
		arguments.add("/home/dir1");

		mkdir.execute(options, arguments);

		assertEquals("dir1 ", fs.getDirectoryContent("/home", FilterBy.DEFAULT));
	}

	@Test
	public void execute_NewDirectoryWithRelativePath_DirectoryAddedToFileSystem()
			throws NotDirectoryException, FileNotFoundException, FileAlreadyExistsException, IllegalArgumentException {
		FileSystem fs = new FileSystem();

		MakeDirectory mkdir = new MakeDirectory(fs, new Path());

		List<String> options = new ArrayList<String>();
		List<String> arguments = new ArrayList<String>();
		arguments.add("dir1");

		mkdir.execute(options, arguments);

		assertEquals("dir1 ", fs.getDirectoryContent("/home", FilterBy.DEFAULT));
	}

	@Test (expected = FileAlreadyExistsException.class)
	public void execute_MakeExistingDirectory_ThrowFileAlreadyExistsException()
			throws InvalidPathException, FileAlreadyExistsException, IllegalArgumentException {
		MakeDirectory mkdir = new MakeDirectory(new FileSystem(), new Path());

		List<String> options = new ArrayList<String>();
		List<String> arguments = new ArrayList<String>();
		arguments.add("/home");

		mkdir.execute(options, arguments);
	}

	@Test (expected = InvalidPathException.class )
	public void execute_MakeDirectoryWithNonExistentPath_ThrowInvalidPathException()
			throws InvalidPathException, FileAlreadyExistsException, IllegalArgumentException {
		MakeDirectory mkdir = new MakeDirectory(new FileSystem(), new Path());
		String dir = "/home/dir1/dir2";

		List<String> options = new ArrayList<String>();
		List<String> arguments = new ArrayList<String>();
		arguments.add(dir);

		mkdir.execute(options, arguments);
	}
}
