package fileSystem.fs;

import static org.junit.Assert.*;

import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class DirectoryTest {
	private Directory directory;

	@Before
	public void init() {
		directory = new Directory("test");
	}

	@Test
	public void addFile_AddNewTextFileInDirectory_TextFileAddedInDirectory() throws FileAlreadyExistsException {
		directory.addFile(new TextFile("f1"));

		List<String> expectedResult = new ArrayList<String>();
		expectedResult.add("f1");
		
		assertArrayEquals(expectedResult.toArray(), directory.getContent(FilterBy.DEFAULT).toArray());
	}

	@Test
	public void addFile_AddSeveralNewTextFilesInDirectory_TextFilesAddedInDirectory()
			throws FileAlreadyExistsException {
		List<String> expectedResult = new ArrayList<String>();
		
		for (int i = 0; i < 10; i++) {
			String fileName = "f" + i;
			directory.addFile(new TextFile(fileName));
			expectedResult.add(fileName);
		}
		
		assertArrayEquals(expectedResult.toArray(), directory.getContent(FilterBy.DEFAULT).toArray());
	}

	@Test(expected = FileAlreadyExistsException.class)
	public void addFile_AddExistingTextFile_ThrowFileAlreadyExistsExceptionException()
			throws FileAlreadyExistsException {
		File file = new TextFile("f1");

		directory.addFile(file);

		directory.addFile(file);
	}

	@Test
	public void addFile_addNewDirectory_DirectoryAddedToContent() throws FileAlreadyExistsException {
		String dirName = "dir1";
		Directory newSubDirectory = new Directory(dirName);
		
		List<String> expectedResult = new ArrayList<String>();
		expectedResult.add(dirName);
		
		directory.addFile(newSubDirectory);
	
		assertArrayEquals(expectedResult.toArray(), directory.getContent(FilterBy.DEFAULT).toArray());
	}

	@Test(expected = FileAlreadyExistsException.class)
	public void addFile_addExistingDirectory_ThrowFileAlreadyExistsExceptionException()
			throws FileAlreadyExistsException {
		Directory subDirectoryName = new Directory("dir1");

		directory.addFile(subDirectoryName);
		directory.addFile(subDirectoryName);
	}

	@Test
	public void getContent_getContentByDefaultOption_ContentSortedByName() throws FileAlreadyExistsException {
		List<String> expectedResult = new ArrayList<String>();

		for (int i = 0; i < 5; i++) {
			Directory dir = new Directory("dir" + i);
			expectedResult.add(dir.getName());
			directory.addFile(dir);
		}

		assertArrayEquals(expectedResult.toArray(), directory.getContent(FilterBy.DEFAULT).toArray());
	}

	@Test
	public void getContent_getContentBySizeDescending_ReturnContentSortedBySizeDescending()
			throws FileAlreadyExistsException {
		TextFile f1 = new TextFile("f1");
		f1.write(1, "hello", false);
		TextFile f2 = new TextFile("f2");
		f2.write(2, "Hello, World!", false);
		Directory dir = new Directory("dir1");

		directory.addFile(f1);
		directory.addFile(f2);
		directory.addFile(dir);

		List<String> expectedResult = new ArrayList<String>();
		expectedResult.add(f2.getName());
		expectedResult.add(f1.getName());
		expectedResult.add(dir.getName());

		assertArrayEquals(expectedResult.toArray(), directory.getContent(FilterBy.SIZE_DESCENDING).toArray());
	}
}
