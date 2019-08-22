package fileSystem.commands;

import java.util.List;

import fileSystem.fs.FileSystem;;

public class CreateFile implements Command {
	private FileSystem fs;

	public CreateFile(FileSystem fs) {
		this.fs = fs;
	}

	@Override
	public void execute(List<String> options, List<String> arguments) {
		if (options == null && arguments.size() == 1) {
			fs.createFile(arguments.get(0));
		} else if (options != null) {
			System.out.println("Invalid options!");
		} else {
			System.out.println("Invalid arguments!");
		}
	}
}
