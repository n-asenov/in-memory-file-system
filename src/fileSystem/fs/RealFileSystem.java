package fileSystem.fs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import fileSystem.output.ConsoleOutput;
import fileSystem.output.Output;

public class RealFileSystem implements AbstractFileSystem {
	private String root = "root";

	public RealFileSystem() {
		try {
			Files.createDirectories(Paths.get(root, "home"));
		} catch (IOException e) {
			Output output = new ConsoleOutput();
			output.print(e.getMessage());
		}
	}

	@Override
	public void makeDirectory(String absolutePath) throws IOException {
		Files.createDirectory(Paths.get(root, absolutePath));
	}

	@Override
	public void createTextFile(String absolutePath) throws IOException {
		Files.createFile(Paths.get(root, absolutePath));
	}

	@Override
	public String getTextFileContent(String absolutePath) throws IOException {
		StringBuilder content = new StringBuilder();

		try (BufferedReader reader = Files.newBufferedReader(Paths.get(root, absolutePath))) {
			String line;

			while ((line = reader.readLine()) != null) {
				content.append(line).append(System.lineSeparator());
			}
		}

		return content.toString();
	}

	@Override
	public void writeToTextFile(String absolutePath, int line, String content, boolean overwrite) throws IOException {
		int counter = 1;
		StringBuilder newContent = new StringBuilder();

		try (BufferedReader reader = Files.newBufferedReader(Paths.get(root, absolutePath))) {
			String lineContent;
			while ((lineContent = reader.readLine()) != null) {
				if (counter != line) {
					newContent.append(lineContent).append(System.lineSeparator());
				} else {
					if (!overwrite) {
						newContent.append(lineContent).append(" ");
					}

					newContent.append(content).append(System.lineSeparator());
				}
				counter++;
			}

			if (counter <= line) {
				while (counter < line) {
					newContent.append(System.lineSeparator());
					counter++;
				}

				newContent.append(content).append(System.lineSeparator());
			}
		}

		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(root, absolutePath))) {
			writer.write(newContent.toString());
		}
	}

	@Override
	public List<String> getDirectoryContent(String absolutePath, FilterBy option) throws IOException {
		List<String> result = new ArrayList<String>();

		try (DirectoryStream<Path> content = Files.newDirectoryStream(Paths.get(root, absolutePath))) {
			List<Path> list = new ArrayList<Path>();
			for (Path file : content) {
				list.add(file);
			}

			Collections.sort(list, getComparator(option));

			for (int i = 0; i < list.size(); i++) {
				result.add(list.get(i).getFileName().toString());
			}
		}

		return result;
	}

	@Override
	public boolean isDirectory(String absolutePath) {
		return Files.isDirectory(Paths.get(root, absolutePath));
	}

	@Override
	public Integer getWordCount(String absolutePath) throws IOException {
		int counter = 0;

		try (BufferedReader reader = Files.newBufferedReader(Paths.get(root, absolutePath))) {
			String line;

			while ((line = reader.readLine()) != null) {
				counter += line.split(" ").length;
			}
		}

		return counter;
	}

	@Override
	public Integer getLineCount(String absolutePath) throws IOException {
		int counter = 0;

		try (BufferedReader reader = Files.newBufferedReader(Paths.get(root, absolutePath))) {
			while (reader.readLine() != null) {
				counter++;
			}
		}

		return counter;
	}

	@Override
	public void removeTextFile(String absolutePath) throws IOException {
		Files.delete(Paths.get(root, absolutePath));
	}

	@Override
	public void removeContentFromLinesInTextFile(String absolutePath, int start, int end) throws IOException {
		int counter = 1;
		StringBuilder newContent = new StringBuilder();

		try (BufferedReader reader = Files.newBufferedReader(Paths.get(root, absolutePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (counter < start || counter > end) {
					newContent.append(line).append(System.lineSeparator());
				}

				counter++;
			}
		}

		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(root, absolutePath))) {
			writer.write(newContent.toString());
		}
	}

	private Comparator<Path> getComparator(FilterBy option) {
		if (option == FilterBy.SIZE_DESCENDING) {
			return (f1, f2) -> {
				long f1Size = 0, f2Size = 0;
				try {
					f1Size = Files.size(f1);
					f2Size = Files.size(f2);
				} catch (IOException e) {

				}

				return Long.compare(f2Size, f1Size);
			};
		}

		// Default sort
		return (f1, f2) -> f1.compareTo(f2);
	}
}
