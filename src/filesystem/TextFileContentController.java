package filesystem;

import java.io.FileNotFoundException;

import filesystem.exceptions.InvalidArgumentException;
import filesystem.exceptions.NotEnoughMemoryException;

public interface TextFileContentController {
    
    void writeToTextFile(String absolutePath, int line, String content, boolean overwrite)
	    throws InvalidArgumentException, FileNotFoundException, NotEnoughMemoryException;

    void removeContentFromLinesInTextFile(String absolutePath, int start, int end)
	    throws InvalidArgumentException, FileNotFoundException;

    String getTextFileContent(String absolutePath) throws InvalidArgumentException, FileNotFoundException;

}
