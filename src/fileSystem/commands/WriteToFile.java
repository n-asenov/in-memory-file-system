package fileSystem.commands;

import java.util.List;

import fileSystem.fs.FileSystem;

public class WriteToFile implements Command {
	private FileSystem fs;

	public WriteToFile(FileSystem fs) {
		this.fs = fs;
	}

	@Override
	public void execute(List<String> options, List<String> arguments) {
		if (options == null && arguments.size() == 3) {
			String file = arguments.get(0);
			int lineNumber = Integer.parseInt(arguments.get(1));
			String lineContent = arguments.get(2);

			fs.writeToFile(file, lineNumber, lineContent, false);
		}

	}

}
