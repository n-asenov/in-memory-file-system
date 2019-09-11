package fileSystem.commands;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import fileSystem.Path;
import fileSystem.fs.AbstractFileSystem;
import fileSystem.fs.FileSystem;
import fileSystem.fs.InvalidArgumentException;
import fileSystem.fs.NotEnoughMemoryException;

public class RemoveLineContentFromTextFileTest {
	private RemoveLineContentFromTextFile command;
	private AbstractFileSystem fileSystem;
	private List<String> options;
	private List<String> arguments;
	
	@Before
	public void init() throws FileAlreadyExistsException {
		fileSystem = new FileSystem();
		command = new RemoveLineContentFromTextFile(fileSystem, new Path());
		options = new ArrayList<String>();
		arguments = new ArrayList<String>();
	}
	
	@Test (expected = InvalidArgumentException.class)
	public void execute_InvalidOption_ThrowInvalidArgumentException() throws FileNotFoundException, InvalidArgumentException  {
		options.add("-invalid");
		
		command.execute(options, arguments);
	}
	
	@Test (expected = InvalidArgumentException.class)
	public void execute_TooMuchArguments_ThrowInvalidArgumentException() throws FileNotFoundException, InvalidArgumentException   {
		arguments.add("f1");
		arguments.add("1-5");
		arguments.add("6-10");
		
		command.execute(options, arguments);
	}
	
	@Test (expected = InvalidArgumentException.class)
	public void execute_NotEnoughArguments_ThrowInvalidArgumentException() throws InvalidArgumentException, FileNotFoundException {
		command.execute(options, arguments);
	}
	
	@Test (expected = InvalidArgumentException.class)
	public void execute_InvalidInterval_ThrowInvalidArgumentException()
			throws InvalidArgumentException, FileNotFoundException {
		arguments.add("f1");
		arguments.add("1-5-10");
		
		command.execute(options, arguments);
	}
	
	@Test (expected = InvalidArgumentException.class)
	public void execute_WrongPathToTextFile_ThrowInvalidArgumentExcepiton() throws FileNotFoundException, InvalidArgumentException {
		arguments.add("/home/dir1/f1");
		arguments.add("1-5");
		
		command.execute(options, arguments);
	}
	
	@Test (expected = FileNotFoundException.class)
	public void execute_TextFileDoesNotExists_ThrowFileNotFoundException() throws FileNotFoundException, InvalidArgumentException {
		arguments.add("/home/f1");
		arguments.add("1-5");
		
		command.execute(options, arguments);
	}
	
	@Test (expected = FileNotFoundException.class)
	public void execute_FileIsDirectory_ThrowFileNotFoundException() throws FileNotFoundException, InvalidArgumentException {
		arguments.add("/home");
		arguments.add("1-5");
		
		command.execute(options, arguments);
	}
	
	@Test 
	public void execute_TextFile_ClearTextFileContent() throws FileAlreadyExistsException, InvalidArgumentException, FileNotFoundException, NotEnoughMemoryException {
		String absolutePath = "/home/f1";
		fileSystem.createTextFile(absolutePath);
		
		int lines = 10;
		for(int i = 1; i <= lines; i++) {
			fileSystem.writeToTextFile(absolutePath, i, "hello", false);
		}
		
		arguments.add(absolutePath);
		arguments.add("1-" + lines);
		
		assertNull(command.execute(options, arguments));
		assertEquals("", fileSystem.getTextFileContent(absolutePath));
	}
}
