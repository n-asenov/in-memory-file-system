package fileSystem.fs;

import static org.junit.Assert.*;

import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class DirectoryTest {
	@Test
	public void addFile_AddNewTextFileInDirectory_TextFileAddedInDirectory() {
		Directory directory = new Directory("test");
		File file = new TextFile("f1");

		try {
			directory.addFile(file);
		} catch (FileAlreadyExistsException e) {
			assertFalse("Unexpected exception thrown", true);
		}
	}

	@Test
	public void addFile_AddSeveralNewTextFilesInDirectory_TextFilesAddedInDirectory() throws FileAlreadyExistsException {
		Directory directory = new Directory("test");

		int numberOfFiles = 10;

		for (int i = 0; i < numberOfFiles; i++) {
			File file = new TextFile("f" + i);
			directory.addFile(file);
		}
	}

	@Test(expected = FileAlreadyExistsException.class)
	public void addFile_AddExistingTextFile_ThrowFileAlreadyExistsExceptionException() throws FileAlreadyExistsException {
		Directory directory = new Directory("test");

		File file = new TextFile("f1");

		directory.addFile(file);

		directory.addFile(file);
	}

	@Test
	public void addFile_addNewDirectory_DirectoryAddedToContent() throws FileAlreadyExistsException {
		Directory directory = new Directory("test");

		Directory newSubDirectory = new Directory("dir1");

		directory.addFile(newSubDirectory);
	}

	@Test (expected = FileAlreadyExistsException.class)
	public void addFile_addExistingDirectory_ThrowFileAlreadyExistsExceptionException() throws FileAlreadyExistsException {
		Directory directory = new Directory("Test");

		Directory subDirectoryName = new Directory("dir1");

		directory.addFile(subDirectoryName);
		directory.addFile(subDirectoryName);
	}

	@Test
	public void getContent_getContentByDefaultOption_ContentSortedByName() throws FileAlreadyExistsException {
		Directory directory = new Directory("Test");

		int size = 5;

		List<String> expectedResult = new ArrayList<String>();

		for (int i = 0; i < size; i++) {
			Directory dir = new Directory("dir" + i);
			expectedResult.add(dir.getName());
			directory.addFile(dir);
		}
		
		List<String> result = directory.getContent(FilterBy.DEFAULT);

		assertArrayEquals(expectedResult.toArray(), result.toArray());
	}

	@Test
	public void getContent_getContentBySizeDescending_ReturnContentSortedBySizeDescending() throws FileAlreadyExistsException {
		Directory directory = new Directory("Test");

		TextFile f1 = new TextFile("f1");
		f1.write(1, "hello", false);
		TextFile f2 = new TextFile("f2");
		f2.write(2, "Hello, World!", false);
		Directory dir = new Directory("dir1");

		directory.addFile(f1);
		directory.addFile(f2);
		directory.addFile(dir);

		List<String> result = directory.getContent(FilterBy.SIZE_DESCENDING);

		List<String> expectedResult = new ArrayList<String>();

		expectedResult.add(f2.getName());
		expectedResult.add(f1.getName());
		expectedResult.add(dir.getName());

		assertArrayEquals(expectedResult.toArray(), result.toArray());
	}

}
