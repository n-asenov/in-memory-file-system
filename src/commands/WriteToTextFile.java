package commands;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Set;

import commands.exception.InvalidArgumentException;
import filesystem.TextFileContentController;
import filesystem.exceptions.NotEnoughMemoryException;
import path.Path;

public class WriteToTextFile implements Command {
  private static final String OVERWRITE_OPTION = "-overwrite";
  private static final String INVALID_SECOND_ARGUMENT_MESSAGE =
      "Second argument must be a positive integer";

  private TextFileContentController fileSystem;
  private Path currentDirectory;

  public WriteToTextFile(TextFileContentController fileSystem, Path currentDirectory) {
    this.fileSystem = fileSystem;
    this.currentDirectory = currentDirectory;
  }

  @Override
  public String execute(List<String> arguments, Set<String> options)
      throws InvalidArgumentException, FileNotFoundException, NotEnoughMemoryException {
    validateArguments(arguments);
    validateOptions(options);

    String absolutePath = currentDirectory.getAbsolutePath(arguments.get(0));
    int lineNumber = getLineNumber(arguments);
    String lineContent = getLineContent(arguments);

    if (options.contains(OVERWRITE_OPTION)) {
      fileSystem.writeToTextFile(absolutePath, lineNumber, lineContent);
    } else {
      fileSystem.appendToTextFile(absolutePath, lineNumber, lineContent);
    }

    return "";
  }

  private void validateArguments(List<String> arguments) throws InvalidArgumentException {
    final int minimumArgumentSize = 3;
    if (arguments.size() < minimumArgumentSize) {
      throw new InvalidArgumentException("Commmand expects more arguments");
    }
  }

  private void validateOptions(Set<String> options) throws InvalidArgumentException {
    for (String option : options) {
      if (!option.equals(OVERWRITE_OPTION)) {
        throw new InvalidArgumentException(INVALID_OPTION_MESSAGE);
      }
    }
  }

  private int getLineNumber(List<String> arguments) throws InvalidArgumentException {
    final int lineNumberIndex = 1;
    int lineNumber = 0;

    try {
      lineNumber = Integer.parseInt(arguments.get(lineNumberIndex));
    } catch (NumberFormatException e) {
      throw new InvalidArgumentException(INVALID_SECOND_ARGUMENT_MESSAGE, e);
    }

    validateLineNumber(lineNumber);

    return lineNumber;
  }

  private void validateLineNumber(int lineNumber) throws InvalidArgumentException {
    if (lineNumber <= 0) {
      throw new InvalidArgumentException(INVALID_SECOND_ARGUMENT_MESSAGE);
    }
  }

  private String getLineContent(List<String> arguments) {
    StringBuilder lineContent = new StringBuilder();
    final int lineContentStartIndex = 2;
    final int lineContentEndIndex = arguments.size() - 1;

    for (int index = lineContentStartIndex; index < lineContentEndIndex; index++) {
      lineContent.append(arguments.get(index)).append(" ");
    }
    lineContent.append(arguments.get(lineContentEndIndex));

    return lineContent.toString();
  }
}
