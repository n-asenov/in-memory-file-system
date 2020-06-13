package filesystem;

import java.util.TreeMap;

public class TextFile extends File {
    private TreeMap<Integer, String> content;
    private int size;

    public TextFile(String name) {
	super(name);
	content = new TreeMap<Integer, String>();
	size = 0;
    }

    @Override
    int getSize() {
	return size;
    }

    @Override
    boolean isDirectory() {
	return false;
    }

    @Override
    boolean isTextFile() {
	return true;
    }

    public String getContent() {
	StringBuilder fileContent = new StringBuilder();

	if (content.size() != 0) {
	    for (int i = 1; i < content.lastKey(); i++) {
		String lineContent = content.get(i);
		if (lineContent == null) {
		    fileContent.append(System.lineSeparator());
		} else {
		    fileContent.append(lineContent);
		    fileContent.append(System.lineSeparator());
		}
	    }
	    fileContent.append(content.get(content.lastKey()));
	}

	return fileContent.toString();
    }

    public Integer getNumberOfWords() {
	int counter = 0;

	if (content.size() != 0) {
	    for (int i = 1; i <= content.lastKey(); i++) {
		String line = content.get(i);
		if (line != null) {
		    counter += line.split(" ").length;
		}
	    }
	}

	return counter;
    }

    public Integer getNumberOfLines() {
	if (content.size() == 0) {
	    return 0;
	}

	return content.lastKey();
    }

    public boolean isEmptyLine(int line) {
	return content.get(line) == null;
    }

    public int getLineSize(int line) {
	String lineContent = content.get(line);

	if (lineContent == null) {
	    return 0;
	}

	return lineContent.length();
    }

    public void removeContentFromLines(int start, int end) throws InvalidArgumentException {
	if (end < start) {
	    throw new InvalidArgumentException("Second line number must be bigger than first");
	}

	for (int i = start; i <= end; i++) {
	    removeLineContent(i);
	}
    }

    private void removeLineContent(int line) throws InvalidArgumentException {
	if (line <= 0) {
	    throw new InvalidArgumentException("Line number must be positive");
	}

	String deletedContent = content.remove(line);

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
