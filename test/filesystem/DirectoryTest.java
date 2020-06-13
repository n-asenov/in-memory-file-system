package filesystem;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNull;

import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import filesystem.exceptions.InvalidArgumentException;

public class DirectoryTest {
    private static final Comparator<File> DEFAULT = (f1, f2) -> f1.getName().compareTo(f2.getName());
    private static final Comparator<File> SIZE_DESCENDING = (f1, f2) -> Integer.compare(f2.getSize(), f1.getSize());
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

	assertArrayEquals(expectedResult.toArray(), directory.getContent(DEFAULT).toArray());
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

	assertArrayEquals(expectedResult.toArray(), directory.getContent(DEFAULT).toArray());
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

	assertArrayEquals(expectedResult.toArray(), directory.getContent(DEFAULT).toArray());
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

	assertArrayEquals(expectedResult.toArray(), directory.getContent(DEFAULT).toArray());
    }

    @Test
    public void getContent_getContentBySizeDescending_ReturnContentSortedBySizeDescending()
	    throws FileAlreadyExistsException, InvalidArgumentException {
	TextFile f1 = new TextFile("f1");
	f1.overwrite(1, "hello");
	TextFile f2 = new TextFile("f2");
	f2.overwrite(2, "Hello, World!");
	Directory dir = new Directory("dir1");

	directory.addFile(f1);
	directory.addFile(f2);
	directory.addFile(dir);

	List<String> expectedResult = new ArrayList<String>();
	expectedResult.add(f2.getName());
	expectedResult.add(f1.getName());
	expectedResult.add(dir.getName());

	assertArrayEquals(expectedResult.toArray(), directory.getContent(SIZE_DESCENDING).toArray());
    }

    @Test(expected = FileNotFoundException.class)
    public void removeTextFile_FileDoesNotExists_ThrowFileNotFoundException() throws FileNotFoundException {
	directory.removeTextFile("dir1");
    }

    @Test(expected = FileNotFoundException.class)
    public void removeTextFile_FileIsDirectory_ThrowFileNotFoundException()
	    throws FileAlreadyExistsException, FileNotFoundException {
	String fileName = "dir1";
	directory.addFile(new Directory(fileName, directory));
	directory.removeTextFile(fileName);
    }

    @Test
    public void removeTextFile_TextFile_RemoveTextFileFromDirectory()
	    throws FileNotFoundException, FileAlreadyExistsException {
	String fileName = "f1";
	directory.addFile(new TextFile(fileName));
	directory.removeTextFile(fileName);
	assertNull(directory.getFile(fileName));
    }
}
