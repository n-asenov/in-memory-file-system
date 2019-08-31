package fileSystem.commands;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fileSystem.Path;
import fileSystem.fs.FileSystem;

public class ChangeDirectoryTest {

	@Test
	public void execute_RelativePathToGoBackToParentDirectory_ReturnToParentDirectory() {
		Path path = new Path();

		ChangeDirectory command = new ChangeDirectory(new FileSystem(), path);

		List<String> options = new ArrayList<String>();
		List<String> arguments = new ArrayList<String>();
		arguments.add("..");

		assertEquals(null, command.execute(options, arguments));
		assertEquals("/home/..", path.getCurrentDirectory());
	}

	@Test
	public void execute_ChangeDirectoryToCurrentDirectory_CurrentDirectoryDoesntChange() {
		Path path = new Path();

		ChangeDirectory command = new ChangeDirectory(new FileSystem(), path);

		List<String> options = new ArrayList<String>();
		List<String> arguments = new ArrayList<String>();
		arguments.add(".");

		assertEquals(null, command.execute(options, arguments));
		assertEquals("/home/.", path.getCurrentDirectory());
	}

	@Test
	public void execute_ChangeDirectoryWithNoArguments_ReturnToHomeDirectory() {
		Path path = new Path();
		path.setCurrentDirectory("/");

		ChangeDirectory command = new ChangeDirectory(new FileSystem(), path);

		List<String> options = new ArrayList<String>();
		List<String> arguments = new ArrayList<String>();

		assertEquals(null, command.execute(options, arguments));
		assertEquals("/home", path.getCurrentDirectory());
	}

}
