package filesystem;

import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.List;

import commands.exception.InvalidArgumentException;

public interface DirectoryContentController {

    List<String> getDirectoryContent(String absolutePath, Comparator<File> comparator)
	    throws InvalidArgumentException, FileNotFoundException;

}