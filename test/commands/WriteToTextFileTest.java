package commands;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import commands.WriteToTextFile;
import filesystem.InvalidArgumentException;
import filesystem.NotEnoughMemoryException;
import filesystem.VirtualFileSystem;
import path.Path;

public class WriteToTextFileTest {
	private WriteToTextFile command;
	private VirtualFileSystem fs;
	private List<String> options;
	private List<String> arguments;
	
	@Before
	public void init() {
		fs = new VirtualFileSystem();
		try {
			fs.createTextFile("/home/f1");
		} catch (FileAlreadyExistsException | InvalidArgumentException e) {
			throw new IllegalArgumentException();
		}
		
		command = new WriteToTextFile(fs, new Path());
		options = new ArrayList<String>();
		arguments = new ArrayList<String>();
	}

	@Test(expected = InvalidArgumentException.class)
	public void execute_CommandWithInvalidOption_ThrowIllegalArgumentException()
			throws NotEnoughMemoryException, InvalidArgumentException, IOException {
		options.add("append");

		command.execute(options, arguments);
	}

	@Test(expected = InvalidArgumentException.class)
	public void execute_CommandWithInvalidNumberOfArguments_ThrowIllegalArgumentException()
			throws NotEnoughMemoryException, InvalidArgumentException, IOException {
		arguments.add("/home/f1");

		command.execute(options, arguments);
	}

	@Test (expected = InvalidArgumentException.class)
	public void execute_CommnadWithWrongSecondArgument_ThrowIllegalArgumentException()
			throws NotEnoughMemoryException, InvalidArgumentException, IOException {
		arguments.add("/home/f1");
		arguments.add("Hello");
		arguments.add("Hello, World!");

		command.execute(options, arguments);
	}

	@Test
	public void execute_ValidCommandWithNoOption_WriteContentToTextFile()
			throws NotEnoughMemoryException, InvalidArgumentException, IOException {
		arguments.add("/home/f1");
		arguments.add("1");
		arguments.add("Hello, World!");

		command.execute(options, arguments);

		assertEquals("Hello, World!", fs.getTextFileContent("/home/f1"));
	}

	@Test
	public void execute_ValidCommandWithOverwriteOption_WriteContentToTextFile()
			throws  NotEnoughMemoryException, InvalidArgumentException, IOException {
		options.add("-overwrite");
		arguments.add("/home/f1");
		arguments.add("1");
		arguments.add("Hello, World!");

		command.execute(options, arguments);
		
		assertEquals("Hello, World!", fs.getTextFileContent("/home/f1"));
	}

	@Test
	public void execute_ValidCommandWithRelativePath_WriteContentToTextFile()
			throws NotEnoughMemoryException, InvalidArgumentException, IOException {
		arguments.add("f1");
		arguments.add("1");
		arguments.add("Hello, World!");

		command.execute(options, arguments);
		
		assertEquals("Hello, World!", fs.getTextFileContent("/home/f1"));
	}
}