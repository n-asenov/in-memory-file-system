package fileSystem.commands;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fileSystem.commands.PrintTextFileContent;
import fileSystem.fs.FileSystem;

public class PrintTextFileContentTest {

	@Test
	public void execute_CommandWithOption_ReturnInvalidOptionError() {
		PrintTextFileContent command = new PrintTextFileContent(new FileSystem());

		List<String> options = new ArrayList<String>();
		options.add("-option");
		List<String> arguments = new ArrayList<String>();
		arguments.add("alabala");

		String result = command.execute(options, arguments);
		String expectedResult = "Invalid option";

		assertEquals(expectedResult, result);
	}

	@Test
	public void execute_PathToTextFileIsInvalid_ReturnErrorMessage() {
		PrintTextFileContent command = new PrintTextFileContent(new FileSystem());

		List<String> options = new ArrayList<String>();
		List<String> arguments = new ArrayList<String>();
		arguments.add("/home/dir1/f1");

		String result = command.execute(options, arguments);
		String expectedResult = "Path doesn't exists!";

		assertEquals(expectedResult, result);
	}

	@Test
	public void execute_FileDoesNotExsist_ReturnErrorMessage() {
		PrintTextFileContent command = new PrintTextFileContent(new FileSystem());

		List<String> options = new ArrayList<String>();
		List<String> arguments = new ArrayList<String>();
		arguments.add("/home/f1");

		String result = command.execute(options, arguments);
		String expectedResult = "File doesn't exists!";

		assertEquals(expectedResult, result);
	}

	@Test
	public void execute_FileIsDirectory_ReturnErrorMessage() {
		PrintTextFileContent command = new PrintTextFileContent(new FileSystem());

		List<String> options = new ArrayList<String>();
		List<String> arguments = new ArrayList<String>();
		arguments.add("/home");

		String result = command.execute(options, arguments);
		String expectedResult = "File is directory!";

		assertEquals(expectedResult, result);
	}

	@Test
	public void execute_EmptyTextFile_ReturnEmptyString() {
		FileSystem fs = new FileSystem();
		fs.createTextFile("/home/f1");
		
		PrintTextFileContent command = new PrintTextFileContent(fs);

		List<String> options = new ArrayList<String>();
		List<String> arguments = new ArrayList<String>();
		arguments.add("/home/f1");

		String result = command.execute(options, arguments);
		String expectedResult = "";

		assertEquals(expectedResult, result);
	}
}
