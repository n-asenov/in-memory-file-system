package commands;

import java.io.FileNotFoundException;
import java.util.List;

import filesystem.TextFileContentController;
import filesystem.exceptions.InvalidArgumentException;
import filesystem.exceptions.NotEnoughMemoryException;
import path.Path;

public class WriteToTextFile implements Command {
    private TextFileContentController fileSystem;
    private Path currentDirectory;
    private boolean overwrite;
    
    public WriteToTextFile(TextFileContentController fileSystem, Path currentDirectory) {
	this.fileSystem = fileSystem;
	this.currentDirectory = currentDirectory;
	this.overwrite = false;
    }

    @Override
    public String execute(List<String> arguments, List<String> options)
	    throws InvalidArgumentException, FileNotFoundException, NotEnoughMemoryException {
	validateArguments(arguments);
	validateOptions(options);

	String absolutePath = currentDirectory.getAbsolutePath(arguments.get(0));
	int lineNumber = getLineNumber(arguments);
	String lineContent = getLineContent(arguments);

	if (overwrite) {
	    fileSystem.writeToTextFile(absolutePath, lineNumber, lineContent);
	} else {
	    fileSystem.appendToTextFile(absolutePath, lineNumber, lineContent);
	}

	overwrite = false;
	return "";
    }

    private void validateArguments(List<String> arguments) throws InvalidArgumentException {
	final int minimumArgumentSize = 3;
	if (arguments.size() < minimumArgumentSize) {
	    throw new InvalidArgumentException("Commmand expects more arguments");
	}
    }

    private void validateOptions(List<String> options) throws InvalidArgumentException {
	for (String option : options) {
	    if (option.equals("-overwrite")) {
		overwrite = true;
	    } else {
		throw new InvalidArgumentException("Invalid option");
	    }
	}
    }

    private int getLineNumber(List<String> arguments) throws InvalidArgumentException {
	final int lineNumberIndex = 1;
	int lineNumber = 0;
	
	try {
	    lineNumber = Integer.parseInt(arguments.get(lineNumberIndex));
	} catch (NumberFormatException e) {
	    throw new InvalidArgumentException("Second argument must be a positive integer", e);
	}
		
	validateLineNumber(lineNumber);

	return lineNumber;
    }
    
    private void validateLineNumber(int lineNumber) throws InvalidArgumentException {
	if (lineNumber <= 0) {
	    throw new InvalidArgumentException("Second argument must be a positive integer");
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
