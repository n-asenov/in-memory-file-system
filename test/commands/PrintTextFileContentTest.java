package commands;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import commands.PrintTextFileContent;
import filesystem.VirtualFileSystem;
import filesystem.exceptions.InvalidArgumentException;
import path.Path;

public class PrintTextFileContentTest {
    private PrintTextFileContent command;
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
	command = new PrintTextFileContent(fs, new Path());
	options = new ArrayList<String>();
	arguments = new ArrayList<String>();
    }

    @Test(expected = InvalidArgumentException.class)
    public void execute_CommandWithOption_ThrowIllegalArgumentException() throws InvalidArgumentException, IOException {
	options.add("-option");

	command.execute(options, arguments);
    }

    @Test(expected = InvalidArgumentException.class)
    public void execute_PathToTextFileIsInvalid_ThrowInvalidPathException()
	    throws InvalidArgumentException, IOException {
	arguments.add("/home/dir1/f1");

	command.execute(options, arguments);
    }

    @Test(expected = FileNotFoundException.class)
    public void execute_FileDoesNotExsist_ThrowFileNotFoundException() throws InvalidArgumentException, IOException {
	arguments.add("/home/f2");

	command.execute(options, arguments);
    }

    @Test(expected = FileNotFoundException.class)
    public void execute_FileIsDirectory_ThrowFileNotFoundException() throws InvalidArgumentException, IOException {
	arguments.add("/home");

	command.execute(options, arguments);
    }

    @Test
    public void execute_EmptyTextFile_OutputEmptyString() throws InvalidArgumentException, IOException {
	arguments.add("/home/f1");

	assertEquals("", command.execute(options, arguments));
    }

    @Test
    public void execute_EmptyTextFileWithRelativePath_ReturnEmptyString() throws InvalidArgumentException, IOException {
	arguments.add("f1");

	assertEquals("", command.execute(options, arguments));
    }
}
