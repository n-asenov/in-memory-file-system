package filesystem;

import java.nio.file.FileAlreadyExistsException;

import commands.exception.InvalidArgumentException;

public interface DirectoryController {

  void makeDirectory(String absolutePath)
      throws InvalidArgumentException, FileAlreadyExistsException;
}
