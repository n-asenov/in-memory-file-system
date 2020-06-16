package filesystem;

import java.io.FileNotFoundException;

import commands.exception.InvalidArgumentException;

public interface DirectoryValidator {

  boolean isDirectory(String absolutePath) throws InvalidArgumentException, FileNotFoundException;
}
