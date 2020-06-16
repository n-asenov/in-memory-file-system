package filesystem;

import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;

import commands.exception.InvalidArgumentException;
import filesystem.exceptions.NotEnoughMemoryException;

public class VirtualFileSystem
    implements TextFileController,
        TextFileContentController,
        TextFileStatistics,
        DirectoryController,
        DirectoryContentController,
        DirectoryValidator {
  public static final int MEMORY_CAPACITY = 1000;
  public static final String PATH_SEPARATOR = "/";

  private Directory root;
  private Deque<File> deletedFiles;
  private int usedMemory;

  public VirtualFileSystem() {
    root = new Directory("/");
    try {
      root.addFile(new Directory("home", root));
    } catch (FileAlreadyExistsException e) {
      throw new IllegalArgumentException("Should not reach here");
    }
    usedMemory = 0;
    deletedFiles = new ArrayDeque<>();
  }

  public int getUsedMemory() {
    return usedMemory;
  }

  @Override
  public void createTextFile(String absolutePath)
      throws InvalidArgumentException, FileAlreadyExistsException {
    Directory workDirectory = findWorkDirectory(absolutePath);
    File textFile = new TextFile(getLastFileName(absolutePath));
    workDirectory.addFile(textFile);
  }

  private Directory findWorkDirectory(String absolutePath) throws InvalidArgumentException {
    Directory currentDirectory = root;
    String[] directories = absolutePath.split(PATH_SEPARATOR);

    for (int index = 1; index < directories.length - 1; index++) {
      File nextDirectory = currentDirectory.getFile(directories[index]);

      if (nextDirectory == null || !(nextDirectory instanceof Directory)) {
        throw new InvalidArgumentException("Path: " + absolutePath + " " + "doesn't exists");
      }

      currentDirectory = (Directory) nextDirectory;
    }

    return currentDirectory;
  }

  private String getLastFileName(String absolutePath) {
    String[] filePath = absolutePath.split(PATH_SEPARATOR);

    if (filePath.length == 0) {
      String currentDirectory = ".";
      return currentDirectory;
    }

    return filePath[filePath.length - 1];
  }

  @Override
  public void removeTextFile(String absolutePath)
      throws InvalidArgumentException, FileNotFoundException {
    Directory workDirectory = findWorkDirectory(absolutePath);
    String textFileName = getLastFileName(absolutePath);
    File deletedTextFile = workDirectory.removeTextFile(textFileName);
    deletedFiles.addLast(deletedTextFile);
  }

  @Override
  public void writeToTextFile(String absolutePath, int line, String content)
      throws InvalidArgumentException, FileNotFoundException, NotEnoughMemoryException {
    TextFile textFile = findTextFile(absolutePath);

    usedMemory -= textFile.getLineSize(line);

    allocateMemory(textFile, line, content);

    textFile.overwrite(line, content);
  }

  private TextFile findTextFile(String absolutePath)
      throws InvalidArgumentException, FileNotFoundException {
    Directory workDirectory = findWorkDirectory(absolutePath);
    File file = workDirectory.getFile(getLastFileName(absolutePath));

    if (file == null) {
      throw new FileNotFoundException("Text file doesn't exists");
    }

    if (file instanceof Directory) {
      throw new FileNotFoundException("File is directory");
    }

    return (TextFile) file;
  }

  private void allocateMemory(TextFile textFile, int line, String newContent)
      throws NotEnoughMemoryException {
    int memoryToAdd = newContent.length();

    if (textFile.isEmptyLine(line)) {
      memoryToAdd += 1;
    }

    while (usedMemory + memoryToAdd > MEMORY_CAPACITY && !deletedFiles.isEmpty()) {
      File deletedFile = deletedFiles.removeFirst();
      usedMemory -= deletedFile.getSize();
    }

    if (usedMemory + memoryToAdd > MEMORY_CAPACITY) {
      throw new NotEnoughMemoryException("Not enough memory to add content to text file");
    }

    usedMemory += memoryToAdd;
  }

  @Override
  public void appendToTextFile(String absolutePath, int line, String content)
      throws InvalidArgumentException, FileNotFoundException, NotEnoughMemoryException {
    TextFile textFile = findTextFile(absolutePath);

    allocateMemory(textFile, line, content);

    textFile.append(line, content);
  }

  @Override
  public void removeContentFromTextFile(String absolutePath, int startLine, int endLine)
      throws InvalidArgumentException, FileNotFoundException {
    TextFile textFile = findTextFile(absolutePath);

    textFile.removeContentFromLines(startLine, endLine);
  }

  @Override
  public String getTextFileContent(String absolutePath)
      throws InvalidArgumentException, FileNotFoundException {
    TextFile textFile = findTextFile(absolutePath);

    return textFile.getContent();
  }

  @Override
  public int getWordCount(String absolutePath)
      throws InvalidArgumentException, FileNotFoundException {
    TextFile textFile = findTextFile(absolutePath);

    return textFile.getNumberOfWords();
  }

  @Override
  public int getLineCount(String absolutePath)
      throws InvalidArgumentException, FileNotFoundException {
    TextFile textFile = findTextFile(absolutePath);

    return textFile.getNumberOfLines();
  }

  @Override
  public void makeDirectory(String absolutePath)
      throws InvalidArgumentException, FileAlreadyExistsException {
    Directory workDirectory = findWorkDirectory(absolutePath);
    String newDirectoryName = getLastFileName(absolutePath);
    Directory newDirectory = new Directory(newDirectoryName, workDirectory);
    workDirectory.addFile(newDirectory);
  }

  @Override
  public List<String> getDirectoryContent(String absolutePath, Comparator<File> comparator)
      throws FileNotFoundException, InvalidArgumentException {
    File file = getLastFile(absolutePath);

    if (file instanceof TextFile) {
      throw new FileNotFoundException("File is not a directory");
    }

    Directory directory = (Directory) file;

    return directory.getContent(comparator);
  }

  private File getLastFile(String absolutePath)
      throws InvalidArgumentException, FileNotFoundException {
    Directory workDirectory = findWorkDirectory(absolutePath);

    File file = workDirectory.getFile(getLastFileName(absolutePath));

    if (file == null) {
      throw new FileNotFoundException("Directory doesn't exist");
    }

    return file;
  }

  @Override
  public boolean isDirectory(String absolutePath)
      throws InvalidArgumentException, FileNotFoundException {
    File file = getLastFile(absolutePath);

    return file instanceof Directory;
  }
}
