package fileSystem.commands;

import java.util.List;

import fileSystem.fs.AbstractFileSystem;

public class CreateTextFile implements Command {
	private AbstractFileSystem fs;
	
	public CreateTextFile(AbstractFileSystem fs) {
		this.fs = fs;
	}	
	
	@Override
	public String execute(List<String> options, List<String> arguments) {
		if (options.size() != 0) {
			return "Invalid option";
		}

		StringBuilder results = new StringBuilder();

		for (String argument : arguments) {
			String result = fs.createTextFile(argument);
			
			if (result != null) {
				results.append("Cannot create text file: ");
				results.append(argument + " : ");
				results.append(result);
			}
		}

		return results.toString();
	}
}
