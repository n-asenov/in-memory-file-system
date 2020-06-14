package filesystem;

import java.io.FileNotFoundException;

import filesystem.exceptions.InvalidArgumentException;

public interface TextFileStatistics {
    
    Integer getWordCount(String absolutePath) throws InvalidArgumentException, FileNotFoundException;

    Integer getLineCount(String absolutePath) throws InvalidArgumentException, FileNotFoundException;

}
