package filesystem;

import java.util.SortedMap;
import java.util.TreeMap;
import commands.exception.InvalidArgumentException;

public class TextFile extends File {
  private SortedMap<Integer, String> content;
  private int size;

  public TextFile(String name) {
    super(name);
    content = new TreeMap<>();
    size = 0;
  }

  @Override
  public int getSize() {
    return size;
  }

  public String getContent() {
    StringBuilder fileContent = new StringBuilder();

    if (!content.isEmpty()) {
      int lastLineIndex = content.lastKey();

      for (int lineIndex = 1; lineIndex < lastLineIndex; lineIndex++) {
        String lineContent = content.get(lineIndex);

        if (lineContent != null) {
          fileContent.append(lineContent);
        }

        fileContent.append(System.lineSeparator());
      }

      fileContent.append(content.get(lastLineIndex));
    }

    return fileContent.toString();
  }

  public boolean isEmptyLine(int lineIndex) {
    return content.get(lineIndex) == null;
  }

  public int getLineSize(int lineIndex) {
    String lineContent = content.get(lineIndex);

    if (lineContent == null) {
      return 0;
    }

    return lineContent.length();
  }

  public void removeContentFromLines(int startIndex, int endIndex) throws InvalidArgumentException {
    if (endIndex < startIndex) {
      throw new InvalidArgumentException("End line must be bigger than start line");
    }

    for (int lineIndex = startIndex; lineIndex <= endIndex; lineIndex++) {
      removeLineContent(lineIndex);
    }
  }

  private void removeLineContent(int lineIndex) throws InvalidArgumentException {
    if (lineIndex <= 0) {
      throw new InvalidArgumentException("Line number must be positive");
    }

    String deletedContent = content.remove(lineIndex);

    if (deletedContent != null) {
      int lineSize = deletedContent.length() + 1;
      size -= lineSize;
    }
  }

  public void overwrite(int lineIndex, String lineContent) {
    String currentContent = content.get(lineIndex);

    if (currentContent != null) {
      size -= currentContent.length();
    } else {
      size++;
    }

    content.put(lineIndex, lineContent);
    size += lineContent.length();
  }

  public void append(int lineIndex, String lineContent) {
    String currentContent = content.get(lineIndex);
    String newContent;

    if (currentContent == null) {
      newContent = lineContent;
    } else {
      newContent = currentContent + lineContent;
    }

    overwrite(lineIndex, newContent);
  }
}
