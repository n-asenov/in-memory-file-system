package commands;

import java.io.IOException;
import java.util.List;

import filesystem.AbstractFileSystem;
import filesystem.FilterBy;
import filesystem.InvalidArgumentException;
import path.Path;

public class ListDirectoryContent implements Command {
    private AbstractFileSystem fileSystem;
    private Path currentDirectory;

    public ListDirectoryContent(AbstractFileSystem fileSystem, Path currentDirectory) {
        this.fileSystem = fileSystem;
        this.currentDirectory = currentDirectory;
    }

    @Override
    public String execute(List<String> options, List<String> arguments) throws InvalidArgumentException, IOException {
        FilterBy flag = validateOptions(options);

        int size = arguments.size();

        if (size == 0) {
            return appendDirectoryContent(fileSystem.getDirectoryContent(currentDirectory.getCurrentDirectory(), flag));
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < size - 1; i++) {
            result.append(appendDirectoryContent(
                    fileSystem.getDirectoryContent(currentDirectory.getAbsolutePath(arguments.get(i)), flag)));
            result.append(System.lineSeparator());
        }
        result.append(appendDirectoryContent(
                fileSystem.getDirectoryContent(currentDirectory.getAbsolutePath(arguments.get(size - 1)), flag)));

        return result.toString();
    }

    private FilterBy validateOptions(List<String> options) throws InvalidArgumentException {
        FilterBy flag = FilterBy.DEFAULT;

        for (String option : options) {
            if (!option.equals("-sortedDesc")) {
                throw new InvalidArgumentException("Invalid option");
            }
            flag = FilterBy.SIZE_DESCENDING;
        }

        return flag;
    }

    private String appendDirectoryContent(List<String> content) {
        StringBuilder result = new StringBuilder();

        for (String fileName : content) {
            result.append(fileName);
            result.append(" ");
        }

        return result.toString();
    }
}
