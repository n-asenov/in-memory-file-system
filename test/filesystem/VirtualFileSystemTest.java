package filesystem;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.InvalidPathException;
import java.nio.file.NotDirectoryException;
import java.util.Comparator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import commands.exception.InvalidArgumentException;
import filesystem.exceptions.NotEnoughMemoryException;

public class VirtualFileSystemTest {
  private static final Comparator<File> DEFAULT = (f1, f2) -> f1.getName().compareTo(f2.getName());

  private VirtualFileSystem fileSystem;

  @Before
  public void init() {
    fileSystem = new VirtualFileSystem();
  }

  @Test
  public void makeDirectory_NewDirectory_DirectoryAddedToFileSystem()
      throws FileAlreadyExistsException, FileNotFoundException, InvalidArgumentException {
    fileSystem.makeDirectory("/home/newDir");

    String[] expectedResult = {"newDir"};
    assertArrayEquals(expectedResult, getActualDirectoryContent("/home"));
  }

  private String[] getActualDirectoryContent(String directoryAbsolutePath)
      throws FileNotFoundException, InvalidArgumentException {
    List<String> directoryContent = fileSystem.getDirectoryContent(directoryAbsolutePath, DEFAULT);
    return directoryContent.toArray(new String[0]);
  }

  @Test(expected = InvalidArgumentException.class)
  public void makeDirectory_PathDoesNotExists_ThrowInvalidPathException()
      throws FileAlreadyExistsException, InvalidArgumentException {
    fileSystem.makeDirectory("/wrongPath/dir1");
  }

  @Test(expected = FileAlreadyExistsException.class)
  public void makeDirectory_AddExistingDirectory_ThrowFileAlreadyExistsException()
      throws FileAlreadyExistsException, InvalidArgumentException {
    fileSystem.makeDirectory("/");
  }

  @Test
  public void createTextFile_NewTextFile_TextFileAddedInDirectory()
      throws FileAlreadyExistsException, FileNotFoundException, InvalidArgumentException {
    fileSystem.createTextFile("/home/f1");
    String[] expectedResult = {"f1"};
    assertArrayEquals(expectedResult, getActualDirectoryContent("/home"));
  }

  @Test(expected = InvalidArgumentException.class)
  public void createTextFile_PathDoesNotExists_ThrowInvalidPathException()
      throws FileAlreadyExistsException, InvalidArgumentException {
    fileSystem.createTextFile("/wrongPath/f1");
  }

  @Test(expected = FileAlreadyExistsException.class)
  public void createTextFile_AddExistingFile_ThrowFileAlreadyExistsException()
      throws FileAlreadyExistsException, InvalidArgumentException {
    String absolutePath = "/home/f1";

    fileSystem.createTextFile(absolutePath);
    fileSystem.createTextFile(absolutePath);
  }

  @Test
  public void getTextFileContent_ExistingEmptyFile_ReturnEmptyString()
      throws FileAlreadyExistsException, FileNotFoundException, InvalidArgumentException {
    String absolutePath = "/home/f1";

    fileSystem.createTextFile(absolutePath);

    assertEquals("", fileSystem.getTextFileContent(absolutePath));
  }

  @Test(expected = InvalidArgumentException.class)
  public void getTextFileContent_PathDoesNotExists_ThrowInvalidPathException()
      throws FileNotFoundException, InvalidArgumentException {
    fileSystem.getTextFileContent("/home/dir1/f1");
  }

  @Test(expected = FileNotFoundException.class)
  public void getTextFileContent_FileDoesNotExists_ThrowFileNotFoundException()
      throws FileNotFoundException, InvalidArgumentException {
    fileSystem.getTextFileContent("/home/f1");
  }

  @Test(expected = FileNotFoundException.class)
  public void getTextFileContent_FileIsDirectory_ThrowFileNotFoundException()
      throws FileNotFoundException, InvalidArgumentException {
    fileSystem.getTextFileContent("/home");
  }

  @Test(expected = FileNotFoundException.class)
  public void writeToTextFile_WriteToNonExistingFile_ThrowFileNotFoundException()
      throws FileNotFoundException, NotEnoughMemoryException, InvalidArgumentException {
    fileSystem.writeToTextFile("/home/f1", 1, "Test");
  }

  @Test(expected = InvalidArgumentException.class)
  public void writeToTextFile_PathToFileDoesNotExists_ThrowInvalidPathException()
      throws FileNotFoundException, NotEnoughMemoryException, InvalidArgumentException {
    fileSystem.writeToTextFile("/home/dir1/f1", 1, "Test");
  }

  @Test
  public void writeToTextFile_WriteToEmptyFile_ContentWrittenToFile()
      throws FileNotFoundException, FileAlreadyExistsException, NotEnoughMemoryException,
          InvalidArgumentException {
    String absolutePath = "/home/f1";

    fileSystem.createTextFile(absolutePath);
    fileSystem.writeToTextFile(absolutePath, 1, "Test");

    assertEquals("Test", fileSystem.getTextFileContent(absolutePath));
    assertEquals(5, fileSystem.getUsedMemory());
  }

  @Test(expected = FileNotFoundException.class)
  public void writeToTextFile_FileIsDirectory_ThrowFileNotFoundException()
      throws FileNotFoundException, NotEnoughMemoryException, InvalidArgumentException {
    fileSystem.writeToTextFile("/home", 1, "hello");
  }

  @Test(expected = NotEnoughMemoryException.class)
  public void writeToTextFile_NotEnoughSpaceInFileSystem_ThrowNotEnoughMemoryException()
      throws FileAlreadyExistsException, FileNotFoundException, NotEnoughMemoryException,
          InvalidArgumentException {
    String absolutePath = "/home/f1";
    fileSystem.createTextFile(absolutePath);

    StringBuilder content = new StringBuilder();

    for (int i = 0; i <= VirtualFileSystem.MEMORY_CAPACITY; i++) {
      content.append('a');
    }

    fileSystem.writeToTextFile(absolutePath, 1, content.toString());
  }

  @Test
  public void writeToTextFile_OverwriteLine_NewContentAddedToTextFile()
      throws FileAlreadyExistsException, FileNotFoundException, NotEnoughMemoryException,
          InvalidArgumentException {
    String absolutePath = "/home/f1";

    fileSystem.createTextFile(absolutePath);
    fileSystem.writeToTextFile(absolutePath, 1, "Hello");
    assertEquals(6, fileSystem.getUsedMemory());

    fileSystem.writeToTextFile(absolutePath, 1, "A");
    assertEquals(2, fileSystem.getUsedMemory());
  }

  @Test
  public void writeToTextFile_AppendLine_NewContentAddedToTextFile()
      throws FileAlreadyExistsException, FileNotFoundException, NotEnoughMemoryException,
          InvalidArgumentException {
    String absolutePath = "/home/f1";

    fileSystem.createTextFile(absolutePath);
    fileSystem.writeToTextFile(absolutePath, 1, "hello");
    assertEquals(6, fileSystem.getUsedMemory());
    fileSystem.appendToTextFile(absolutePath, 1, " world");
    assertEquals(12, fileSystem.getUsedMemory());
  }

  @Test
  public void writeToTextFile_FreeEnoughSpaceByDeletingFilesToDelete_ContentWrittenToTextFile()
      throws FileAlreadyExistsException, FileNotFoundException, NotEnoughMemoryException,
          InvalidArgumentException {
    fileSystem.createTextFile("/home/f1");
    fileSystem.writeToTextFile("/home/f1", 1, "Hello");
    fileSystem.removeTextFile("/home/f1");

    StringBuilder content = new StringBuilder();

    for (int i = 1; i < VirtualFileSystem.MEMORY_CAPACITY; i++) {
      content.append('a');
    }

    fileSystem.createTextFile("/home/f2");
    fileSystem.writeToTextFile("/home/f2", 1, content.toString());

    assertEquals(content.toString(), fileSystem.getTextFileContent("/home/f2"));
    assertEquals(VirtualFileSystem.MEMORY_CAPACITY, fileSystem.getUsedMemory());
  }

  @Test(expected = FileNotFoundException.class)
  public void getDirectoryContent_NonExistingDirectory_ThrowFileNotFoundException()
      throws NotDirectoryException, FileNotFoundException, InvalidArgumentException {
    fileSystem.getDirectoryContent("/home/dir1", DEFAULT);
  }

  @Test(expected = InvalidArgumentException.class)
  public void getDirectoryContent_PathDoesNotExists_ThrowInvalidPathException()
      throws InvalidPathException, FileNotFoundException, InvalidArgumentException {
    fileSystem.getDirectoryContent("/home/dir1/dir2/", DEFAULT);
  }

  @Test
  public void getDirectoryContent_EmptyDirectory_ReturnEmptyString()
      throws FileNotFoundException, InvalidArgumentException {
    String[] expectedResult = {};
    assertArrayEquals(expectedResult, getActualDirectoryContent("/home"));
  }

  @Test
  public void getDirectoryContent_RootDirectory_ReturnRootContent()
      throws NotDirectoryException, FileNotFoundException, InvalidArgumentException {
    String[] expectedResult = {"home"};
    assertArrayEquals(expectedResult, getActualDirectoryContent("/"));
  }

  @Test
  public void isDirectory_ExistingDirectory_True()
      throws FileNotFoundException, InvalidArgumentException {
    assertTrue(fileSystem.isDirectory("/home"));
  }

  @Test
  public void isDirectory_Root_True() throws FileNotFoundException, InvalidArgumentException {
    assertTrue(fileSystem.isDirectory("/"));
  }

  @Test(expected = InvalidArgumentException.class)
  public void isDirectory_DirectoryWithWrongPath_ThrowInvalidPathException()
      throws FileNotFoundException, InvalidArgumentException {
    fileSystem.isDirectory("/home/dir1/dir2");
  }

  @Test(expected = FileNotFoundException.class)
  public void isDirectory_NonExistentDirectory_ThrowFileNotFoundException()
      throws FileNotFoundException, InvalidArgumentException {
    fileSystem.isDirectory("/home/dir1");
  }

  @Test
  public void isDirectory_FileIsNotDirectory_False()
      throws FileNotFoundException, FileAlreadyExistsException, InvalidArgumentException {
    String absolutePath = "/home/f1";

    fileSystem.createTextFile(absolutePath);

    assertFalse(fileSystem.isDirectory(absolutePath));
  }

  @Test(expected = InvalidArgumentException.class)
  public void removeContentFromLinesInTextFile_InvalidPathToTextFile_ThrowInvalidArgumentExcepiton()
      throws FileNotFoundException, InvalidArgumentException {
    fileSystem.removeContentFromTextFile("/home/dir1/f1", 1, 2);
  }

  @Test(expected = FileNotFoundException.class)
  public void removeContentFromLinesInTextFile_FileDoesNotExists_ThrowFileNotFoundException()
      throws FileNotFoundException, InvalidArgumentException {
    fileSystem.removeContentFromTextFile("f1", 1, 2);
  }

  @Test(expected = FileNotFoundException.class)
  public void removeContentFromLinesInTextFile_FileIsDirectory_ThrowFileNotFoundException()
      throws FileNotFoundException, InvalidArgumentException {
    fileSystem.removeContentFromTextFile("/home", 1, 2);
  }

  @Test
  public void removeContentFromLinesInTextFile_RemoveAllContentFromTextFile_EmptyTextFile()
      throws FileAlreadyExistsException, FileNotFoundException, NotEnoughMemoryException,
          InvalidArgumentException {
    fileSystem.createTextFile("/home/f1");
    int start = 1;
    int end = 5;
    for (int i = start; i <= end; i++) {
      fileSystem.writeToTextFile("/home/f1", i, "hello");
    }
    fileSystem.removeContentFromTextFile("/home/f1", start, end);
    assertEquals("", fileSystem.getTextFileContent("/home/f1"));
  }

  @Test(expected = InvalidArgumentException.class)
  public void getWordCount_WrongPathToTextFile_ThrowInvalidArgumentException()
      throws FileNotFoundException, InvalidArgumentException {
    fileSystem.getWordCount("/home/dir1/f1");
  }

  @Test(expected = FileNotFoundException.class)
  public void getWordCount_TextFileDoesNotExists_ThrowFileNotFoundException()
      throws FileNotFoundException, InvalidArgumentException {
    fileSystem.getWordCount("/home/f1");
  }

  @Test(expected = FileNotFoundException.class)
  public void getWordCount_Directory_ThrowFileNotFoundException()
      throws FileNotFoundException, InvalidArgumentException {
    fileSystem.getWordCount("/home");
  }

  @Test
  public void getWordCount_EmptyTextFile_ReturnNumberOfWordsInTextFile()
      throws FileAlreadyExistsException, InvalidArgumentException, FileNotFoundException {
    fileSystem.createTextFile("/home/f1");
    assertEquals(0, fileSystem.getWordCount("/home/f1"));
  }

  @Test
  public void getWordCount_TextFile_ReturnNumberOfWordsInTextFile()
      throws FileAlreadyExistsException, InvalidArgumentException, FileNotFoundException,
          NotEnoughMemoryException {
    String absolutePath = "/home/f1";
    fileSystem.createTextFile(absolutePath);
    fileSystem.writeToTextFile(absolutePath, 1, "Hello World");
    assertEquals(2, fileSystem.getWordCount(absolutePath));
  }

  @Test(expected = InvalidArgumentException.class)
  public void getLineCount_WrongPathToTextFile_ThrowInvalidArgumentException()
      throws FileNotFoundException, InvalidArgumentException {
    fileSystem.getLineCount("/home/dir1/f1");
  }

  @Test(expected = FileNotFoundException.class)
  public void getLineContent_TextFileDoesNotExists_ThrowFileNotFoundException()
      throws FileNotFoundException, InvalidArgumentException {
    fileSystem.getLineCount("/home/f1");
  }

  @Test(expected = FileNotFoundException.class)
  public void getLineContent_FileIsDirectory_ThrowFileNotFoundException()
      throws FileNotFoundException, InvalidArgumentException {
    fileSystem.getLineCount("/home");
  }

  @Test
  public void getLineContent_TextFile_ReturnNumberOfLinesInTextFile()
      throws FileNotFoundException, NotEnoughMemoryException, InvalidArgumentException,
          FileAlreadyExistsException {
    String absolutePath = "/home/f1";
    fileSystem.createTextFile(absolutePath);
    fileSystem.writeToTextFile(absolutePath, 5, "hello world");
    assertEquals(5, fileSystem.getLineCount(absolutePath));
  }
}
