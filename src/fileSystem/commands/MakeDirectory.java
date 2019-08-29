package fileSystem.commands;

import java.util.List;

import fileSystem.fs.AbstractFileSystem;

public class MakeDirectory implements Command {
	private AbstractFileSystem fs;

	public MakeDirectory(AbstractFileSystem fs) {
		this.fs = fs;
	}

	@Override
	public String execute(List<String> options, List<String> arguments) {
		if (options.size() != 0) {
			return "Invalid option";
		}

		StringBuilder results = new StringBuilder();

		for (String argument : arguments) {
			String result = fs.makeDirectory(argument);
			
			if (result != null) {
				results.append("Cannot create directory: ");
				results.append(argument + " : ");
				results.append(result);
			}
		}

		return results.toString();
	}
}		
