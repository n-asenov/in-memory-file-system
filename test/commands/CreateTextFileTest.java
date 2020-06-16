package commands;

import static org.junit.Assert.assertArrayEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import commands.exception.InvalidArgumentException;
import filesystem.File;
import filesystem.VirtualFileSystem;
import path.Path;

public class CreateTextFileTest {
  private static final Comparator<File> DEFAULT = (f1, f2) -> f1.getName().compareTo(f2.getName());

  private VirtualFileSystem fileSystem;
  private CreateTextFile command;
  private List<String> arguments;
  private Set<String> options;

  @Before
  public void init() {
    fileSystem = new VirtualFileSystem();
    command = new CreateTextFile(fileSystem, new Path());
    arguments = new ArrayList<>();
    options = new HashSet<>();
  }

  @Test(expected = InvalidArgumentException.class)
  public void execute_CommandWithOption_ThrowIllegalArgumentException()
      throws InvalidArgumentException, FileAlreadyExistsException {
    arguments.add("/home/f1");
    options.add("-l");

    command.execute(arguments, options);
  }

  @Test(expected = FileAlreadyExistsException.class)
  public void execute_CreateAlreadyExistingFile_ThrowFileAlreadyExistsException()
      throws InvalidArgumentException, FileAlreadyExistsException {
    arguments.add("/home");

    command.execute(arguments, options);
  }

  @Test(expected = InvalidArgumentException.class)
  public void execute_CreateTextFileWithWrongAbsolutePath_ThrowInvalidArgumentException()
      throws InvalidArgumentException, FileAlreadyExistsException {
    arguments.add("/home/dir1/f1");

    command.execute(arguments, options);
  }

  @Test
  public void execute_CreateNewTextFile_TextFileAddedToFileSystem()
      throws InvalidArgumentException, FileAlreadyExistsException, FileNotFoundException {
    arguments.add("/home/f1");

    command.execute(arguments, options);

    String[] expectedResult = {"f1"};
    String[] actualResult = getActualResult();
    assertArrayEquals(expectedResult, actualResult);
  }

  @Test
  public void execute_CreateTextFileWithRelativePath_TextFileAddedToFileSystem()
      throws InvalidArgumentException, FileAlreadyExistsException, FileNotFoundException {
    arguments.add("f1");

    command.execute(arguments, options);

    String[] expectedResult = {"f1"};
    String[] actualResult = getActualResult();
    assertArrayEquals(expectedResult, actualResult);
  }

  @Test
  public void execute_CreateSeveralTextFiles_TextFilesAddedToFileSystem()
      throws InvalidPathException, InvalidArgumentException, IOException {
    arguments.add("f1");
    arguments.add("f2");
    arguments.add("f3");
    arguments.add("/f1");

    command.execute(arguments, options);

    String[] expectedResult = {"f1", "f2", "f3"};
    String[] actualResult = getActualResult();
    assertArrayEquals(expectedResult, actualResult);
  }

  private String[] getActualResult() throws FileNotFoundException, InvalidArgumentException {
    List<String> directoryContent = fileSystem.getDirectoryContent("/home", DEFAULT);
    String[] actualResult = directoryContent.toArray(new String[0]);
    return actualResult;
  }
}
