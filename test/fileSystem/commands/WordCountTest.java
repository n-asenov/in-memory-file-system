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

public class WordCountTest {
	private FileSystem fileSystem;
	private WordCount command;
	private List<String> options;
	private List<String> arguments;
	
	@Before
	public void init() throws FileAlreadyExistsException, InvalidArgumentException {
		fileSystem = new FileSystem();
		command = new WordCount(fileSystem, new Path());
		options = new ArrayList<String>();
		arguments = new ArrayList<String>();
	}

	@Test (expected = InvalidArgumentException.class)
	public void execute_InvalidOption_ThrowInvalidArgumentException() throws FileNotFoundException, InvalidArgumentException {
		options.add("-invalid");
		
		command.execute(options, arguments);
	}
	
	@Test (expected = InvalidArgumentException.class)
	public void execute_InvalidNumberOfArguments_ThrowInvalidArgumentException() throws FileNotFoundException, InvalidArgumentException {
		command.execute(options, arguments);
	}
	
	@Test
	public void execute_TextFile_GetNumberOfWordsInTextFile() throws FileAlreadyExistsException, InvalidArgumentException, FileNotFoundException, NotEnoughMemoryException {
		String absolutePath = "/home/f1";
		fileSystem.createTextFile(absolutePath);
		fileSystem.writeToTextFile(absolutePath, 2, "hello world", false);
		
		arguments.add(absolutePath);
		
		assertEquals("2", command.execute(options, arguments));
	}
	
	@Test
	public void execute_Text_ReturnNumberOfWordsInText() throws FileNotFoundException, InvalidArgumentException {
		arguments.add("hello");
		arguments.add("world");
		
		assertEquals("2", command.execute(options, arguments));
	}
	
	@Test
	public void execute_CommandWithLOptionAndTextFile_ReturnNumberOfLinesInTextFile() throws FileNotFoundException, InvalidArgumentException, NotEnoughMemoryException, FileAlreadyExistsException {
		String absolutePath = "/home/f1";
		fileSystem.createTextFile(absolutePath);
		fileSystem.writeToTextFile(absolutePath, 5, "hello world", false);
		
		options.add("-l");
		arguments.add(absolutePath);
		
		assertEquals("5", command.execute(options, arguments));
	}
	
	@Test
	public void execute_CommandWithOptionAndText_ReturnNumberOfLinesInText() throws FileNotFoundException, InvalidArgumentException {
		options.add("-l");
		arguments.add("First Line\\n");
		arguments.add("Second Line\\n");
		arguments.add("Third");
		
		assertEquals("3", command.execute(options, arguments));
	}
}
