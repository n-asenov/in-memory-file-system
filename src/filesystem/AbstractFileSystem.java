package filesystem;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import filesystem.exceptions.InvalidArgumentException;
import filesystem.exceptions.NotEnoughMemoryException;

public interface AbstractFileSystem {
    void makeDirectory(String absolutePath) throws InvalidArgumentException, IOException;

    void createTextFile(String absolutePath) throws InvalidArgumentException, IOException;

    String getTextFileContent(String absolutePath) throws InvalidArgumentException, IOException;

    void writeToTextFile(String absolutePath, int line, String content, boolean overwrite)
	    throws InvalidArgumentException, IOException, NotEnoughMemoryException;

    List<String> getDirectoryContent(String absolutePath, FilterBy option) throws InvalidArgumentException, IOException;

    boolean isDirectory(String absolutePath) throws InvalidArgumentException, FileNotFoundException;

    Integer getWordCount(String absolutePath) throws InvalidArgumentException, IOException;

    Integer getLineCount(String absolutePath) throws InvalidArgumentException, IOException;

    void removeTextFile(String absolutePath) throws InvalidArgumentException, IOException;

    void removeContentFromLinesInTextFile(String absolutePath, int start, int end)
	    throws InvalidArgumentException, IOException;
}
