package filesystem;

import java.io.FileNotFoundException;

import filesystem.exceptions.InvalidArgumentException;
import filesystem.exceptions.NotEnoughMemoryException;

public interface TextFileContentController {

    void writeToTextFile(String absolutePath, int line, String content)
	    throws InvalidArgumentException, FileNotFoundException, NotEnoughMemoryException;

    void appendToTextFile(String absolutePath, int line, String content)
	    throws FileNotFoundException, InvalidArgumentException, NotEnoughMemoryException;

    void removeContentFromTextFile(String absolutePath, int start, int end)
	    throws InvalidArgumentException, FileNotFoundException;

    String getTextFileContent(String absolutePath) throws InvalidArgumentException, FileNotFoundException;

}
