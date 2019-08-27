package fileSystem.commands;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fileSystem.fs.FileSystem;

public class ListDirectoryContentTest {

	@Test
	public void execute_CommandWithInvalidOptions_ReturnInvalidOptionMessage() {
		ListDirectoryContent ls = new ListDirectoryContent(new FileSystem());

		List<String> options = new ArrayList<String>();
		options.add("al");
		List<String> arguments = new ArrayList<String>();
		arguments.add("/home");

		String result = ls.execute(options, arguments);
		String expectedResult = "Invalid option!";

		assertEquals(expectedResult, result);
	}

	@Test
	public void execute_listEmptyDirectory_ReturnEmptyString() {
		ListDirectoryContent ls = new ListDirectoryContent(new FileSystem());

		List<String> options = new ArrayList<String>();
		List<String> arguments = new ArrayList<String>();
		arguments.add("/home");
		
		assertEquals("" + System.lineSeparator(), ls.execute(options, arguments));
	}
	
	@Test
	public void execute_listTwoDirectories_ReturnContetnOfDirectories() {
		ListDirectoryContent ls = new ListDirectoryContent(new FileSystem());

		List<String> options = new ArrayList<String>();
		List<String> arguments = new ArrayList<String>();
		arguments.add("/");
		arguments.add("/home");
		
		String result = ls.execute(options, arguments);
		String expectedResult = "home " + System.lineSeparator() + System.lineSeparator();
		
		assertEquals(expectedResult, result);
	}
	
	@Test
	public void execute_listDirectoryWithSortedDescOption_ReturnContentSortedDesc() {
		ListDirectoryContent ls = new ListDirectoryContent(new FileSystem());

		List<String> options = new ArrayList<String>();
		options.add("sortedDesc");
		List<String> arguments = new ArrayList<String>();
		arguments.add("/");
		
		String result = ls.execute(options, arguments);
		String expectedResult = "home " + System.lineSeparator();
		
		assertEquals(expectedResult, result);
	}
}