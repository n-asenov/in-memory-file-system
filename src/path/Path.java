package path;

import java.util.ArrayDeque;
import java.util.Deque;

public class Path {
  private static final String PATH_SEPARATOR = "/";

  private String homeDirectory;
  private String currentDirectory;

  public Path() {
    homeDirectory = "/home/";
    currentDirectory = "/home/";
  }

  public String getCurrentDirectory() {
    return currentDirectory;
  }

  public void setCurrentDirectory(String currentDirectory) {
    if (currentDirectory.equals("")) {
      this.currentDirectory = homeDirectory;
    } else {
      this.currentDirectory = currentDirectory;
    }
  }

  public String getAbsolutePath(String relativePath) {
    if (isRelativePath(relativePath)) {
      return trimAbsolutePath(currentDirectory + relativePath);
    }

    if (!isRootPath(relativePath)) {
      return trimAbsolutePath(relativePath);
    }

    return relativePath;
  }

  private boolean isRelativePath(String path) {
    return path.indexOf(PATH_SEPARATOR) != 0;
  }

  private boolean isRootPath(String path) {
    return path.equals("/");
  }

  private String trimAbsolutePath(String absolutePath) {
    Deque<String> trimmedAbsolutePath = new ArrayDeque<>();

    for (String directory : absolutePath.split(PATH_SEPARATOR)) {
      if (!isSpecialDirectory(directory)) {
        trimmedAbsolutePath.addLast(directory);
      } else if (isParentDirectory(directory) && hasParentDirectory(trimmedAbsolutePath)) {
        trimmedAbsolutePath.removeLast();
      }
    }

    return buildAbsolutePath(trimmedAbsolutePath);
  }

  private boolean isSpecialDirectory(String directory) {
    return directory.equals(".") || directory.equals("..");
  }

  private boolean isParentDirectory(String directory) {
    return directory.equals("..");
  }

  private boolean hasParentDirectory(Deque<String> trimmedAbsolutePath) {
    return !trimmedAbsolutePath.peekLast().equals("");
  }

  private String buildAbsolutePath(Deque<String> tokens) {
    StringBuilder absolutePath = new StringBuilder();

    while (!tokens.isEmpty()) {
      absolutePath.append(tokens.removeFirst()).append(PATH_SEPARATOR);
    }

    return absolutePath.toString();
  }
}
