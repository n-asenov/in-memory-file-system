package commands;

import java.io.FileNotFoundException;
import java.util.List;

import filesystem.TextFileContentController;
import filesystem.exceptions.InvalidArgumentException;
import path.Path;

public class RemoveContentFromTextFile implements Command {
    private static final int START_INDEX = 0;
    private static final int END_INDEX = 1;
    
    private TextFileContentController fileSystem;
    private Path currentDirectory;

    public RemoveContentFromTextFile(TextFileContentController fileSystem, Path currentDirectory) {
	this.fileSystem = fileSystem;
	this.currentDirectory = currentDirectory;
    }

    @Override
    public String execute(List<String> arguments, List<String> options)
	    throws InvalidArgumentException, FileNotFoundException {
	validateArguments(arguments);
	validateOptions(options);
	
	final int intervalIndex = 1;
	String intervalSeparator = "-";
	String[] interval = arguments.get(intervalIndex).split(intervalSeparator);
	validateInterval(interval);
	
	int[] parsedInterval = parseInterval(interval);
	
	final int textFilePathIndex = 0;
	String textFileAbsolutePath = currentDirectory.getAbsolutePath(arguments.get(textFilePathIndex));

	fileSystem.removeContentFromTextFile(textFileAbsolutePath, parsedInterval[START_INDEX], parsedInterval[END_INDEX]);
	return "";
    }

    private void validateArguments(List<String> arguments) throws InvalidArgumentException {
	final int expectedArgumentsSize = 2;

	if (arguments.size() != expectedArgumentsSize) {
	    throw new InvalidArgumentException("Command should receive " + expectedArgumentsSize + " arguments");
	}
    }

    private void validateOptions(List<String> options) throws InvalidArgumentException {
	if (!options.isEmpty()) {
	    throw new InvalidArgumentException("Invalid option");
	}
    }
    
    private void validateInterval(String[] interval) throws InvalidArgumentException {
	final int intervalLength = 2;
	
	if (interval.length != intervalLength) {
	    throw new InvalidArgumentException("Interval must have 2 integers by -");
	}
    }
    
    private int[] parseInterval(String[] interval) throws InvalidArgumentException {
	int[] parsedInterval = new int[interval.length];
	
	try {
	    parsedInterval[START_INDEX] = Integer.parseInt(interval[START_INDEX]);
	    parsedInterval[END_INDEX] = Integer.parseInt(interval[END_INDEX]);
	} catch (NumberFormatException e) {
	    throw new InvalidArgumentException("Interval must have 2 integers", e);
	}

	return parsedInterval;
    }
}
