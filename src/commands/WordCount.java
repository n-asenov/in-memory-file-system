package commands;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Set;

import commands.exception.InvalidArgumentException;
import filesystem.TextFileStatistics;
import path.Path;

public class WordCount implements Command {
    private static final String COUNT_LINES_OPTION = "-l";

    private TextFileStatistics fileSystem;
    private Path currentDirectory;

    public WordCount(TextFileStatistics fileSystem, Path currentDirectory) {
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

    private int countLines(List<String> arguments) throws InvalidArgumentException, FileNotFoundException {
	if (countInTextFile(arguments)) {
	    String textFileAbsolutePath = getTextFileAbsolutePath(arguments);
	    int linesInTextFile = fileSystem.getLineCount(textFileAbsolutePath);
	    return linesInTextFile;
	}

	return countLinesInText(arguments);
    }

    private boolean countInTextFile(List<String> arguments) {
	return arguments.size() == 1;
    }

    private int countLinesInText(List<String> text) {
	final String newLine = "\\n";
	int lines = 1;

	for (String word : text) {
	    int index = word.indexOf(newLine);

	    while (index != -1) {
		lines++;
		index = word.indexOf(newLine, index + 1);
	    }
	}

	return lines;
    }

    private int countWords(List<String> arguments) throws InvalidArgumentException, FileNotFoundException {
	if (countInTextFile(arguments)) {
	    String textFileAbsolutePath = getTextFileAbsolutePath(arguments);
	    int linesInTextFile = fileSystem.getWordCount(textFileAbsolutePath);
	    return linesInTextFile;
	}
	
	int wordsInText = arguments.size();
	return wordsInText;
    }
    
    private String getTextFileAbsolutePath(List<String> arguments) {
	String textFilePath = arguments.get(0);
	String textFileAbsolutePath = currentDirectory.getAbsolutePath(textFilePath);
	return textFileAbsolutePath;
    }
}
