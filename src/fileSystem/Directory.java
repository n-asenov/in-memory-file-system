package fileSystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

public class Directory {
	private Directory parent;
	private String name;
	private TreeMap<String, Directory> subDirectories;
	private TreeMap<String, File> files;

	public Directory(String name) {
		parent = null;
		this.name = name;
		subDirectories = new TreeMap<String, Directory>();
		files = new TreeMap<String, File>();
	}

	public Directory(String name, Directory parent) {
		this.parent = parent;
		this.name = name;
		subDirectories = new TreeMap<String, Directory>();
		files = new TreeMap<String, File>();
	}

	public Directory getParent() {
		return parent;
	}

	public String getName() {
		return name;
	}

	public TreeMap<String, Directory> getSubDirectories() {
		return subDirectories;
	}

	public TreeMap<String, File> getFiles() {
		return files;
	}

	public void addFile(String name) {
		if (files.get(name) == null) {
			File file = new File(name);
			files.put(name, file);
		} else {
			System.out.println("There is already file with such name!");
		}
	}

	public void addSubDirectory(String name) {
		if (subDirectories.get(name) == null) {
			Directory newSubDirectory = new Directory(name, this);
			subDirectories.put(name, newSubDirectory);
		} else {
			System.out.println("There is already directory with such name!");
		}
	}

	public void listContent(FilterBy option) {
		if (option == FilterBy.DEFAULT) {
			listContent();
		} else {
			listContentSortedByFilter(option);
		}
	}

	private void listContent() {
		System.out.println("Directories:");
		for (String name : subDirectories.keySet()) {
			System.out.print(name + " ");
		}
		System.out.println();

		System.out.println("Files:");
		for (String name : files.keySet()) {
			System.out.print(name + " ");
		}
		System.out.println();
	}

	private void listContentSortedByFilter(FilterBy option) {
		List<File> list = new ArrayList<File>();

		for (String name : files.keySet()) {
			list.add(files.get(name));
		}

		Collections.sort(list, getComparator(option));

		System.out.println("Files:");
		for (File file : list) {
			System.out.print(file.getName() + " ");
		}
		System.out.println();

		System.out.println("Directories:");
		for (String name : subDirectories.keySet()) {
			System.out.print(name + " ");
		}
		System.out.println();
	}

	private Comparator<File> getComparator(FilterBy option) {
		// if (option == FilterBy.SIZE_DESCENDING)
		Comparator<File> compareBySize = (File f1, File f2) -> Integer.compare(f2.getSize(), f1.getSize());
		return compareBySize;
	}
}
