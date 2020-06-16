package terminal;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.util.List;
import java.util.Set;

import commands.Command;
import commands.CommandFactory;
import commands.exception.InvalidArgumentException;
import filesystem.VirtualFileSystem;
import filesystem.exceptions.NotEnoughMemoryException;
import output.ConsoleOutput;
import output.Output;
import parser.CommandParser;
import parser.Parser;
import parser.StandardInputParser;
import path.Path;

public class Terminal {
    private VirtualFileSystem fileSystem;
    private InputStream input;
    private Output output;

    public Terminal(VirtualFileSystem fileSystem, InputStream input, Output output) {
	this.fileSystem = fileSystem;
	this.input = input;
	this.output = output;
    }
    
    public static void main(String[] args) {
	InputStream input = System.in;
	Terminal terminal = new Terminal(new VirtualFileSystem(), input, new ConsoleOutput());
	
	terminal.run();
    }
    
    public void run() {
	Parser inputParser = new StandardInputParser(input);
	CommandParser commandParser = new CommandParser();
	Path currentDirectory = new Path();
	CommandFactory commandFactory = new CommandFactory(fileSystem, currentDirectory);

	while (true) {
	    if (inputParser.hasNextLine()) {
		List<String> commandLine = inputParser.getCommandLine();
		String commandName = commandParser.getCommandName(commandLine);
		try {
		    Command command = commandFactory.getCommand(commandName);
		    List<String> commandArguments = commandParser.getCommandArguments(commandLine);
		    Set<String> commandOptions = commandParser.getCommandOptions(commandLine);
		    String commmandResult = command.execute(commandArguments, commandOptions);
		    output.print(commmandResult);
		} catch (InvalidArgumentException | FileAlreadyExistsException | FileNotFoundException
			| NotEnoughMemoryException e) {
		    output.print(e.getMessage());
		}
	    }
	}
    }
}
