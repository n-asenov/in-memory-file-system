package fileSystem.commands;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fileSystem.Path;
import fileSystem.fs.FileSystem;

public class WriteToTextFileTest {

	@Test(expected = IllegalArgumentException.class)
	public void execute_CommandWithInvalidOption_ThrowIllegalArgumentException()
			throws InvalidPathException, IllegalArgumentException, FileNotFoundException, FileAlreadyExistsException {
		WriteToTextFile command = new WriteToTextFile(new FileSystem(), new Path());

		List<String> options = new ArrayList<String>();
		options.add("append");
		List<String> arguments = new ArrayList<String>();

		command.execute(options, arguments);
	}

	@Test(expected = IllegalArgumentException.class)
	public void execute_CommandWithInvalidNumberOfArguments_ThrowIllegalArgumentException()
			throws InvalidPathException, IllegalArgumentException, FileNotFoundException, FileAlreadyExistsException {
		WriteToTextFile command = new WriteToTextFile(new FileSystem(), new Path());

		List<String> options = new ArrayList<String>();
		List<String> arguments = new ArrayList<String>();
		arguments.add("/home/f1");

		command.execute(options, arguments);
	}

	@Test (expected = IllegalArgumentException.class)
	public void execute_CommnadWithWrongSecondArgument_ThrowIllegalArgumentException()
			throws InvalidPathException, IllegalArgumentException, FileNotFoundException, FileAlreadyExistsException {
		WriteToTextFile command = new WriteToTextFile(new FileSystem(), new Path());

		List<String> options = new ArrayList<String>();
		List<String> arguments = new ArrayList<String>();
		arguments.add("/home/f1");
		arguments.add("Number");
		arguments.add("Hello, World!");

		command.execute(options, arguments);
	}

	@Test
	public void execute_ValidCommandWithNoOption_WriteContentToTextFile()
			throws InvalidPathException, IllegalArgumentException, FileNotFoundException, FileAlreadyExistsException {
		FileSystem fs = new FileSystem();
		fs.createTextFile("/home/f1");

		WriteToTextFile command = new WriteToTextFile(fs, new Path());

		List<String> options = new ArrayList<String>();
		List<String> arguments = new ArrayList<String>();
		arguments.add("/home/f1");
		arguments.add("1");
		arguments.add("Hello, World!");

		command.execute(options, arguments);

		assertEquals("Hello, World!", fs.getTextFileContent("/home/f1"));
	}

	@Test
	public void execute_ValidCommandWithOverwriteOption_WriteContentToTextFile()
			throws InvalidPathException, IllegalArgumentException, FileNotFoundException, FileAlreadyExistsException {
		FileSystem fs = new FileSystem();
		fs.createTextFile("/home/f1");

		WriteToTextFile command = new WriteToTextFile(fs, new Path());

		List<String> options = new ArrayList<String>();
		options.add("-overwrite");
		List<String> arguments = new ArrayList<String>();
		arguments.add("/home/f1");
		arguments.add("1");
		arguments.add("Hello, World!");

		command.execute(options, arguments);
		
		assertEquals("Hello, World!", fs.getTextFileContent("/home/f1"));
	}

	@Test
	public void execute_ValidCommandWithRelativePath_WriteContentToTextFile()
			throws InvalidPathException, IllegalArgumentException, FileNotFoundException, FileAlreadyExistsException {
		FileSystem fs = new FileSystem();
		fs.createTextFile("/home/f1");

		WriteToTextFile command = new WriteToTextFile(fs, new Path());

		List<String> options = new ArrayList<String>();
		List<String> arguments = new ArrayList<String>();
		arguments.add("f1");
		arguments.add("1");
		arguments.add("Hello, World!");

		command.execute(options, arguments);
		
		assertEquals("Hello, World!", fs.getTextFileContent("/home/f1"));
	}
}
