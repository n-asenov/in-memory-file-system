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

	String[] expectedDirectoryContent = { "f1" };
	assertArrayEquals(expectedDirectoryContent, getActualDirectoryContent());
    }

    private String[] getActualDirectoryContent() {
	List<String> directoryContent = directory.getContent(DEFAULT);
	return directoryContent.toArray(new String[0]);
    }

    @Test
    public void addFile_AddSeveralNewTextFilesInDirectory_TextFilesAddedInDirectory()
	    throws FileAlreadyExistsException {
	List<String> expectedDirectoryContent = new ArrayList<>();

	for (int i = 0; i < 10; i++) {
	    String fileName = "f" + i;
	    directory.addFile(new TextFile(fileName));
	    expectedDirectoryContent.add(fileName);
	}

	assertArrayEquals(expectedDirectoryContent.toArray(), getActualDirectoryContent());
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
	String directoryName = "dir1";
	Directory newSubDirectory = new Directory(directoryName);

	directory.addFile(newSubDirectory);

	String[] expectedDirectoryContent = { "dir1" };
	assertArrayEquals(expectedDirectoryContent, getActualDirectoryContent());
    }

    @Test(expected = FileAlreadyExistsException.class)
    public void addFile_addExistingDirectory_ThrowFileAlreadyExistsExceptionException()
	    throws FileAlreadyExistsException {
	Directory subDirectory = new Directory("dir1");

	directory.addFile(subDirectory);
	directory.addFile(subDirectory);
    }

    @Test
    public void getContent_getContentByDefaultOption_ContentSortedByName() throws FileAlreadyExistsException {
	List<String> expectedDirectoryContent = new ArrayList<>();

	for (int i = 0; i < 5; i++) {
	    Directory subDirectory = new Directory("dir" + i);
	    directory.addFile(subDirectory);
	    expectedDirectoryContent.add(subDirectory.getName());
	}

	assertArrayEquals(expectedDirectoryContent.toArray(), getActualDirectoryContent());
    }

    @Test
    public void getContent_getContentBySizeDescending_ReturnContentSortedBySizeDescending()
	    throws FileAlreadyExistsException {
	TextFile firstTextFile = new TextFile("f1");
	firstTextFile.overwrite(1, "hello");
	TextFile secondTextFile = new TextFile("f2");
	secondTextFile.overwrite(2, "Hello, World!");
	Directory firstSubDirectory = new Directory("dir1");
	Directory secondSubDirectory = new Directory("dir2");
	
	directory.addFile(firstTextFile);
	directory.addFile(secondTextFile);
	directory.addFile(firstSubDirectory);
	directory.addFile(secondSubDirectory);

	String[] expectedDirectoryContent = { "f2", "f1", "dir2", "dir1" };
	List<String> directoryContent = directory.getContent(SIZE_DESCENDING);
	String[] actualDirectoryContent = directoryContent.toArray(new String[0]);
	assertArrayEquals(expectedDirectoryContent, actualDirectoryContent);
    }

    @Test(expected = FileNotFoundException.class)
    public void removeTextFile_FileDoesNotExists_ThrowFileNotFoundException() throws FileNotFoundException {
	directory.removeTextFile("f1");
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
