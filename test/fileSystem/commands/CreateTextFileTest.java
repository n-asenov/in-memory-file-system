package fileSystem.commands;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fileSystem.Path;
import fileSystem.fs.FileSystem;

public class CreateTextFileTest {

	@Test
	public void execute_CommandWithOption_ReturnErrorMessage() {
		CreateTextFile commnand = new CreateTextFile(new FileSystem(), new Path());
		
		List<String> options = new ArrayList<String>();
		options.add("-l");
		List<String> arguments = new ArrayList<String>();
		arguments.add("/home/f1");
		
		assertEquals("Invalid option!", commnand.execute(options, arguments));
	}
	
	@Test
	public void execute_CreateAlreadyExistingFile_ReturnErrorMessage() {
		CreateTextFile command = new CreateTextFile(new FileSystem(), new Path());
		String fileName = "/home";
		
		List<String> options = new ArrayList<String>();
		List<String> arguments = new ArrayList<String>();
		arguments.add(fileName);
		
		String result = command.execute(options, arguments);
		String expectedResult = "Cannot create text file: " + fileName + " : File already exists!";
		
		assertEquals(expectedResult, result);
	}

	@Test
	public void execute_CreateTextFileWithWrongAbsolutePath_ReturnErrorMessage() {
		CreateTextFile command = new CreateTextFile(new FileSystem(), new Path());
		String file = "/home/dir1/f1";
		
		List<String> options = new ArrayList<String>();
		List<String> arguments = new ArrayList<String>();
		arguments.add(file);
		
		String result = command.execute(options, arguments);
		String expectedResult =  "Cannot create text file: " + file + " : Path doesn't exists!";
	
		assertEquals(expectedResult, result);
	}
	
	@Test
	public void execute_CreateNewTextFile_TextFileAddedToFileSystem() {
		CreateTextFile command = new CreateTextFile(new FileSystem(), new Path());
		String file = "/home/f1";
		
		List<String> options = new ArrayList<String>();
		List<String> arguments = new ArrayList<String>();
		arguments.add(file);
		
		assertEquals("", command.execute(options, arguments));
	}
	
	@Test
	public void execute_CreateTextFileWithRelativePath_TextFileAddedToFileSystem() {
		CreateTextFile command = new CreateTextFile(new FileSystem(), new Path());
		
		List<String> options = new ArrayList<String>();
		List<String> arguments = new ArrayList<String>();
		arguments.add("f1");
		
		assertEquals("", command.execute(options, arguments));
	}
	
	@Test
	public void execute_CreateSeveralTextFiles_TextFilesAddedToFileSystem() {
CreateTextFile command = new CreateTextFile(new FileSystem(), new Path());
		
		List<String> options = new ArrayList<String>();
		List<String> arguments = new ArrayList<String>();
		arguments.add("f1");
		arguments.add("f2");
		arguments.add("f3");
		arguments.add("/f1");
		
		assertEquals("", command.execute(options, arguments));
	}
}
