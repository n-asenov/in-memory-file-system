package commands;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import commands.exception.InvalidArgumentException;
import filesystem.VirtualFileSystem;
import filesystem.exceptions.NotEnoughMemoryException;
import path.Path;

public class WordCountTest {
    private VirtualFileSystem fileSystem;
    private WordCount command;
    private Set<String> options;
    private List<String> arguments;

    @Before
    public void init() throws FileAlreadyExistsException, InvalidArgumentException {
	fileSystem = new VirtualFileSystem();
	command = new WordCount(fileSystem, new Path());
	arguments = new ArrayList<>();
	options = new HashSet<>();
    }

    @Test(expected = InvalidArgumentException.class)
    public void execute_InvalidOption_ThrowInvalidArgumentException()
	    throws InvalidArgumentException, FileNotFoundException {
	options.add("-invalid");

	command.execute(arguments, options);
    }

    @Test(expected = InvalidArgumentException.class)
    public void execute_InvalidNumberOfArguments_ThrowInvalidArgumentException()
	    throws InvalidArgumentException, FileNotFoundException {
	command.execute(arguments, options);
    }

    @Test
    public void execute_TextFile_GetNumberOfWordsInTextFile() throws InvalidArgumentException, NotEnoughMemoryException,
	    FileAlreadyExistsException, FileNotFoundException {
	String absolutePath = "/home/f1";
	fileSystem.createTextFile(absolutePath);
	fileSystem.writeToTextFile(absolutePath, 2, "hello world");

	arguments.add(absolutePath);

	String expectedResult = "2";
	String actualResult = command.execute(arguments, options);
	assertEquals(expectedResult, actualResult);
    }

    @Test
    public void execute_Text_ReturnNumberOfWordsInText() throws InvalidArgumentException, FileNotFoundException {
	arguments.add("hello");
	arguments.add("world");

	String expectedResult = "2";
	String actualResult = command.execute(arguments, options);
	assertEquals(expectedResult, actualResult);
    }

    @Test
    public void execute_CommandWithLOptionAndTextFile_ReturnNumberOfLinesInTextFile() throws InvalidArgumentException,
	    NotEnoughMemoryException, FileNotFoundException, FileAlreadyExistsException {
	String absolutePath = "/home/f1";
	fileSystem.createTextFile(absolutePath);
	fileSystem.writeToTextFile(absolutePath, 5, "hello world");

	options.add("-l");
	arguments.add(absolutePath);

	String expectedResult = "5";
	String actualResult = command.execute(arguments, options);
	assertEquals(expectedResult, actualResult);
    }

    @Test
    public void execute_CommandWithOptionAndText_ReturnNumberOfLinesInText()
	    throws InvalidArgumentException, FileNotFoundException {
	options.add("-l");
	arguments.add("First Line\\n");
	arguments.add("Second Line\\n");
	arguments.add("Third");

	String expectedResult = "3";
	String actualResult = command.execute(arguments, options);
	assertEquals(expectedResult, actualResult);
    }
    
    @Test
    public void execute_TextWithNewLine_ReturnNumberOfWordsInText() throws FileNotFoundException, InvalidArgumentException {
	arguments.add("First Line" + System.lineSeparator());
	arguments.add("word word word");
	
	String expectedResult = "5";
	String actualResult = command.execute(arguments, options);
	assertEquals(expectedResult, actualResult);
    }
}
