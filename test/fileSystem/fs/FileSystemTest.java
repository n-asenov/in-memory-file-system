package fileSystem.fs;

import static org.junit.Assert.*;

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

		String absolutePath = "/";

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
	public void getDirectoryContent_NonExistingDirectory_ReturnErrorMessage() {
		FileSystem fs = new FileSystem();

		String absolutePath = "/home/dir1";

		String result = fs.getDirectoryContent(absolutePath, FilterBy.DEFAULT);
		String expectedResult = "File doesn't exists!";
		
		assertEquals(expectedResult, result);
	}

	@Test
	public void getDirectoryContent_PathDoesNotExists_ReturnErrorMessage() {
		FileSystem fs = new FileSystem();

		String absolutePath = "/home/dir1/dir2/";

		String result  = fs.getDirectoryContent(absolutePath, FilterBy.DEFAULT);
		String expectedResult = "Path doesn't exists!";
		
		assertEquals(expectedResult, result);
	}

	@Test
	public void getDirectoryContent_EmptyDirectory_ReturnEmptyString() {
		FileSystem fs = new FileSystem();

		String absolutePath = "/home";

		String result = fs.getDirectoryContent(absolutePath, FilterBy.DEFAULT);

		assertEquals("", result);
	}
	
	@Test
	public void getDirectoryContent_RootDirectory_ReturnRootContent() {
		FileSystem fs = new FileSystem();

		String absolutePath = "/";

		String result = fs.getDirectoryContent(absolutePath, FilterBy.DEFAULT);
		String expectedResult = "home ";
		
		assertEquals(expectedResult, result);
	}
	
	@Test
	public void isDirectory_ExistingDirectory_ReturnNull() {
		FileSystem fs = new FileSystem();
		
		assertEquals(null, fs.isDirectory("/home"));
	}
	
	@Test
	public void isDirectory_Root_ReturnNull() {
		FileSystem fs = new FileSystem();
		
		assertEquals(null, fs.isDirectory("/"));
	}
	
	@Test
	public void isDirectory_DirectoryWithWrongPath_ReturnPathDoesNotExistsMessage() {
		FileSystem fs = new FileSystem();
		
		assertEquals("Path doesn't exists!", fs.isDirectory("/home/dir1/dir2"));
	}
	
	@Test
	public void isDirectory_NonExistentDirectory_ReturnFileDoesNotExistsMessage() {
		FileSystem fs = new FileSystem();
		
		assertEquals("File doesn't exists!", fs.isDirectory("/home/dir1"));
	}
	
	@Test
	public void isDirectory_FileIsNotDirectory_ReturnFileIsNotDirectoryMessage() {
		FileSystem fs = new FileSystem();
		fs.createTextFile("/home/f1");
		
		assertEquals("File is text file!", fs.isDirectory("/home/f1"));
	}
}
