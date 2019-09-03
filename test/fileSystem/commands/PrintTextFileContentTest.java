package fileSystem.commands;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fileSystem.Path;
import fileSystem.commands.PrintTextFileContent;
import fileSystem.fs.FileSystem;
import fileSystem.output.ConsoleOutput;

public class PrintTextFileContentTest {

	@Test(expected = IllegalArgumentException.class)
	public void execute_CommandWithOption_ThrowIllegalArgumentException()
			throws InvalidPathException, IllegalArgumentException, FileNotFoundException, FileAlreadyExistsException {
		PrintTextFileContent command = new PrintTextFileContent(new FileSystem(), new Path(), new ConsoleOutput());

		List<String> options = new ArrayList<String>();
		options.add("-option");
		List<String> arguments = new ArrayList<String>();
		arguments.add("alabala");

		command.execute(options, arguments);
	}

	@Test(expected = InvalidPathException.class)
	public void execute_PathToTextFileIsInvalid_ThrowInvalidPathException()
			throws InvalidPathException, IllegalArgumentException, FileNotFoundException, FileAlreadyExistsException {
		PrintTextFileContent command = new PrintTextFileContent(new FileSystem(), new Path(), new ConsoleOutput());

		List<String> options = new ArrayList<String>();
		List<String> arguments = new ArrayList<String>();
		arguments.add("/home/dir1/f1");

		command.execute(options, arguments);
	}

	@Test(expected = FileNotFoundException.class)
	public void execute_FileDoesNotExsist_ThrowFileNotFoundException()
			throws InvalidPathException, IllegalArgumentException, FileNotFoundException, FileAlreadyExistsException {
		PrintTextFileContent command = new PrintTextFileContent(new FileSystem(), new Path(), new ConsoleOutput());

		List<String> options = new ArrayList<String>();
		List<String> arguments = new ArrayList<String>();
		arguments.add("/home/f1");

		command.execute(options, arguments);
	}

	@Test(expected = FileNotFoundException.class)
	public void execute_FileIsDirectory_ThrowFileNotFoundException()
			throws InvalidPathException, IllegalArgumentException, FileNotFoundException, FileAlreadyExistsException {
		PrintTextFileContent command = new PrintTextFileContent(new FileSystem(), new Path(), new ConsoleOutput());

		List<String> options = new ArrayList<String>();
		List<String> arguments = new ArrayList<String>();
		arguments.add("/home");

		command.execute(options, arguments);
	}

	@Test
	public void execute_EmptyTextFile_OutputEmptyString()
			throws InvalidPathException, IllegalArgumentException, FileNotFoundException, FileAlreadyExistsException {
		FileSystem fs = new FileSystem();
		fs.createTextFile("/home/f1");

		PrintTextFileContent command = new PrintTextFileContent(fs, new Path(), new ConsoleOutput());

		List<String> options = new ArrayList<String>();
		List<String> arguments = new ArrayList<String>();
		arguments.add("/home/f1");

		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));

		command.execute(options, arguments);

		assertEquals("", outContent.toString());

		System.setOut(System.out);
	}

	@Test
	public void execute_EmptyTextFileWithRelativePath_ReturnEmptyString()
			throws InvalidPathException, IllegalArgumentException, FileNotFoundException, FileAlreadyExistsException {
		FileSystem fs = new FileSystem();
		fs.createTextFile("/home/f1");

		PrintTextFileContent command = new PrintTextFileContent(fs, new Path(), new ConsoleOutput());

		List<String> options = new ArrayList<String>();
		List<String> arguments = new ArrayList<String>();
		arguments.add("f1");

		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));

		command.execute(options, arguments);

		assertEquals("", outContent.toString());

		System.setOut(System.out);
	}

}
