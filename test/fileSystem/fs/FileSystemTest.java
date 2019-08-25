package fileSystem.fs;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class FileSystemTest {

	@Test
	public void makeDirectory_newDirectory_DirectoryAddedToFileSystem() {
		FileSystem fs = new FileSystem();

		String absolutePathToNewDirectory = "/home/newDir";

		String result = fs.makeDirectory(absolutePathToNewDirectory);
		String expectedResult = null;

		assertEquals(expectedResult, result);
	}

	@Test
	public void makeDirectory_PathDoesNotExists_ReturnErrorMessage() {
		FileSystem fs = new FileSystem();

		String absolutePath = "/wrongPath/dir1";

		String result = fs.makeDirectory(absolutePath);
		String expectedResult = "Path doesn't exists!";

		assertEquals(expectedResult, result);
	}

	@Test
	public void makeDirectory_AddExistingDirectory_ReturnErrorMessage() {
		FileSystem fs = new FileSystem();

		String absolutePath = "/home/dir1";

		fs.makeDirectory(absolutePath);
		String result = fs.makeDirectory(absolutePath);
		String expectedResult = "File already exists!";

		assertEquals(expectedResult, result);
	}

	@Test
	public void createTextFile_NewFile_FileAddedInDirectory() {
		FileSystem fs = new FileSystem();

		String absolutePathToNewFile = "/home/f1";

		String result = fs.createTextFile(absolutePathToNewFile);
		String expectedResult = null;

		assertEquals(expectedResult, result);
	}

	@Test
	public void createTextFile_PathDoesNotExists_ReturnErrorMessage() {
		FileSystem fs = new FileSystem();

		String absolutePath = "/wrongPath/f1";

		String result = fs.createTextFile(absolutePath);
		String expectedResult = "Path doesn't exists!";

		assertEquals(expectedResult, result);
	}

	@Test
	public void createTextFile_AddExistingFile_ReturnErrorMessage() {
		FileSystem fs = new FileSystem();

		String absolutePath = "/home/f1";

		fs.createTextFile(absolutePath);
		String result = fs.createTextFile(absolutePath);
		String expectedResult = "File already exists!";

		assertEquals(expectedResult, result);
	}

	@Test
	public void getTextFileContent_ExistingEmptyFile_ReturnEmptyString() {
		FileSystem fs = new FileSystem();

		String absolutePath = "/home/f1";

		fs.createTextFile(absolutePath);

		String result = fs.getTextFileContent(absolutePath);
		String expectedResult = "";

		assertEquals(expectedResult, result);
	}

	@Test
	public void getTextFileContent_PathDoesNotExists_ReturnErrorMessage() {
		FileSystem fs = new FileSystem();

		String absolutePath = "/home/dir1/f1";

		fs.createTextFile(absolutePath);
		
		String result = fs.getTextFileContent(absolutePath);
		String expectedResult = "Path doesn't exists!";
		
		assertEquals(expectedResult, result);
	}

	@Test
	public void getTextFileContent_FileDoesNotExists_ReturnErrorMessage() {
		FileSystem fs = new FileSystem();

		String absolutePath = "/home/f1";
		
		String result = fs.getTextFileContent(absolutePath);
		String expectedResult = "File doesn't exists!";
		
		assertEquals(expectedResult, result);
	}
	
	@Test
	public void getTextFileContent_FileIsDirectory_ReturnErrorMessage() {
		FileSystem fs = new FileSystem();
		
		String absolutePath = "/home";
		
		String result = fs.getTextFileContent(absolutePath);
		String expectedResult = "File is directory!";
		
		assertEquals(expectedResult, result);
	}
	

	@Test
	public void writeToTextFile_WriteToNonExistingFile_ReturnErrorMessage() {
		FileSystem fs = new FileSystem();

		String absolutePath = "/home/f1";

		String result = fs.writeToTextFile(absolutePath, 1, "Test", false);
		String expectedResult = "File doesn't exists!";
		
		assertEquals(expectedResult, result);
	}

	@Test
	public void writeToTextFile_PathToFileDoesNotExists_ReturnErrorMessage() {
		FileSystem fs = new FileSystem();

		String absolutePath = "/home/dir1/f1";

		String result = fs.writeToTextFile(absolutePath, 1, "Test", false);
		String expectedResult = "Path doesn't exists!";
		
		assertEquals(expectedResult, result);
	}

	@Test
	public void writeToTextFile_WriteToEmptyFile_ContentWrittenToFile() {
		FileSystem fs = new FileSystem();

		String absolutePath = "/home/f1";

		fs.createTextFile(absolutePath);

		String result = fs.writeToTextFile(absolutePath, 1, "Test", false);
		String expectedResult = null;
		
		assertEquals(expectedResult, result);
	}

	@Test
	public void writeToTextFile_FileIsDirectory_ReturnErrorMessage() {
		FileSystem fs = new FileSystem();
		
		String absolutePath = "/home";
		
		String result = fs.writeToTextFile(absolutePath, 1, "hello", false);
		String expectedResult = "File is directory!";
		
		assertEquals(expectedResult, result);
	}
	
	@Test
	public void getDirectoryContent_NonExistingDirectory_ReturnNull() {
		FileSystem fs = new FileSystem();

		String absolutePath = "/home/dir1";

		List<String> result = fs.getDirectoryContent(absolutePath, FilterBy.DEFAULT);
		
		assertEquals(null, result);
	}

	@Test
	public void getDirectoryContent_PathDoesNotExists_ReturnNull() {
		FileSystem fs = new FileSystem();

		String absolutePath = "/home/dir1/dir2";

		List<String> result  = fs.getDirectoryContent(absolutePath, FilterBy.DEFAULT);

		assertEquals(null, result);
	}

	@Test
	public void getDirectoryContent_EmptyDirectory_ReturnEmptyList() {
		FileSystem fs = new FileSystem();

		String absolutePath = "/home";

		List<String> result = fs.getDirectoryContent(absolutePath, FilterBy.DEFAULT);

		assertEquals(0, result.size());
	}
}
