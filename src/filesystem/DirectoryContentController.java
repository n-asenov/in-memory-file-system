package filesystem;

import java.io.FileNotFoundException;
import java.nio.file.NotDirectoryException;
import java.util.List;

import filesystem.exceptions.InvalidArgumentException;

public interface DirectoryContentController {

    List<String> getDirectoryContent(String absolutePath, FilterBy option) throws InvalidArgumentException, NotDirectoryException, FileNotFoundException;

}