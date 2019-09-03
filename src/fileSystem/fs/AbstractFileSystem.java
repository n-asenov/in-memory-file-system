package fileSystem.fs;

import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.InvalidPathException;
import java.nio.file.NotDirectoryException;

public interface AbstractFileSystem {
	abstract void makeDirectory(String absolutePath) throws InvalidPathException, FileAlreadyExistsException;

	abstract void createTextFile(String absolutePath) throws InvalidPathException, FileAlreadyExistsException;

	abstract String getTextFileContent(String absolutePath) throws InvalidPathException, FileNotFoundException;

	abstract void writeToTextFile(String absolutePath, int line, String content, boolean overwrite) throws InvalidPathException, FileNotFoundException;

	abstract String getDirectoryContent(String absolutePath, FilterBy option) throws InvalidPathException, NotDirectoryException, FileNotFoundException;
	
	abstract boolean isDirectory(String absolutePath) throws InvalidPathException, FileNotFoundException;
}
