package fileSystem.fs;

import java.util.TreeMap;

public class File {
	private String name;
	private TreeMap<Integer, String> content;
	private int size;

	// default constructor ?

	public File(String name) {
		this.name = name;
		content = new TreeMap<Integer, String>();
		size = 0;
	}

	public String getName() {
		return name;
	}

	public TreeMap<Integer, String> getContent() {
		return content;
	}

	public int getSize() {
		return size;
	}

	public void write(int lineNumber, String lineContent, boolean overwrite) {
		if (!overwrite) {
			write(lineNumber, lineContent);
		} else {
			overwrite(lineNumber, lineContent);
		}
	}

	public String printContent() {
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

	private void overwrite(int lineNumber, String lineContent) {
		String currentContent = content.get(lineNumber);

		if (currentContent != null) {
			size -= currentContent.length();
		}

		content.put(lineNumber, lineContent);
		size += lineContent.length();
	}

	private void write(int lineNumber, String lineContent) {
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
}
