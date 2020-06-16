package commands;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import commands.exception.InvalidArgumentException;
import filesystem.VirtualFileSystem;
import path.Path;

public class ListDirectoryContentTest {
  private VirtualFileSystem fileSystem;
  private ListDirectoryContent command;
  private List<String> arguments;
  private Set<String> options;

  @Before
  public void init() {
    fileSystem = new VirtualFileSystem();
    try {
      fileSystem.makeDirectory("/home/dir1");
      fileSystem.makeDirectory("/home/dir1/dir2");
      fileSystem.makeDirectory("/home/dir1/dir3");
    } catch (FileAlreadyExistsException | InvalidArgumentException e) {
      throw new IllegalArgumentException(e);
    }
    command = new ListDirectoryContent(fileSystem, new Path());
    arguments = new ArrayList<>();
    options = new HashSet<>();
  }

  @Test(expected = InvalidArgumentException.class)
  public void execute_CommandWithInvalidOptions_ThrowIllegalArgumentException()
      throws InvalidArgumentException, FileNotFoundException {
    options.add("al");

    command.execute(arguments, options);
  }

  @Test
  public void execute_listEmptyDirectory_ReturnEmptyString()
      throws InvalidArgumentException, FileNotFoundException {
    arguments.add("/home/dir1/dir2");

    String expectedResult = "";
    String actualResult = command.execute(arguments, options);
    assertEquals(expectedResult, actualResult);
  }

  @Test
  public void execute_listTwoDirectories_ReturnContetnOfDirectories()
      throws InvalidArgumentException, FileNotFoundException {
    arguments.add("/");
    arguments.add("/home");

    String expectedResult = "home " + System.lineSeparator() + "dir1 ";
    String actualResult = command.execute(arguments, options);
    assertEquals(expectedResult, actualResult);
  }

  @Test
  public void execute_listDirectoryWithSortedDescOption_ReturnContentSortedDesc()
      throws InvalidArgumentException, FileNotFoundException {
    arguments.add("/");
    options.add("-sortedDesc");

    String expectedResult = "home ";
    String actualResult = command.execute(arguments, options);
    assertEquals(expectedResult, actualResult);
  }

  @Test
  public void execute_listDirectoryWithRelativePath_ReturnContentOfDirectory()
      throws InvalidArgumentException, FileNotFoundException {
    arguments.add("dir1");

    String expectedResult = "dir2 dir3 ";
    String actualResult = command.execute(arguments, options);
    assertEquals(expectedResult, actualResult);
  }

  @Test
  public void execute_listDirectoryWithNoArguments_ReturnContentOfCurrentDirectory()
      throws InvalidArgumentException, FileNotFoundException {
    String expectedResult = "dir1 ";
    String actualResult = command.execute(arguments, options);
    assertEquals(expectedResult, actualResult);
  }
}
