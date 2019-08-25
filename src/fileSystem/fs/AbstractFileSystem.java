package fileSystem.fs;

import java.util.List;

public interface AbstractFileSystem {
	abstract String makeDirectory(String absolutePath);

	abstract String createTextFile(String absolutePath);

	abstract String getTextFileContent(String absolutePath);

	abstract String writeToTextFile(String absolutePath, int line, String content, boolean overwrite);

	abstract List<String> getDirectoryContent(String absolutePath, FilterBy option);
}
