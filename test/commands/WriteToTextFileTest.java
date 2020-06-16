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
import filesystem.exceptions.NotEnoughMemoryException;
import path.Path;

public class WriteToTextFileTest {
  private WriteToTextFile command;
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

    command = new WriteToTextFile(fileSystem, new Path());
    arguments = new ArrayList<>();
    options = new HashSet<>();
  }

  @Test(expected = InvalidArgumentException.class)
  public void execute_CommandWithInvalidOption_ThrowIllegalArgumentException()
      throws NotEnoughMemoryException, InvalidArgumentException, FileNotFoundException {
    options.add("append");

    command.execute(arguments, options);
  }

  @Test(expected = InvalidArgumentException.class)
  public void execute_CommandWithInvalidNumberOfArguments_ThrowIllegalArgumentException()
      throws NotEnoughMemoryException, InvalidArgumentException, FileNotFoundException {
    arguments.add("/home/f1");

    command.execute(arguments, options);
  }

  @Test(expected = InvalidArgumentException.class)
  public void execute_CommnadWithWrongSecondArgument_ThrowIllegalArgumentException()
      throws NotEnoughMemoryException, InvalidArgumentException, FileNotFoundException {
    arguments.add("/home/f1");
    arguments.add("Hello");
    arguments.add("Hello, World!");

    command.execute(arguments, options);
  }

  @Test
  public void execute_ValidCommandWithNoOption_WriteContentToTextFile()
      throws NotEnoughMemoryException, InvalidArgumentException, FileNotFoundException {
    arguments.add("/home/f1");
    arguments.add("1");
    arguments.add("Hello, World!");

    command.execute(arguments, options);

    assertEquals("Hello, World!", fileSystem.getTextFileContent("/home/f1"));
  }

  @Test
  public void execute_ValidCommandWithOverwriteOption_WriteContentToTextFile()
      throws NotEnoughMemoryException, InvalidArgumentException, FileNotFoundException {
    options.add("-overwrite");
    arguments.add("/home/f1");
    arguments.add("1");
    arguments.add("Hello, World!");

    command.execute(arguments, options);

    assertEquals("Hello, World!", fileSystem.getTextFileContent("/home/f1"));
  }

  @Test
  public void execute_ValidCommandWithRelativePath_WriteContentToTextFile()
      throws NotEnoughMemoryException, InvalidArgumentException, FileNotFoundException {
    arguments.add("f1");
    arguments.add("1");
    arguments.add("Hello, World!");

    command.execute(arguments, options);

    assertEquals("Hello, World!", fileSystem.getTextFileContent("/home/f1"));
  }
}
