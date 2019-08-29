package fileSystem.terminal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fileSystem.commands.*;
import fileSystem.fs.AbstractFileSystem;
import fileSystem.output.Output;
import fileSystem.parser.Parser;

public class Terminal {
	private AbstractFileSystem fs;
	private Parser parser;
	private Output output;
	private HashMap<String, Command> commands;
	private String currentDirectory;

	public Terminal(AbstractFileSystem fs, Parser parser, Output output) {
		this.fs = fs;
		this.parser = parser;
		this.output = output;
		currentDirectory = "/home";

		commands = new HashMap<String, Command>();
		// commands.put("cd", new ChangeDirectory());
		commands.put("mkdir", new MakeDirectory(fs));
		commands.put("create_file", new CreateTextFile(fs));
		commands.put("cat", new PrintTextFileContent(fs));
		commands.put("write", new WriteToTextFile(fs));
		commands.put("ls", new ListDirectoryContent(fs));
	}

	public void run() {
		while (true) {
			if (parser.hasNextLine()) {
				List<String> options = new ArrayList<String>();
				List<String> arguments = new ArrayList<String>();
				String commnandName = parser.getCommand(options, arguments);
				
				if (commnandName != null) {
					Command command = commands.get(commnandName);
					
					if(command != null) {
						String result = command.execute(options, arguments);

						if (result != null && !result.equals("")) {
							output.print(result);
						}
					}
					else {
						output.print("Invalid commnand!");
					}
				}
			}
		}
	}
}
