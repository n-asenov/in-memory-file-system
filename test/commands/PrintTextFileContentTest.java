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

public class PrintTextFileContentTest {
  private GetTextFileContent command;
  private VirtualFileSystem fileSystem;
  private List<String> arguments;
  private Set<String> options;

  @Before
  public void init() {
    fileSystem = new VirtualFileSystem();
    try {
      fileSystem.createTextFile("/home/f1");
    } catch (FileAlreadyExistsException | InvalidArgumentException e) {
      throw new IllegalArgumentException(e);
    }
    command = new GetTextFileContent(fileSystem, new Path());
    options = new HashSet<>();
    arguments = new ArrayList<>();
  }

  @Test(expected = InvalidArgumentException.class)
  public void execute_CommandWithOption_ThrowIllegalArgumentException()
      throws InvalidArgumentException, FileNotFoundException {
    options.add("-option");

    command.execute(arguments, options);
  }

  @Test(expected = InvalidArgumentException.class)
  public void execute_PathToTextFileIsInvalid_ThrowInvalidArgumentException()
      throws InvalidArgumentException, FileNotFoundException {
    arguments.add("/home/dir1/f1");

    command.execute(arguments, options);
  }

  @Test(expected = FileNotFoundException.class)
  public void execute_FileDoesNotExsist_ThrowFileNotFoundException()
      throws InvalidArgumentException, FileNotFoundException {
    arguments.add("/home/f2");

    command.execute(arguments, options);
  }

  @Test(expected = FileNotFoundException.class)
  public void execute_FileIsDirectory_ThrowFileNotFoundException()
      throws InvalidArgumentException, FileNotFoundException {
    arguments.add("/home");

    command.execute(arguments, options);
  }

  @Test
  public void execute_EmptyTextFile_OutputEmptyString()
      throws InvalidArgumentException, FileNotFoundException {
    arguments.add("/home/f1");

    assertEquals("", command.execute(arguments, options));
  }

  @Test
  public void execute_EmptyTextFileWithRelativePath_ReturnEmptyString()
      throws InvalidArgumentException, FileNotFoundException {
    arguments.add("f1");

    assertEquals("", command.execute(arguments, options));
  }
}
