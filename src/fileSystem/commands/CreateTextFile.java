package fileSystem.commands;

import java.util.List;

import fileSystem.Path;
import fileSystem.fs.AbstractFileSystem;

public class CreateTextFile implements Command {
	private AbstractFileSystem fs;
	private Path currentDirectory;
	
	public CreateTextFile(AbstractFileSystem fs, Path currentDirectory) {
		this.fs = fs;
		this.currentDirectory = currentDirectory;
	}	
	
	@Override
	public String execute(List<String> options, List<String> arguments) {
		if (options.size() != 0) {
			return "Invalid option!";
		}

		StringBuilder results = new StringBuilder();

		for (String argument : arguments) {
			String result = fs.createTextFile(currentDirectory.getAbsolutePath(argument));
			
			if (result != null) {
				results.append("Cannot create text file: ");
				results.append(argument + " : ");
				results.append(result);
			}
		}

		return results.toString();
	}
}
