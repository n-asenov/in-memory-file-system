package filesystem;

import java.nio.file.FileAlreadyExistsException;

import filesystem.exceptions.InvalidArgumentException;

public interface DirectoryController {

    void makeDirectory(String absolutePath) throws InvalidArgumentException, FileAlreadyExistsException;

}