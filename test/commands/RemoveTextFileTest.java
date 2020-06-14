package commands;

import static org.junit.Assert.assertArrayEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import filesystem.File;
import filesystem.VirtualFileSystem;
import filesystem.exceptions.InvalidArgumentException;
import path.Path;

public class RemoveTextFileTest {
    private static final Comparator<File> DEFAULT = (f1, f2) -> f1.getName().compareTo(f2.getName());
    
    private RemoveTextFile command;
    private VirtualFileSystem fileSystem;
    private List<String> options;
    private List<String> arguments;

    @Before
    public void init() {
	fileSystem = new VirtualFileSystem();
	command = new RemoveTextFile(fileSystem, new Path());
	options = new ArrayList<String>();
	arguments = new ArrayList<String>();
    }

    @Test(expected = InvalidArgumentException.class)
    public void execute_InvalidOption_ThrowInvalidArgumentException() throws InvalidArgumentException, IOException {
	options.add("-invalid");

	command.execute(arguments, options);
    }

    @Test(expected = InvalidArgumentException.class)
    public void execute_WrongPathToFile_ThrowInvalidArgumentException() throws InvalidArgumentException, IOException {
	arguments.add("/home/dir1/f1");

	command.execute(arguments, options);
    }

    @Test(expected = FileNotFoundException.class)
    public void execute_TextFileDoesNotExists_ThrowFileNotFoudException() throws InvalidArgumentException, IOException {
	arguments.add("/home/f1");

	command.execute(arguments, options);
    }

    @Test(expected = FileNotFoundException.class)
    public void execute_FileIsDirectory_ThrowFileNotFoundException() throws InvalidArgumentException, IOException {
	arguments.add("/home");

	command.execute(arguments, options);
    }

    @Test
    public void execute_TextFile_RemoveTextFileFromDirectory() throws InvalidArgumentException, IOException {
	fileSystem.createTextFile("/home/f1");
	arguments.add("/home/f1");

	String[] expectedResult = {};
	command.execute(arguments, options);
	
	assertArrayEquals(expectedResult, fileSystem.getDirectoryContent("/home", DEFAULT).toArray());
    }

    @Test
    public void execute_SeveralTextFiles_RemoveAllTextFiles() throws InvalidArgumentException, IOException {
	int numberOfFiles = 10;

	for (int i = 0; i < numberOfFiles; i++) {
	    String fileName = "/home/f" + i;
	    fileSystem.createTextFile(fileName);
	    arguments.add(fileName);
	}
	String[] expectedResult = {};
	command.execute(arguments, options);
	
	assertArrayEquals(expectedResult, fileSystem.getDirectoryContent("/home", DEFAULT).toArray());
    }
}
