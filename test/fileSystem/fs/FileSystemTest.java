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
	public void init() {
		fs = new FileSystem();
	}

	@Test
	public void makeDirectory_NewDirectory_DirectoryAddedToFileSystem()
			throws FileAlreadyExistsException, NotDirectoryException, FileNotFoundException, InvalidArgumentException {
		fs.makeDirectory("/home/newDir");
		String[] expectedResult = { "newDir" };
		assertArrayEquals(expectedResult, fs.getDirectoryContent("/home", FilterBy.DEFAULT).toArray());
	}

	@Test(expected = InvalidArgumentException.class)
	public void makeDirectory_PathDoesNotExists_ThrowInvalidPathException()
			throws FileAlreadyExistsException, InvalidArgumentException {
		fs.makeDirectory("/wrongPath/dir1");
	}

	@Test(expected = FileAlreadyExistsException.class)
	public void makeDirectory_AddExistingDirectory_ThrowFileAlreadyExistsException()
			throws FileAlreadyExistsException, InvalidArgumentException {
		fs.makeDirectory("/");
	}

	@Test
	public void createTextFile_NewTextFile_TextFileAddedInDirectory()
			throws FileAlreadyExistsException, NotDirectoryException, FileNotFoundException, InvalidArgumentException {
		fs.createTextFile("/home/f1");
		String[] expectedResult = { "f1" };
		assertArrayEquals(expectedResult, fs.getDirectoryContent("/home", FilterBy.DEFAULT).toArray());
	}

	@Test(expected = InvalidArgumentException.class)
	public void createTextFile_PathDoesNotExists_ThrowInvalidPathException()
			throws FileAlreadyExistsException, InvalidArgumentException {
		fs.createTextFile("/wrongPath/f1");
	}

	@Test(expected = FileAlreadyExistsException.class)
	public void createTextFile_AddExistingFile_ThrowFileAlreadyExistsException()
			throws FileAlreadyExistsException, InvalidArgumentException {
		String absolutePath = "/home/f1";

		fs.createTextFile(absolutePath);
		fs.createTextFile(absolutePath);
	}

	@Test
	public void getTextFileContent_ExistingEmptyFile_ReturnEmptyString()
			throws FileAlreadyExistsException, FileNotFoundException, InvalidArgumentException {
		String absolutePath = "/home/f1";

		fs.createTextFile(absolutePath);

		assertEquals("", fs.getTextFileContent(absolutePath));
	}

	@Test(expected = InvalidArgumentException.class)
	public void getTextFileContent_PathDoesNotExists_ThrowInvalidPathException()
			throws FileNotFoundException, InvalidArgumentException {
		fs.getTextFileContent("/home/dir1/f1");
	}

	@Test(expected = FileNotFoundException.class)
	public void getTextFileContent_FileDoesNotExists_ThrowFileNotFoundException()
			throws FileNotFoundException, InvalidArgumentException {
		fs.getTextFileContent("/home/f1");
	}

	@Test(expected = FileNotFoundException.class)
	public void getTextFileContent_FileIsDirectory_ThrowFileNotFoundException()
			throws FileNotFoundException, InvalidArgumentException {
		fs.getTextFileContent("/home");
	}

	@Test(expected = FileNotFoundException.class)
	public void writeToTextFile_WriteToNonExistingFile_ThrowFileNotFoundException()
			throws FileNotFoundException, NotEnoughMemoryException, InvalidArgumentException {
		fs.writeToTextFile("/home/f1", 1, "Test", false);
	}

	@Test(expected = InvalidArgumentException.class)
	public void writeToTextFile_PathToFileDoesNotExists_ThrowInvalidPathException()
			throws FileNotFoundException, NotEnoughMemoryException, InvalidArgumentException {
		fs.writeToTextFile("/home/dir1/f1", 1, "Test", false);
	}

	@Test
	public void writeToTextFile_WriteToEmptyFile_ContentWrittenToFile() throws FileNotFoundException,
			FileAlreadyExistsException, NotEnoughMemoryException, InvalidArgumentException {
		String absolutePath = "/home/f1";

		fs.createTextFile(absolutePath);

		fs.writeToTextFile(absolutePath, 1, "Test", false);

		assertEquals("Test", fs.getTextFileContent(absolutePath));
		assertEquals(5, fs.getUsedMemory());
	}

	@Test(expected = FileNotFoundException.class)
	public void writeToTextFile_FileIsDirectory_ThrowFileNotFoundException()
			throws FileNotFoundException, NotEnoughMemoryException, InvalidArgumentException {
		fs.writeToTextFile("/home", 1, "hello", false);
	}

	@Test(expected = NotEnoughMemoryException.class)
	public void writeToTextFile_NotEnoughSpaceInFileSystem_ThrowNotEnoughMemoryException()
			throws FileAlreadyExistsException, FileNotFoundException, NotEnoughMemoryException,
			InvalidArgumentException {
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
	public void writeToTextFile_OverwriteLine_NewContentAddedToTextFile() throws FileAlreadyExistsException,
			FileNotFoundException, NotEnoughMemoryException, InvalidArgumentException {
		String absolutePath = "/home/f1";

		fs.createTextFile(absolutePath);
		fs.writeToTextFile(absolutePath, 1, "Hello", false);
		assertEquals(6, fs.getUsedMemory());

		fs.writeToTextFile(absolutePath, 1, "A", true);
		assertEquals(2, fs.getUsedMemory());
	}

	@Test
	public void writeToTextFile_AppendLine_NewContentAddedToTextFile() throws FileAlreadyExistsException,
			FileNotFoundException, NotEnoughMemoryException, InvalidArgumentException {
		String absolutePath = "/home/f1";

		fs.createTextFile(absolutePath);
		fs.writeToTextFile(absolutePath, 1, "hello", false);
		assertEquals(6, fs.getUsedMemory());
		fs.writeToTextFile(absolutePath, 1, " world", false);
		assertEquals(12, fs.getUsedMemory());
	}

	@Test
	public void writeToTextFile_FreeEnoughSpaceByDeletingFilesToDelete_ContentWrittenToTextFile()
			throws FileAlreadyExistsException, FileNotFoundException, NotEnoughMemoryException,
			InvalidArgumentException {
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
			throws NotDirectoryException, FileNotFoundException, InvalidArgumentException {
		fs.getDirectoryContent("/home/dir1", FilterBy.DEFAULT);
	}

	@Test(expected = InvalidArgumentException.class)
	public void getDirectoryContent_PathDoesNotExists_ThrowInvalidPathException()
			throws InvalidPathException, NotDirectoryException, FileNotFoundException, InvalidArgumentException {
		fs.getDirectoryContent("/home/dir1/dir2/", FilterBy.DEFAULT);
	}

	@Test
	public void getDirectoryContent_EmptyDirectory_ReturnEmptyString()
			throws NotDirectoryException, FileNotFoundException, InvalidArgumentException {
		String[] expectedResult = {};
		assertArrayEquals(expectedResult, fs.getDirectoryContent("/home", FilterBy.DEFAULT).toArray());
	}

	@Test
	public void getDirectoryContent_RootDirectory_ReturnRootContent()
			throws NotDirectoryException, FileNotFoundException, InvalidArgumentException {
		String[] expectedResult = { "home" };
		assertArrayEquals(expectedResult, fs.getDirectoryContent("/", FilterBy.DEFAULT).toArray());
	}

	@Test
	public void isDirectory_ExistingDirectory_True() throws FileNotFoundException, InvalidArgumentException {
		assertTrue(fs.isDirectory("/home"));
	}

	@Test
	public void isDirectory_Root_True() throws FileNotFoundException, InvalidArgumentException {
		assertTrue(fs.isDirectory("/"));
	}

	@Test(expected = InvalidArgumentException.class)
	public void isDirectory_DirectoryWithWrongPath_ThrowInvalidPathException()
			throws FileNotFoundException, InvalidArgumentException {
		fs.isDirectory("/home/dir1/dir2");
	}

	@Test(expected = FileNotFoundException.class)
	public void isDirectory_NonExistentDirectory_ThrowFileNotFoundException()
			throws FileNotFoundException, InvalidArgumentException {
		assertEquals("File doesn't exists!", fs.isDirectory("/home/dir1"));
	}

	@Test
	public void isDirectory_FileIsNotDirectory_False()
			throws FileNotFoundException, FileAlreadyExistsException, InvalidArgumentException {
		String absolutePath = "/home/f1";

		fs.createTextFile(absolutePath);

		assertFalse(fs.isDirectory(absolutePath));
	}

	@Test(expected = InvalidArgumentException.class)
	public void removeContentFromLinesInTextFile_InvalidPathToTextFile_ThrowInvalidPathExcepiton()
			throws InvalidPathException, FileNotFoundException, InvalidArgumentException {
		fs.removeContentFromLinesInTextFile("/home/dir1/f1", 1, 2);
	}

	@Test(expected = FileNotFoundException.class)
	public void removeContentFromLinesInTextFile_FileDoesNotExists_ThrowFileNotFoundException()
			throws FileNotFoundException, InvalidArgumentException {
		fs.removeContentFromLinesInTextFile("f1", 1, 2);
	}

	@Test(expected = FileNotFoundException.class)
	public void removeContentFromLinesInTextFile_FileIsDirectory_ThrowFileNotFoundException()
			throws FileNotFoundException, InvalidArgumentException {
		fs.removeContentFromLinesInTextFile("/home", 1, 2);
	}

	@Test
	public void removeContentFromLinesInTextFile_RemoveAllContentFromTextFile_EmptyTextFile()
			throws FileAlreadyExistsException, FileNotFoundException, NotEnoughMemoryException,
			InvalidArgumentException {
		fs.createTextFile("/home/f1");
		int start = 1;
		int end = 5;
		for (int i = start; i <= end; i++) {
			fs.writeToTextFile("/home/f1", i, "hello", false);
		}
		fs.removeContentFromLinesInTextFile("/home/f1", start, end);
		assertEquals("", fs.getTextFileContent("/home/f1"));
	}

	@Test(expected = InvalidArgumentException.class)
	public void getWordCount_WrongPathToTextFile_ThrowInvalidArgumentException() throws FileNotFoundException, InvalidArgumentException {
		fs.getWordCount("/home/dir1/f1");
	}
	
	@Test (expected = FileNotFoundException.class)
	public void getWordCount_TextFileDoesNotExists_ThrowFileNotFoundException() throws FileNotFoundException, InvalidArgumentException {
		fs.getWordCount("/home/f1");
	}
	
	@Test (expected = FileNotFoundException.class)
	public void getWordCount_Directory_ThrowFileNotFound() throws FileNotFoundException, InvalidArgumentException {
		fs.getWordCount("/home");
	}
	
	@Test
	public void getWordCount_EmptyTextFile_ReturnNumberOfWordsInTextFile() throws FileAlreadyExistsException, InvalidArgumentException, FileNotFoundException {
		fs.createTextFile("/home/f1");
		assertEquals((Integer)0, fs.getWordCount("/home/f1"));
	}
	
	@Test
	public void getWordCount_TextFile_ReturnNumberOfWordsInTextFile() throws FileAlreadyExistsException, InvalidArgumentException, FileNotFoundException, NotEnoughMemoryException {
		String absolutePath = "/home/f1";
		fs.createTextFile(absolutePath);
		fs.writeToTextFile(absolutePath, 1, "Hello World", false);
		assertEquals((Integer)2, fs.getWordCount(absolutePath));
	}
	
	@Test (expected = InvalidArgumentException.class)
	public void getLineCount_WrongPathToTextFile_ThrowInvalidArgumentException() throws FileNotFoundException, InvalidArgumentException {
		fs.getLineCount("/home/dir1/f1");
	}
	
	@Test (expected = FileNotFoundException.class)
	public void getLineContent_TextFileDoesNotExists_ThrowFileNotFoundException() throws FileNotFoundException, InvalidArgumentException {
		fs.getLineCount("/home/f1");
	}
	
	@Test (expected = FileNotFoundException.class)
	public void getLineContent_FileIsDirectory_ThrowFileNotFoundException() throws FileNotFoundException, InvalidArgumentException {
		fs.getLineCount("/home");
	}
	
	@Test
	public void getLineContent_TextFile_ReturnNumberOfLinesInTextFile() throws FileNotFoundException, NotEnoughMemoryException, InvalidArgumentException, FileAlreadyExistsException {
		String absolutePath = "/home/f1";
		fs.createTextFile(absolutePath);
		fs.writeToTextFile(absolutePath, 5, "hello world", false);
		assertEquals((Integer)5, fs.getLineCount(absolutePath));
	}
}
