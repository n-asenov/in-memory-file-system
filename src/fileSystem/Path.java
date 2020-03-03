package fileSystem;

import java.util.ArrayDeque;

public class Path {
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
        if (currentDirectory == null) {
            this.currentDirectory = homeDirectory;
        } else {
            this.currentDirectory = currentDirectory;
        }
    }

    public String getAbsolutePath(String relativePath) {
        if (relativePath.indexOf('/') == 0) {
            if (relativePath.length() == 1) {
                return relativePath;
            }

            return trimAbsolutePath(relativePath);
        }

        return trimAbsolutePath(currentDirectory + relativePath);
    }

    private String trimAbsolutePath(String absolutePath) {
        ArrayDeque<String> deque = new ArrayDeque<String>();

        for (String fileName : absolutePath.split("/")) {
            if (fileName.equals("..")) {
                if (!deque.peekLast().equals("")) {
                    deque.removeLast();
                }
            } else if (!fileName.equals(".")) {
                deque.addLast(fileName);
            }
        }

        StringBuilder path = new StringBuilder();

        while (!deque.isEmpty()) {
            path.append(deque.removeFirst()).append("/");
        }

        return path.toString();
    }
}
