package filesystem;

import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;

import commands.exception.InvalidArgumentException;

public interface TextFileController {

  void createTextFile(String absolutePath)
      throws InvalidArgumentException, FileAlreadyExistsException;

  void removeTextFile(String absolutePath) throws InvalidArgumentException, FileNotFoundException;
}
