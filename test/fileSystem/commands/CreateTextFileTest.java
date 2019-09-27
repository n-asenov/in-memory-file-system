package fileSystem.commands;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import fileSystem.Path;
import fileSystem.fs.VirtualFileSystem;
import fileSystem.fs.FilterBy;
import fileSystem.fs.InvalidArgumentException;

public class CreateTextFileTest {
	private CreateTextFile command;
	private VirtualFileSystem fs;
	private List<String> options;
	private List<String> arguments;
	
	@Before
	public void init() {
		fs = new VirtualFileSystem();
		command = new CreateTextFile(fs, new Path());
		options = new ArrayList<String>();
		arguments = new ArrayList<String>();
	}

	@Test(expected = InvalidArgumentException.class)
	public void execute_CommandWithOption_ThrowIllegalArgumentException()
			throws InvalidArgumentException, IOException {
		options.add("-l");
		arguments.add("/home/f1");

		command.execute(options, arguments);
	}

	@Test(expected = FileAlreadyExistsException.class)
	public void execute_CreateAlreadyExistingFile_ThrowFileAlreadyExistsException()
			throws InvalidArgumentException, IOException {
		arguments.add("/home");

		command.execute(options, arguments);
	}

	@Test(expected = InvalidArgumentException.class)
	public void execute_CreateTextFileWithWrongAbsolutePath_ThrowInvalidPathException()
			throws  InvalidArgumentException, IOException {
		arguments.add("/home/dir1/f1");

		command.execute(options, arguments);
	}

	@Test
	public void execute_CreateNewTextFile_TextFileAddedToFileSystem() throws InvalidPathException,
			InvalidArgumentException, IOException {
		arguments.add("/home/f1");

		command.execute(options, arguments);
		String[] expectedResult = { "f1" };
		assertArrayEquals(expectedResult, fs.getDirectoryContent("/home", FilterBy.DEFAULT).toArray());
	}

	@Test
	public void execute_CreateTextFileWithRelativePath_TextFileAddedToFileSystem() throws InvalidPathException,
			InvalidArgumentException, IOException {
		arguments.add("f1");

		command.execute(options, arguments);
		String[] expectedResult = {"f1"};
		assertArrayEquals(expectedResult, fs.getDirectoryContent("/home", FilterBy.DEFAULT).toArray());
	}

	@Test
	public void execute_CreateSeveralTextFiles_TextFilesAddedToFileSystem() throws InvalidPathException,
			InvalidArgumentException, IOException {
		arguments.add("f1");
		arguments.add("f2");
		arguments.add("f3");
		arguments.add("/f1");

		command.execute(options, arguments);
		String[] expectedResult = { "f1", "f2", "f3" };
		assertArrayEquals(expectedResult, fs.getDirectoryContent("/home", FilterBy.DEFAULT).toArray());
		String[] expectedResult2 = { "f1", "home" };
		assertArrayEquals(expectedResult2, fs.getDirectoryContent("/", FilterBy.DEFAULT).toArray());
	}
}
