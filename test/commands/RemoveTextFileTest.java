package commands;

import static org.junit.Assert.assertArrayEquals;

import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import filesystem.File;
import filesystem.VirtualFileSystem;
import filesystem.exceptions.InvalidArgumentException;
import path.Path;

public class RemoveTextFileTest {
    private static final Comparator<File> DEFAULT = (f1, f2) -> f1.getName().compareTo(f2.getName());

    private VirtualFileSystem fileSystem;
    private RemoveTextFile command;
    private List<String> arguments;
    private Set<String> options;

    @Before
    public void init() {
	fileSystem = new VirtualFileSystem();
	command = new RemoveTextFile(fileSystem, new Path());
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
    public void execute_WrongPathToFile_ThrowInvalidArgumentException()
	    throws InvalidArgumentException, FileNotFoundException {
	arguments.add("/home/dir1/f1");

	command.execute(arguments, options);
    }

    @Test(expected = FileNotFoundException.class)
    public void execute_TextFileDoesNotExists_ThrowFileNotFoudException()
	    throws InvalidArgumentException, FileNotFoundException {
	arguments.add("/home/f1");

	command.execute(arguments, options);
    }

    @Test(expected = FileNotFoundException.class)
    public void execute_FileIsDirectory_ThrowFileNotFoundException()
	    throws InvalidArgumentException, FileNotFoundException {
	arguments.add("/home");

	command.execute(arguments, options);
    }

    @Test
    public void execute_TextFile_RemoveTextFileFromDirectory()
	    throws InvalidArgumentException, FileAlreadyExistsException, FileNotFoundException {
	String textFileAbsolutePath = "/home/f1";
	fileSystem.createTextFile(textFileAbsolutePath);
	arguments.add(textFileAbsolutePath);

	command.execute(arguments, options);

	String[] expectedResult = {};
	List<String> directoryContent = fileSystem.getDirectoryContent("/home", DEFAULT);
	String[] actualResult = directoryContent.toArray(new String[0]);
	assertArrayEquals(expectedResult, actualResult);
    }

    @Test
    public void execute_SeveralTextFiles_RemoveAllTextFiles()
	    throws InvalidArgumentException, FileAlreadyExistsException, FileNotFoundException {
	int numberOfFiles = 10;

	for (int i = 0; i < numberOfFiles; i++) {
	    String fileName = "/home/f" + i;
	    fileSystem.createTextFile(fileName);
	    arguments.add(fileName);
	}

	command.execute(arguments, options);
	
	String[] expectedResult = {};
	List<String> directoryContent = fileSystem.getDirectoryContent("/home", DEFAULT);
	String[] actualResult = directoryContent.toArray(new String[0]);
	assertArrayEquals(expectedResult, actualResult);
    }
}
