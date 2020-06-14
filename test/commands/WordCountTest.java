package commands;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import commands.WordCount;
import filesystem.VirtualFileSystem;
import filesystem.exceptions.InvalidArgumentException;
import filesystem.exceptions.NotEnoughMemoryException;
import path.Path;

public class WordCountTest {
    private VirtualFileSystem fileSystem;
    private WordCount command;
    private List<String> options;
    private List<String> arguments;

    @Before
    public void init() throws FileAlreadyExistsException, InvalidArgumentException {
	fileSystem = new VirtualFileSystem();
	command = new WordCount(fileSystem, new Path());
	options = new ArrayList<String>();
	arguments = new ArrayList<String>();
    }

    @Test(expected = InvalidArgumentException.class)
    public void execute_InvalidOption_ThrowInvalidArgumentException() throws InvalidArgumentException, IOException {
	options.add("-invalid");

	command.execute(arguments, options);
    }

    @Test(expected = InvalidArgumentException.class)
    public void execute_InvalidNumberOfArguments_ThrowInvalidArgumentException()
	    throws InvalidArgumentException, IOException {
	command.execute(arguments, options);
    }

    @Test
    public void execute_TextFile_GetNumberOfWordsInTextFile()
	    throws InvalidArgumentException, NotEnoughMemoryException, IOException {
	String absolutePath = "/home/f1";
	fileSystem.createTextFile(absolutePath);
	fileSystem.writeToTextFile(absolutePath, 2, "hello world");

	arguments.add(absolutePath);

	assertEquals("2", command.execute(arguments, options));
    }

    @Test
    public void execute_Text_ReturnNumberOfWordsInText() throws InvalidArgumentException, IOException {
	arguments.add("hello");
	arguments.add("world");

	assertEquals("2", command.execute(arguments, options));
    }

    @Test
    public void execute_CommandWithLOptionAndTextFile_ReturnNumberOfLinesInTextFile()
	    throws InvalidArgumentException, NotEnoughMemoryException, IOException {
	String absolutePath = "/home/f1";
	fileSystem.createTextFile(absolutePath);
	fileSystem.writeToTextFile(absolutePath, 5, "hello world");

	options.add("-l");
	arguments.add(absolutePath);

	assertEquals("5", command.execute(arguments, options));
    }

    @Test
    public void execute_CommandWithOptionAndText_ReturnNumberOfLinesInText()
	    throws InvalidArgumentException, IOException {
	options.add("-l");
	arguments.add("First Line\\n");
	arguments.add("Second Line\\n");
	arguments.add("Third");

	assertEquals("3", command.execute(arguments, options));
    }
}
