package commands;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Set;

import filesystem.TextFileStatistics;
import filesystem.exceptions.InvalidArgumentException;
import path.Path;

public class WordCount implements Command {
    private TextFileStatistics fileSystem;
    private Path currentDirectory;

    public WordCount(TextFileStatistics fileSystem, Path currentDirectory) {
	this.fileSystem = fileSystem;
	this.currentDirectory = currentDirectory;
    }

    @Override
    public String execute(List<String> arguments, Set<String> options) throws InvalidArgumentException, FileNotFoundException {
	validateArguments(arguments);
	
	boolean getLines = validateOptions(options);
	
	

	Integer result = arguments.size();

	if (getLines) {
	    if (result.equals(1)) {
		return String.valueOf(fileSystem.getLineCount(currentDirectory.getAbsolutePath(arguments.get(0))));
	    }

	    return getLinesInText(arguments).toString();
	}

	if (result.equals(1)) {
	    result = fileSystem.getWordCount(currentDirectory.getAbsolutePath(arguments.get(0)));
	}

	return result.toString();
    }

    private void validateArguments(List<String> arguments) throws InvalidArgumentException {
	if (arguments.isEmpty()) {
	    throw new InvalidArgumentException("Invalid number of arguments");
	}
    }
    
    private boolean validateOptions(Set<String> options) throws InvalidArgumentException {
	boolean lines = false;
	for (String option : options) {
	    if (!option.equals("-l")) {
		throw new InvalidArgumentException("Invalid option");
	    }
	    lines = true;
	}
	return lines;
    }

    

    private Integer getLinesInText(List<String> text) {
	int counter = 1;

	for (String word : text) {
	    int index = word.indexOf("\\n", 0);

	    while (index != -1) {
		counter++;
		index = word.indexOf("\\n", index + 1);
	    }
	}

	return counter;
    }
}
