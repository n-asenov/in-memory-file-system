package filesystem;

import java.util.SortedMap;
import java.util.TreeMap;

import filesystem.exceptions.InvalidArgumentException;

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

    public int getNumberOfWords() {
	int words = 0;

	for (String line : content.values()) {
	    words += line.split(" ").length;
	}

	return words;
    }

    public int getNumberOfLines() {
	if (content.isEmpty()) {
	    return 0;
	}

	return content.lastKey();
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
	    size -= deletedContent.length() + 1;
	}
    }

    public void write(int lineNumber, String lineContent, boolean overwrite) throws InvalidArgumentException {
	if (lineNumber <= 0) {
	    throw new InvalidArgumentException("Line number must be a positive integer");
	}

	if (!overwrite) {
	    append(lineNumber, lineContent);
	} else {
	    overwrite(lineNumber, lineContent);
	}
    }

    private void append(int lineNumber, String lineContent) {
	String currentContent = content.get(lineNumber);

	if (currentContent == null) {
	    content.put(lineNumber, lineContent);
	    size++;
	} else {
	    String newContent = currentContent + lineContent;
	    content.put(lineNumber, newContent);
	}

	size += lineContent.length();
    }

    private void overwrite(int lineNumber, String lineContent) {
	String currentContent = content.get(lineNumber);

	if (currentContent != null) {
	    size -= currentContent.length();
	} else {
	    size++;
	}

	content.put(lineNumber, lineContent);
	size += lineContent.length();
    }
}
