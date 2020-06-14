package filesystem;

import java.io.FileNotFoundException;

import filesystem.exceptions.InvalidArgumentException;

public interface DirectoryValidator {
    
    boolean isDirectory(String absolutePath) throws InvalidArgumentException, FileNotFoundException;

}
