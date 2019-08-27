package fileSystem.fs;

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
			for (int i = 1; i <= content.lastKey(); i++) {
				String lineContent = content.get(i);
				if (lineContent == null) {
					fileContent.append(System.lineSeparator());
				} else {
					fileContent.append(lineContent);
					fileContent.append(System.lineSeparator());
				}
			}
		}

		return fileContent.toString();
	}

	public String write(int lineNumber, String lineContent, boolean overwrite) {
		if (lineNumber <= 0) {
			return "Invalid line number!";
		}

		if (!overwrite) {
			append(lineNumber, lineContent);
		} else {
			overwrite(lineNumber, lineContent);
		}

		return null;
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
