package commands;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Set;
import commands.exception.InvalidArgumentException;
import filesystem.TextFileContentController;
import path.Path;

public class WordCount implements Command {
  private static final String COUNT_LINES_OPTION = "-l";

  private TextFileContentController fileSystem;
  private Path currentDirectory;

  public WordCount(TextFileContentController fileSystem, Path currentDirectory) {
    this.fileSystem = fileSystem;
    this.currentDirectory = currentDirectory;
  }

  @Override
  public String execute(List<String> arguments, Set<String> options)
      throws InvalidArgumentException, FileNotFoundException {
    validateArguments(arguments);
    validateOptions(options);

    if (options.contains(COUNT_LINES_OPTION)) {
      int lines = countLines(arguments);
      return String.valueOf(lines);
    }

    int words = countWords(arguments);
    return String.valueOf(words);
  }

  private void validateArguments(List<String> arguments) throws InvalidArgumentException {
    if (arguments.isEmpty()) {
      throw new InvalidArgumentException(INVALID_ARGUMENT_MESSAGE);
    }
  }

  private void validateOptions(Set<String> options) throws InvalidArgumentException {
    for (String option : options) {
      if (!option.equals(COUNT_LINES_OPTION)) {
        throw new InvalidArgumentException(INVALID_OPTION_MESSAGE);
      }
    }
  }

  private int countLines(List<String> arguments)
      throws InvalidArgumentException, FileNotFoundException {
    String text;

    if (countInTextFile(arguments)) {
      text = getTextFileContent(arguments);
    } else {
      text = getTextFromArguments(arguments);
    }

    return countLinesInText(text);
  }

  private boolean countInTextFile(List<String> arguments) {
    return arguments.size() == 1;
  }

  private String getTextFileContent(List<String> arguments)
      throws InvalidArgumentException, FileNotFoundException {
    String textFilePath = arguments.get(0);
    String textFileAbsolutePath = currentDirectory.getAbsolutePath(textFilePath);
    String textFileContent = fileSystem.getTextFileContent(textFileAbsolutePath);
    return textFileContent;
  }

  private int countLinesInText(String text) {
    int lines = 0;

    if (text.isEmpty()) {
      return lines;
    }

    lines = 1;
    int index = text.indexOf(System.lineSeparator());

    while (index != -1) {
      lines++;
      index = text.indexOf(System.lineSeparator(), index + 1);
    }

    return lines;
  }

  private String getTextFromArguments(List<String> arguments) {
    StringBuilder text = new StringBuilder();

    for (String argument : arguments) {
      text.append(argument).append(" ");
    }

    return text.toString();
  }

  private int countWords(List<String> arguments)
      throws InvalidArgumentException, FileNotFoundException {
    String text;

    if (countInTextFile(arguments)) {
      text = getTextFileContent(arguments);
    } else {
      text = getTextFromArguments(arguments);
    }

    return countWordsInText(text);
  }

  private int countWordsInText(String text) {
    int words = 0;

    if (text.isEmpty()) {
      return words;
    }

    final String whitespace = "\\s";

    for (String word : text.split(whitespace)) {
      if (!word.isEmpty()) {
        words++;
      }
    }

    return words;
  }
}
