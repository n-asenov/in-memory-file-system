package fileSystem;

import java.util.HashMap;

import fileSystem.commands.Command;
import fileSystem.fs.FileSystem;

public class Terminal {
	private String currentDirectory;
	private HashMap<String, Command> commands;
	private FileSystem fileSystem;
	
	public Terminal(FileSystem fileSystem) {
		this.fileSystem = fileSystem;
		currentDirectory = "/home";
		commands = new HashMap<String, Command>();
		
		// map string to command
		/*
		commands.put("cd", new ChangeDirectory(fileSystem));
		commands.put("mkdir", new MakeDirectory(fileSystem));
		commands.put("create_file", new CreateFile(fileSystem));
		commands.put("cat", new PrintFileContent(fileSystem));
		commands.put("write", new WriteToFile(fileSystem));
		commands.put("ls", new ListDirectoryContent(fileSystem));*/
	}
}
