package fileSystem.commands;

import java.util.List;

import fileSystem.fs.AbstractFileSystem;

public class WriteToTextFile implements Command {
	private AbstractFileSystem fs;

	public WriteToTextFile(AbstractFileSystem fs) {
		this.fs = fs;
	}

	@Override
	public String execute(List<String> options, List<String> arguments) {
		boolean overwrite = false;

		for (String option : options) {
			if (!option.equals("-overwrite")) {
				return "Invalid option!";
			}
			overwrite = true;
		}

		if (arguments.size() != 3) {
			return "Invalid number of arguments!";
		}

		if (!isNumber(arguments.get(1))) {
			return "Second argument must be a positive number!";
		}

		String absolutePath = arguments.get(0);
		int line = Integer.parseInt(arguments.get(1));
		String lineContent = arguments.get(2);

		String result = fs.writeToTextFile(absolutePath, line, lineContent, overwrite);

		return result;
	}

	private boolean isNumber(String number) {
		return number.matches("\\d*");
	}
	
}
