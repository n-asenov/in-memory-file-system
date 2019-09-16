package fileSystem.fs;

import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.NotDirectoryException;
import java.util.List;

public interface AbstractFileSystem {
	abstract void makeDirectory(String absolutePath) throws InvalidArgumentException, FileAlreadyExistsException;

	abstract void createTextFile(String absolutePath) throws InvalidArgumentException, FileAlreadyExistsException;

	abstract String getTextFileContent(String absolutePath) throws InvalidArgumentException, FileNotFoundException;

	abstract void writeToTextFile(String absolutePath, int line, String content, boolean overwrite) throws InvalidArgumentException, FileNotFoundException, NotEnoughMemoryException;

	abstract List<String> getDirectoryContent(String absolutePath, FilterBy option) throws InvalidArgumentException, NotDirectoryException, FileNotFoundException;
	
	abstract boolean isDirectory(String absolutePath) throws InvalidArgumentException, FileNotFoundException;

	abstract Integer getWordCount(String absolutePath) throws FileNotFoundException, InvalidArgumentException;
	
	abstract Integer getLineCount(String absolutePath) throws FileNotFoundException, InvalidArgumentException;
	
	abstract void removeTextFile(String absolutePath) throws InvalidArgumentException, FileNotFoundException;

	abstract void removeContentFromLinesInTextFile(String absolutePath, int start, int end) throws InvalidArgumentException, FileNotFoundException;
}
