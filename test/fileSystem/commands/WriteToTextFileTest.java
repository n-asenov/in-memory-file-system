package fileSystem.commands;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import fileSystem.Path;
import fileSystem.fs.FileSystem;
import fileSystem.fs.InvalidArgumentException;
import fileSystem.fs.NotEnoughMemoryException;

public class WriteToTextFileTest {
	private WriteToTextFile command;
	private FileSystem fs;
	private List<String> options;
	private List<String> arguments;
	
	@Before
	public void init() throws FileAlreadyExistsException, InvalidArgumentException {
		fs = new FileSystem();
		fs.createTextFile("/home/f1");
		
		command = new WriteToTextFile(fs, new Path());
		options = new ArrayList<String>();
		arguments = new ArrayList<String>();
	}

	@Test(expected = InvalidArgumentException.class)
	public void execute_CommandWithInvalidOption_ThrowIllegalArgumentException()
			throws FileNotFoundException, NotEnoughMemoryException, InvalidArgumentException {
		options.add("append");

		command.execute(options, arguments);
	}

	@Test(expected = InvalidArgumentException.class)
	public void execute_CommandWithInvalidNumberOfArguments_ThrowIllegalArgumentException()
			throws FileNotFoundException, NotEnoughMemoryException, InvalidArgumentException {
		arguments.add("/home/f1");

		command.execute(options, arguments);
	}

	@Test (expected = InvalidArgumentException.class)
	public void execute_CommnadWithWrongSecondArgument_ThrowIllegalArgumentException()
			throws FileNotFoundException, NotEnoughMemoryException, InvalidArgumentException {
		arguments.add("/home/f1");
		arguments.add("-2");
		arguments.add("Hello, World!");

		command.execute(options, arguments);
	}

	@Test
	public void execute_ValidCommandWithNoOption_WriteContentToTextFile()
			throws FileNotFoundException, NotEnoughMemoryException, InvalidArgumentException {
		arguments.add("/home/f1");
		arguments.add("1");
		arguments.add("Hello, World!");

		command.execute(options, arguments);

		assertEquals("Hello, World!", fs.getTextFileContent("/home/f1"));
	}

	@Test
	public void execute_ValidCommandWithOverwriteOption_WriteContentToTextFile()
			throws  FileNotFoundException, NotEnoughMemoryException, InvalidArgumentException {
		options.add("-overwrite");
		arguments.add("/home/f1");
		arguments.add("1");
		arguments.add("Hello, World!");

		command.execute(options, arguments);
		
		assertEquals("Hello, World!", fs.getTextFileContent("/home/f1"));
	}

	@Test
	public void execute_ValidCommandWithRelativePath_WriteContentToTextFile()
			throws FileNotFoundException, NotEnoughMemoryException, InvalidArgumentException {
		arguments.add("f1");
		arguments.add("1");
		arguments.add("Hello, World!");

		command.execute(options, arguments);
		
		assertEquals("Hello, World!", fs.getTextFileContent("/home/f1"));
	}
}
