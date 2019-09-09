package fileSystem.fs;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.InvalidPathException;
import java.nio.file.NotDirectoryException;

import org.junit.Before;
import org.junit.Test;

public class FileSystemTest {
	private FileSystem fs;

	@Before
	public void init() throws FileAlreadyExistsException {
		fs = new FileSystem();
	}

	@Test
	public void makeDirectory_NewDirectory_DirectoryAddedToFileSystem()
			throws FileAlreadyExistsException, InvalidPathException, NotDirectoryException, FileNotFoundException {
		fs.makeDirectory("/home/newDir");

		assertEquals("newDir ", fs.getDirectoryContent("/home", FilterBy.DEFAULT));
	}

	@Test(expected = InvalidPathException.class)
	public void makeDirectory_PathDoesNotExists_ThrowInvalidPathException()
			throws FileAlreadyExistsException, InvalidPathException {
		fs.makeDirectory("/wrongPath/dir1");
	}

	@Test(expected = FileAlreadyExistsException.class)
	public void makeDirectory_AddExistingDirectory_ThrowFileAlreadyExistsException()
			throws FileAlreadyExistsException, InvalidPathException {
		fs.makeDirectory("/");
	}

	@Test
	public void createTextFile_NewTextFile_TextFileAddedInDirectory()
			throws FileAlreadyExistsException, InvalidPathException, NotDirectoryException, FileNotFoundException {
		fs.createTextFile("/home/f1");

		assertEquals("f1 ", fs.getDirectoryContent("/home", FilterBy.DEFAULT));
	}

	@Test(expected = InvalidPathException.class)
	public void createTextFile_PathDoesNotExists_ThrowInvalidPathException()
			throws FileAlreadyExistsException, InvalidPathException {
		fs.createTextFile("/wrongPath/f1");
	}

	@Test(expected = FileAlreadyExistsException.class)
	public void createTextFile_AddExistingFile_ThrowFileAlreadyExistsException()
			throws FileAlreadyExistsException, InvalidPathException {
		String absolutePath = "/home/f1";

		fs.createTextFile(absolutePath);
		fs.createTextFile(absolutePath);
	}

	@Test
	public void getTextFileContent_ExistingEmptyFile_ReturnEmptyString()
			throws FileAlreadyExistsException, InvalidPathException, FileNotFoundException {
		String absolutePath = "/home/f1";

		fs.createTextFile(absolutePath);

		assertEquals("", fs.getTextFileContent(absolutePath));
	}

	@Test(expected = InvalidPathException.class)
	public void getTextFileContent_PathDoesNotExists_ThrowInvalidPathException()
			throws InvalidPathException, FileNotFoundException {
		fs.getTextFileContent("/home/dir1/f1");
	}

	@Test(expected = FileNotFoundException.class)
	public void getTextFileContent_FileDoesNotExists_ThrowFileNotFoundException()
			throws InvalidPathException, FileNotFoundException {
		fs.getTextFileContent("/home/f1");
	}

	@Test(expected = FileNotFoundException.class)
	public void getTextFileContent_FileIsDirectory_ThrowFileNotFoundException()
			throws InvalidPathException, FileNotFoundException {
		fs.getTextFileContent("/home");
	}

	@Test(expected = FileNotFoundException.class)
	public void writeToTextFile_WriteToNonExistingFile_ThrowFileNotFoundException()
			throws InvalidPathException, FileNotFoundException, NotEnoughMemoryException {
		fs.writeToTextFile("/home/f1", 1, "Test", false);
	}

	@Test(expected = InvalidPathException.class)
	public void writeToTextFile_PathToFileDoesNotExists_ThrowInvalidPathException()
			throws InvalidPathException, FileNotFoundException, NotEnoughMemoryException {
		fs.writeToTextFile("/home/dir1/f1", 1, "Test", false);
	}

	@Test
	public void writeToTextFile_WriteToEmptyFile_ContentWrittenToFile()
			throws InvalidPathException, FileNotFoundException, FileAlreadyExistsException, NotEnoughMemoryException {
		String absolutePath = "/home/f1";

		fs.createTextFile(absolutePath);

		fs.writeToTextFile(absolutePath, 1, "Test", false);

		assertEquals("Test", fs.getTextFileContent(absolutePath));
		assertEquals(5, fs.getUsedMemory());
	}

	@Test(expected = FileNotFoundException.class)
	public void writeToTextFile_FileIsDirectory_ThrowFileNotFoundException()
			throws InvalidPathException, FileNotFoundException, NotEnoughMemoryException {
		fs.writeToTextFile("/home", 1, "hello", false);
	}

	@Test(expected = NotEnoughMemoryException.class)
	public void writeToTextFile_NotEnoughSpaceInFileSystem_ThrowNotEnoughMemoryException()
			throws FileAlreadyExistsException, InvalidPathException, FileNotFoundException, NotEnoughMemoryException {
		String absolutePath = "/home/f1";
		fs.createTextFile(absolutePath);
		assertEquals(0, fs.getUsedMemory());

		StringBuilder content = new StringBuilder();

		for (int i = 0; i <= FileSystem.CAPACITY; i++) {
			content.append('a');
		}
		
		fs.writeToTextFile(absolutePath, 1, content.toString(), false);
	}
	
	@Test
	public void writeToTextFile_OverwriteLine_NewContentAddedToTextFile() throws FileAlreadyExistsException, InvalidPathException, FileNotFoundException, NotEnoughMemoryException {
		String absolutePath = "/home/f1";
		
		fs.createTextFile(absolutePath);
		fs.writeToTextFile(absolutePath, 1, "Hello", false);
		assertEquals(6, fs.getUsedMemory());
		
		fs.writeToTextFile(absolutePath, 1, "A", true);
		assertEquals(2, fs.getUsedMemory());
	}
	
	@Test
	public void writeToTextFile_AppendLine_NewContentAddedToTextFile() throws FileAlreadyExistsException, InvalidPathException, FileNotFoundException, NotEnoughMemoryException {
		String absolutePath = "/home/f1";
		
		fs.createTextFile(absolutePath);
		fs.writeToTextFile(absolutePath, 1, "hello", false);
		assertEquals(6, fs.getUsedMemory());
		fs.writeToTextFile(absolutePath, 1, " world", false);
		assertEquals(12, fs.getUsedMemory());
	}
	
	@Test
	public void writeToTextFile_FreeEnoughSpaceByDeletingFilesToDelete_ContentWrittenToTextFile() throws FileAlreadyExistsException, InvalidPathException, FileNotFoundException, NotEnoughMemoryException {
		fs.createTextFile("/home/f1");
		fs.writeToTextFile("/home/f1", 1, "Hello", false);
		fs.removeTextFile("/home/f1");
		
		StringBuilder content = new StringBuilder();

		for (int i = 1; i < FileSystem.CAPACITY; i++) {
			content.append('a');
		}
		
		fs.createTextFile("/home/f2");
		fs.writeToTextFile("/home/f2", 1, content.toString(), false);
		
		assertEquals(content.toString(), fs.getTextFileContent("/home/f2"));
		assertEquals(FileSystem.CAPACITY, fs.getUsedMemory());
	}

	@Test(expected = FileNotFoundException.class)
	public void getDirectoryContent_NonExistingDirectory_ThrowFileNotFoundException()
			throws InvalidPathException, NotDirectoryException, FileNotFoundException {
		fs.getDirectoryContent("/home/dir1", FilterBy.DEFAULT);
	}

	@Test(expected = InvalidPathException.class)
	public void getDirectoryContent_PathDoesNotExists_ThrowInvalidPathException()
			throws InvalidPathException, NotDirectoryException, FileNotFoundException {
		fs.getDirectoryContent("/home/dir1/dir2/", FilterBy.DEFAULT);
	}

	@Test
	public void getDirectoryContent_EmptyDirectory_ReturnEmptyString()
			throws InvalidPathException, NotDirectoryException, FileNotFoundException {
		assertEquals("", fs.getDirectoryContent("/home", FilterBy.DEFAULT));
	}

	@Test
	public void getDirectoryContent_RootDirectory_ReturnRootContent()
			throws InvalidPathException, NotDirectoryException, FileNotFoundException {
		assertEquals("home ", fs.getDirectoryContent("/", FilterBy.DEFAULT));
	}

	@Test
	public void isDirectory_ExistingDirectory_True() throws InvalidPathException, FileNotFoundException {
		assertTrue(fs.isDirectory("/home"));
	}

	@Test
	public void isDirectory_Root_True() throws InvalidPathException, FileNotFoundException {
		assertTrue(fs.isDirectory("/"));
	}

	@Test(expected = InvalidPathException.class)
	public void isDirectory_DirectoryWithWrongPath_ThrowInvalidPathException()
			throws InvalidPathException, FileNotFoundException {
		fs.isDirectory("/home/dir1/dir2");
	}

	@Test(expected = FileNotFoundException.class)
	public void isDirectory_NonExistentDirectory_ThrowFileNotFoundException()
			throws InvalidPathException, FileNotFoundException {
		assertEquals("File doesn't exists!", fs.isDirectory("/home/dir1"));
	}

	@Test
	public void isDirectory_FileIsNotDirectory_False()
			throws InvalidPathException, FileNotFoundException, FileAlreadyExistsException {
		String absolutePath = "/home/f1";

		fs.createTextFile(absolutePath);

		assertFalse(fs.isDirectory(absolutePath));
	}
}
