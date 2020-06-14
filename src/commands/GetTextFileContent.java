package commands;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Set;

import filesystem.TextFileContentController;
import filesystem.exceptions.InvalidArgumentException;
import path.Path;

public class GetTextFileContent implements Command {
    private TextFileContentController fileSystem;
    private Path currentDirectory;

    public GetTextFileContent(TextFileContentController fileSystem, Path currentDirectory) {
	this.fileSystem = fileSystem;
	this.currentDirectory = currentDirectory;
    }

    @Override
    public String execute(List<String> arguments, Set<String> options)
	    throws InvalidArgumentException, FileNotFoundException {
	validateArguments(arguments);
	validateOptions(options);

	StringBuilder textFilesContent = new StringBuilder();
	final int lastIndex = arguments.size() - 1;
	
	for (int index = 0; index < arguments.size(); index++) {
	    String textFileRelativePath = arguments.get(index);
	    String textFileAbsolutePath = currentDirectory.getAbsolutePath(textFileRelativePath);
	    String currentTextFileContent = fileSystem.getTextFileContent(textFileAbsolutePath);
	    textFilesContent.append(currentTextFileContent);
	    
	    if (index != lastIndex) {
		textFilesContent.append(System.lineSeparator());
	    }
	}

	return textFilesContent.toString();
    }

    private void validateArguments(List<String> arguments) throws InvalidArgumentException {
	if (arguments.isEmpty()) {
	    throw new InvalidArgumentException("Invalid argument");
	}
    }
    
    private void validateOptions(Set<String> options) throws InvalidArgumentException {
	if (!options.isEmpty()) {
	    throw new InvalidArgumentException("Invalid option");
	}
    }
}
