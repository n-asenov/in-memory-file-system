package fileSystem.commands;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fileSystem.Path;
import fileSystem.fs.FileSystem;

public class MakeDirectoryTest {

	@Test
	public void execute_MakeDirectoryWithOption_ReturnErrorMessage() {
		MakeDirectory mkdir = new MakeDirectory(new FileSystem(), new Path());

		List<String> options = new ArrayList<String>();
		options.add("-l");
		List<String> arguments = new ArrayList<String>();
		arguments.add("/home/dir1");

		assertEquals("Invalid option!", mkdir.execute(options, arguments));
	}

	@Test
	public void execute_NewDirectory_DirectoryAddedToFileSystem() {
		MakeDirectory mkdir = new MakeDirectory(new FileSystem(), new Path());

		List<String> options = new ArrayList<String>();
		List<String> arguments = new ArrayList<String>();
		arguments.add("/home/dir1");

		assertEquals("", mkdir.execute(options, arguments));
	}
	
	@Test
	public void execute_NewDirectoryWithRelativePath_DirectoryAddedToFileSystem() {
		MakeDirectory mkdir = new MakeDirectory(new FileSystem(), new Path());
		
		List<String> options = new ArrayList<String>();
		List<String> arguments = new ArrayList<String>();
		arguments.add("dir1");
		
		assertEquals("", mkdir.execute(options, arguments));
	}

	@Test
	public void execute_MakeExistingDirectory_ReturnErrorMessage() {
		MakeDirectory mkdir = new MakeDirectory(new FileSystem(), new Path());
		String dir = "/home";
		
		List<String> options = new ArrayList<String>();
		List<String> arguments = new ArrayList<String>();
		arguments.add(dir);

		String result = mkdir.execute(options, arguments);
		String expectedResult = "Cannot create directory: " + dir + " : File already exists!";

		assertEquals(expectedResult, result);
	}

	@Test
	public void execute_MakeDirectoryWithNonExistentPath_ReturnErrorMessage() {
		MakeDirectory mkdir = new MakeDirectory(new FileSystem(), new Path());
		String dir = "/home/dir1/dir2";
		
		List<String> options = new ArrayList<String>();
		List<String> arguments = new ArrayList<String>();
		arguments.add(dir);
		
		String result = mkdir.execute(options, arguments);
		String expectedResult = "Cannot create directory: " + dir + " : Path doesn't exists!";
	
		assertEquals(expectedResult, result);
	}
}
