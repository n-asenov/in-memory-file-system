package commands;

import static org.junit.Assert.assertArrayEquals;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import filesystem.File;
import filesystem.VirtualFileSystem;
import filesystem.exceptions.InvalidArgumentException;
import path.Path;

public class CreateTextFileTest {
    private static final Comparator<File> DEFAULT = (f1, f2) -> f1.getName().compareTo(f2.getName());
    
    private CreateTextFile command;
    private VirtualFileSystem fs;
    private List<String> options;
    private List<String> arguments;

    @Before
    public void init() {
	fs = new VirtualFileSystem();
	command = new CreateTextFile(fs, new Path());
	options = new ArrayList<String>();
	arguments = new ArrayList<String>();
    }

    @Test(expected = InvalidArgumentException.class)
    public void execute_CommandWithOption_ThrowIllegalArgumentException() throws InvalidArgumentException, IOException {
	options.add("-l");
	arguments.add("/home/f1");

	command.execute(arguments, options);
    }

    @Test(expected = FileAlreadyExistsException.class)
    public void execute_CreateAlreadyExistingFile_ThrowFileAlreadyExistsException()
	    throws InvalidArgumentException, IOException {
	arguments.add("/home");

	command.execute(arguments, options);
    }

    @Test(expected = InvalidArgumentException.class)
    public void execute_CreateTextFileWithWrongAbsolutePath_ThrowInvalidPathException()
	    throws InvalidArgumentException, IOException {
	arguments.add("/home/dir1/f1");

	command.execute(arguments, options);
    }

    @Test
    public void execute_CreateNewTextFile_TextFileAddedToFileSystem()
	    throws InvalidPathException, InvalidArgumentException, IOException {
	arguments.add("/home/f1");

	command.execute(arguments, options);
	String[] expectedResult = { "f1" };
	assertArrayEquals(expectedResult, fs.getDirectoryContent("/home", DEFAULT).toArray());
    }

    @Test
    public void execute_CreateTextFileWithRelativePath_TextFileAddedToFileSystem()
	    throws InvalidPathException, InvalidArgumentException, IOException {
	arguments.add("f1");

	command.execute(arguments, options);
	String[] expectedResult = { "f1" };
	assertArrayEquals(expectedResult, fs.getDirectoryContent("/home", DEFAULT).toArray());
    }

    @Test
    public void execute_CreateSeveralTextFiles_TextFilesAddedToFileSystem()
	    throws InvalidPathException, InvalidArgumentException, IOException {
	arguments.add("f1");
	arguments.add("f2");
	arguments.add("f3");
	arguments.add("/f1");

	command.execute(arguments, options);
	String[] expectedResult = { "f1", "f2", "f3" };
	assertArrayEquals(expectedResult, fs.getDirectoryContent("/home", DEFAULT).toArray());
	String[] expectedResult2 = { "f1", "home" };
	assertArrayEquals(expectedResult2, fs.getDirectoryContent("/", DEFAULT).toArray());
    }
}
