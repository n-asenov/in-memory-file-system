package fileSystem.commands;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fileSystem.fs.FileSystem;

public class WriteToTextFileTest {

	@Test
	public void execute_CommandWithInvalidOption_ReturnInvalidOptionMessage() {
		WriteToTextFile command = new WriteToTextFile(new FileSystem());

		List<String> options = new ArrayList<String>();
		options.add("append");
		List<String> arguments = new ArrayList<String>();

		String result = command.execute(options, arguments);
		String expectedResult = "Invalid option!";

		assertEquals(expectedResult, result);
	}

	@Test
	public void execute_CommandWithInvalidNumberOfArguments_ReturnInvalidNumberOfArgumentsMessage() {
		WriteToTextFile command = new WriteToTextFile(new FileSystem());

		List<String> options = new ArrayList<String>();
		List<String> arguments = new ArrayList<String>();
		arguments.add("/home/f1");

		String result = command.execute(options, arguments);
		String expectedResult = "Invalid number of arguments!";

		assertEquals(expectedResult, result);
	}

	@Test
	public void execute_CommnadWithWrongSecondArgument_ReturnSecondArgumentErrorMessage() {
		WriteToTextFile command = new WriteToTextFile(new FileSystem());

		List<String> options = new ArrayList<String>();
		List<String> arguments = new ArrayList<String>();
		arguments.add("/home/f1");
		arguments.add("Number");
		arguments.add("Hello, World!");

		String result = command.execute(options, arguments);
		String expectedResult = "Second argument must be a positive number!";

		assertEquals(expectedResult, result);
	}

	@Test
	public void execute_ValidCommandWithNoOption_WriteContentToTextFile() {
		FileSystem fs = new FileSystem();
		fs.createTextFile("/home/f1");

		WriteToTextFile command = new WriteToTextFile(fs);

		List<String> options = new ArrayList<String>();
		List<String> arguments = new ArrayList<String>();
		arguments.add("/home/f1");
		arguments.add("2");
		arguments.add("Hello, World!");

		String result = command.execute(options, arguments);
		String expectedResult = null;

		assertEquals(expectedResult, result);
	}

	@Test
	public void execute_ValidCommandWithOverwriteOption_WriteContentToTextFile() {
		FileSystem fs = new FileSystem();
		fs.createTextFile("/home/f1");

		WriteToTextFile command = new WriteToTextFile(fs);

		List<String> options = new ArrayList<String>();
		options.add("overwrite");
		List<String> arguments = new ArrayList<String>();
		arguments.add("/home/f1");
		arguments.add("2");
		arguments.add("Hello, World!");

		String result = command.execute(options, arguments);
		String expectedResult = null;

		assertEquals(expectedResult, result);
	}
}
