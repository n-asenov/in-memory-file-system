package commands;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Set;

import commands.exception.InvalidArgumentException;
import filesystem.DirectoryValidator;
import path.Path;

public class ChangeDirectory implements Command {
  private DirectoryValidator fileSystem;
  private Path currentDirectory;

  public ChangeDirectory(DirectoryValidator fileSystem, Path currentDirectory) {
    this.fileSystem = fileSystem;
    this.currentDirectory = currentDirectory;
  }

  @Override
  public String execute(List<String> arguments, Set<String> options)
      throws InvalidArgumentException, FileNotFoundException {
    validateArguments(arguments);
    validateOptions(options);

    String newCurrentDirectory = "";

    if (!arguments.isEmpty()) {
      int newCurrentDirectoryIndex = 0;
      String newCurrentDirectoryRelativePath = arguments.get(newCurrentDirectoryIndex);
      newCurrentDirectory = currentDirectory.getAbsolutePath(newCurrentDirectoryRelativePath);
      validateNewCurrentDirectory(newCurrentDirectory);
    }

    currentDirectory.setCurrentDirectory(newCurrentDirectory);
    return "";
  }

  private void validateArguments(List<String> arguments) throws InvalidArgumentException {
    final int maxArgumentsCount = 1;
    if (arguments.size() > maxArgumentsCount) {
      throw new InvalidArgumentException("Too many arguments");
    }
  }

  private void validateOptions(Set<String> options) throws InvalidArgumentException {
    if (!options.isEmpty()) {
      throw new InvalidArgumentException(INVALID_OPTION_MESSAGE);
    }
  }

  private void validateNewCurrentDirectory(String newCurrentDirectory)
      throws FileNotFoundException, InvalidArgumentException {
    if (!fileSystem.isDirectory(newCurrentDirectory)) {
      throw new FileNotFoundException("File is not a directory");
    }
  }
}
