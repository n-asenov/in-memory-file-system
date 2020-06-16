package filesystem;

import java.io.FileNotFoundException;

import commands.exception.InvalidArgumentException;

public interface TextFileStatistics {
    
    int getWordCount(String absolutePath) throws InvalidArgumentException, FileNotFoundException;

    int getLineCount(String absolutePath) throws InvalidArgumentException, FileNotFoundException;

}
