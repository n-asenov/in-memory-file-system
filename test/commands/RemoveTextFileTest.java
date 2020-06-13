package commands;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import commands.RemoveTextFile;
import filesystem.AbstractFileSystem;
import filesystem.FilterBy;
import filesystem.InvalidArgumentException;
import filesystem.VirtualFileSystem;
import path.Path;

public class RemoveTextFileTest {
    private RemoveTextFile command;
    private AbstractFileSystem fileSystem;
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

	command.execute(options, arguments);
    }

    @Test(expected = InvalidArgumentException.class)
    public void execute_WrongPathToFile_ThrowInvalidArgumentException() throws InvalidArgumentException, IOException {
	arguments.add("/home/dir1/f1");

	command.execute(options, arguments);
    }

    @Test(expected = FileNotFoundException.class)
    public void execute_TextFileDoesNotExists_ThrowFileNotFoudException() throws InvalidArgumentException, IOException {
	arguments.add("/home/f1");

	command.execute(options, arguments);
    }

    @Test(expected = FileNotFoundException.class)
    public void execute_FileIsDirectory_ThrowFileNotFoundException() throws InvalidArgumentException, IOException {
	arguments.add("/home");

	command.execute(options, arguments);
    }

    @Test
    public void execute_TextFile_RemoveTextFileFromDirectory() throws InvalidArgumentException, IOException {
	fileSystem.createTextFile("/home/f1");
	arguments.add("/home/f1");

	String[] expectedResult = {};
	assertNull(command.execute(options, arguments));
	assertArrayEquals(expectedResult, fileSystem.getDirectoryContent("/home", FilterBy.DEFAULT).toArray());
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
	assertNull(command.execute(options, arguments));
	assertArrayEquals(expectedResult, fileSystem.getDirectoryContent("/home", FilterBy.DEFAULT).toArray());
    }
}
