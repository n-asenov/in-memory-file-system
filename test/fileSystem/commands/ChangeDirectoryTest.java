package fileSystem.commands;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import fileSystem.Path;
import fileSystem.fs.FileSystem;

public class ChangeDirectoryTest {
	private ChangeDirectory command;
	private Path path;
	private List<String> options;
	private List<String> arguments;
	
	@Before
	public void init() throws FileAlreadyExistsException {
		path = new Path();
		command = new ChangeDirectory(new FileSystem(), path);
		options = new ArrayList<String>();
		arguments = new ArrayList<String>();
	}
	
	@Test
	public void execute_RelativePathToGoBackToParentDirectory_ReturnToParentDirectory()
			throws InvalidPathException, IllegalArgumentException, FileNotFoundException {
		arguments.add("..");

		command.execute(options, arguments);

		assertEquals("/home/..", path.getCurrentDirectory());
	}

	@Test
	public void execute_ChangeDirectoryToCurrentDirectory_CurrentDirectoryDoesntChange()
			throws InvalidPathException, IllegalArgumentException, FileNotFoundException {
		arguments.add(".");

		command.execute(options, arguments);
		
		assertEquals("/home/.", path.getCurrentDirectory());
	}

	@Test
	public void execute_ChangeDirectoryWithNoArguments_ReturnToHomeDirectory()
			throws InvalidPathException, IllegalArgumentException, FileNotFoundException {
		path.setCurrentDirectory("/");

		command.execute(options, arguments);
		
		assertEquals("/home", path.getCurrentDirectory());
	}
}
