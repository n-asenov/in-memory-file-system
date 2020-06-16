package filesystem;

import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Directory extends File {
    private Map<String, File> content;

    public Directory(String name) {
	super(name);
	content = new HashMap<>();
	setUpContent(this);
    }

    public Directory(String name, Directory parent) {
	super(name);
	content = new HashMap<>();
	setUpContent(parent);
    }

    private void setUpContent(Directory parent) {
	content.put(".", this);
	content.put("..", parent);
    }

    @Override
    public int getSize() {
	int size = 0;

	for (String fileName : content.keySet()) {
	    if (!fileName.equals(".") && !fileName.equals("..")) {
		size += content.get(fileName).getSize();
	    }
	}

	return size;
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

    public File removeTextFile(String textFileName) throws FileNotFoundException {
	File file = content.get(textFileName);

	if (file == null) {
	    throw new FileNotFoundException("Text file doesn't exists");
	}

	if (file instanceof Directory) {
	    throw new FileNotFoundException("File is directory");
	}

	return content.remove(textFileName);
    }

    public List<String> getContent(Comparator<File> comparator) {
	List<File> sortedContent = new ArrayList<>();

	for (String fileName : content.keySet()) {
	    if (!fileName.equals(".") && !fileName.equals("..")) {
		File file = content.get(fileName);
		sortedContent.add(file);
	    }
	}
	
	Collections.sort(sortedContent, comparator);
	
	return sortedContent.stream()
		.map(file -> file.getName())
		.collect(Collectors.toList());
    }
}
