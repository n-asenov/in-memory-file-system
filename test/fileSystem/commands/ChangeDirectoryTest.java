package fileSystem.commands;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import fileSystem.Path;
import fileSystem.fs.AbstractFileSystem;
import fileSystem.fs.VirtualFileSystem;
import fileSystem.fs.InvalidArgumentException;

public class ChangeDirectoryTest {
	private AbstractFileSystem fileSystem;
	private ChangeDirectory command;
	private Path path;
	private List<String> options;
	private List<String> arguments;

	@Before
	public void init() {
		path = new Path();
		fileSystem = new VirtualFileSystem();
		command = new ChangeDirectory(fileSystem, path);
		options = new ArrayList<String>();
		arguments = new ArrayList<String>();
	}

	@Test(expected = InvalidArgumentException.class)
	public void execute_InvalidOption_ThrowInvalidArgumentException()
			throws NotDirectoryException, FileNotFoundException, InvalidArgumentException {
		options.add("-a");

		command.execute(options, arguments);
	}

	@Test(expected = InvalidArgumentException.class)
	public void execute_InvalidArguments_ThrowInvalidArgumentException()
			throws NotDirectoryException, FileNotFoundException, InvalidArgumentException {
		arguments.add("/home");
		arguments.add("/dir1");

		command.execute(options, arguments);
	}
	
	@Test(expected = FileNotFoundException.class) 
	public void execute_ChangeDirectoryToNonExistentDirectory_ThrowFileNotFoundExcepiton()
			throws NotDirectoryException, FileNotFoundException, InvalidArgumentException {
		arguments.add("/home/dir1");
		
		command.execute(options, arguments);
	}

	@Test (expected = NotDirectoryException.class) 
	public void execute_ChangeDirectoryToTextFile_ThrowNotDirectoryException()
			throws InvalidArgumentException, IOException {
		fileSystem.createTextFile("/home/f1");
		
		arguments.add("f1");
		
		command.execute(options, arguments);
	}
	
	@Test
	public void execute_RelativePathToGoBackToParentDirectory_ReturnToParentDirectory()
			throws FileNotFoundException, NotDirectoryException, InvalidArgumentException {
		arguments.add("..");

		command.execute(options, arguments);

		assertEquals("/", path.getCurrentDirectory());
	}

	@Test
	public void execute_ChangeDirectoryToCurrentDirectory_CurrentDirectoryDoesntChange()
			throws FileNotFoundException, NotDirectoryException, InvalidArgumentException {
		arguments.add(".");

		command.execute(options, arguments);

		assertEquals("/home/", path.getCurrentDirectory());
	}

	@Test
	public void execute_ChangeDirectoryWithNoArguments_ReturnToHomeDirectory()
			throws FileNotFoundException, NotDirectoryException, InvalidArgumentException {
		path.setCurrentDirectory("/");

		command.execute(options, arguments);

		assertEquals("/home/", path.getCurrentDirectory());
	}
}
