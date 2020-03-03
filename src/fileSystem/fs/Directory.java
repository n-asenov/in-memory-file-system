package fileSystem.fs;

import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Directory extends File {
	private Map<String, File> content;

	public Directory(String name) {
		super(name);
		content = new TreeMap<String, File>();
		setUpContent(this);
	}

	public Directory(String name, Directory parent) {
		super(name);
		content = new TreeMap<String, File>();
		setUpContent(parent);
	}

	@Override
	int getSize() {
		int size = 0;

		for (String name : content.keySet()) {
			if (!name.equals(".") && !name.equals("..")) {
				size += content.get(name).getSize();
			}
		}

		return size;
	}

	@Override
	boolean isDirectory() {
		return true;
	}

	@Override
	boolean isTextFile() {
		return false;
	}

	public void addFile(File file) throws FileAlreadyExistsException {
		String fileName = file.getName();

		if (content.get(fileName) != null) {
			throw new FileAlreadyExistsException("File " + fileName + " already exists");
		}

		content.put(fileName, file);
	}

	public File getFile(String name) {
		return content.get(name);
	}

	public File removeTextFile(String name) throws FileNotFoundException {
		File file = content.get(name);

		if (file == null) {
			throw new FileNotFoundException("Text file doesn't exists");
		}

		if (file.isDirectory()) {
			throw new FileNotFoundException("File is directory");
		}

		return content.remove(name);
	}

	public List<String> getContent(FilterBy option) {
		List<File> list = new ArrayList<File>();

		for (String fileName : content.keySet()) {
			if (!fileName.equals(".") && !fileName.equals("..")) {
				list.add(content.get(fileName));
			}
		}

		Collections.sort(list, getComparator(option));

		List<String> result = new ArrayList<String>();

		for (int i = 0; i < list.size(); i++) {
			result.add(list.get(i).getName());
		}

		return result;
	}

	private Comparator<File> getComparator(FilterBy option) {
		if (option == FilterBy.SIZE_DESCENDING) {
			return (f1, f2) -> Integer.compare(f2.getSize(), f1.getSize());
		}

		// Default sort
		return (f1, f2) -> f1.getName().compareTo(f2.getName());
	}

	private void setUpContent(Directory parent) {
		content.put(".", this);
		content.put("..", parent);
	}
}
