package fileSystem.fs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface AbstractFileSystem {
	abstract void makeDirectory(String absolutePath) throws InvalidArgumentException, IOException;

	abstract void createTextFile(String absolutePath) throws InvalidArgumentException, IOException;

	abstract String getTextFileContent(String absolutePath) throws InvalidArgumentException, IOException;

	abstract void writeToTextFile(String absolutePath, int line, String content, boolean overwrite) throws InvalidArgumentException, IOException, NotEnoughMemoryException;

	abstract List<String> getDirectoryContent(String absolutePath, FilterBy option) throws InvalidArgumentException, IOException;
	
	abstract boolean isDirectory(String absolutePath) throws InvalidArgumentException, FileNotFoundException;

	abstract Integer getWordCount(String absolutePath) throws InvalidArgumentException, IOException;
	
	abstract Integer getLineCount(String absolutePath) throws InvalidArgumentException, IOException;
	
	abstract void removeTextFile(String absolutePath) throws InvalidArgumentException, IOException;

	abstract void removeContentFromLinesInTextFile(String absolutePath, int start, int end) throws InvalidArgumentException, IOException;
}
