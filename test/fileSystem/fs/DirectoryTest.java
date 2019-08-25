package fileSystem.fs;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class DirectoryTest {
	@Test
	public void addFile_AddNewTextFileInDirectory_TextFileAddedInDirectory() {
		Directory directory = new Directory("test");
		File file = new TextFile("f1");

		String error = directory.addFile(file);

		String expectedError = null;
		int expectedFilesSize = 1;
		int expectedDirectorySize = 0;

		List<String> list = directory.getContent(FilterBy.DEFAULT);

		assertEquals(expectedError, error);
		assertEquals(expectedFilesSize, list.size());
		assertEquals(file.getName(), list.get(0));
		assertEquals(expectedDirectorySize, directory.getSize());
	}

	@Test
	public void addFile_AddSeveralNewTextFilesInDirectory_TextFilesAddedInDirectory() {
		Directory directory = new Directory("test");

		int numberOfFiles = 10;

		int expectedDirectorySize = 0;
		int expectedFilesSize = 0;
		String expectedError = null;

		for (int i = 0; i < numberOfFiles; i++) {
			File file = new TextFile("f" + i);
			String error = directory.addFile(file);
			assertEquals(expectedError, error);
			expectedFilesSize++;
		}

		List<String> list = directory.getContent(FilterBy.DEFAULT);

		assertEquals(expectedDirectorySize, directory.getSize());
		assertEquals(expectedFilesSize, list.size());

		for (int i = 0; i < numberOfFiles; i++) {
			String expectedResult = "f" + i;
			assertEquals(expectedResult, list.get(i));
		}
	}

	@Test
	public void addFile_AddExistingTextFile_ReturnErrorMessage() {
		Directory directory = new Directory("test");

		File file = new TextFile("f1");

		String error;
		String expectedError = null;

		error = directory.addFile(file);
		assertEquals(expectedError, error);

		error = directory.addFile(file);
		expectedError = "File already exists!";

		assertEquals(expectedError, error);
	}

	@Test
	public void addFile_addNewDirectory_DirectoryAddedToContent() {
		Directory directory = new Directory("test");

		Directory newSubDirectory = new Directory("dir1");

		String result;
		String expectedResult = null;

		result = directory.addFile(newSubDirectory);

		assertEquals(expectedResult, result);
	}

	@Test
	public void addFile_addExistingDirectory_ReturnErrorMessage() {
		Directory directory = new Directory("Test");

		Directory subDirectoryName = new Directory("dir1");

		String expectedResult = "File already exists!";

		directory.addFile(subDirectoryName);
		String result = directory.addFile(subDirectoryName);

		assertEquals(expectedResult, result);
	}

	@Test
	public void getContent_getContentByDefaultOption_ContentSortedByName() {
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
	public void getContent_getContentBySizeDescending_ReturnContentSortedBySizeDescending() {
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
