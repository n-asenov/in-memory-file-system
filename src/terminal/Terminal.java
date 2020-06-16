package terminal;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileAlreadyExistsException;
import java.util.List;
import java.util.Set;

import commands.Command;
import commands.CommandFactory;
import commands.exception.InvalidArgumentException;
import filesystem.VirtualFileSystem;
import filesystem.exceptions.NotEnoughMemoryException;
import output.Output;
import parser.CommandParser;
import parser.InputParser;
import path.Path;

public class Terminal {
    private VirtualFileSystem fileSystem;
    private InputStream input;
    private OutputStream output;

    public Terminal(VirtualFileSystem fileSystem, InputStream input, OutputStream output) {
	this.fileSystem = fileSystem;
	this.input = input;
	this.output = output;
    }

    public static void main(String[] args) {
	VirtualFileSystem fileSystem = new VirtualFileSystem();
	InputStream input = System.in;
	OutputStream output = System.out;
	Terminal terminal = new Terminal(fileSystem, input, output);
	terminal.run();
    }

    public void run() {
	try (InputParser inputParser = new InputParser(input); Output outputPrinter = new Output(output)) {
	    CommandParser commandParser = new CommandParser();
	    Path currentDirectory = new Path();
	    CommandFactory commandFactory = new CommandFactory(fileSystem, currentDirectory);

	    while (true) {
		try {
		    if (inputParser.hasNextLine()) {
			List<String> commandLine = inputParser.getCommandLine();
			String commandName = commandParser.getCommandName(commandLine);
			Command command = commandFactory.getCommand(commandName);
			List<String> commandArguments = commandParser.getCommandArguments(commandLine);
			Set<String> commandOptions = commandParser.getCommandOptions(commandLine);
			String commmandResult = command.execute(commandArguments, commandOptions);
			outputPrinter.println(commmandResult);
		    }
		} catch (InvalidArgumentException | FileAlreadyExistsException | FileNotFoundException
			| NotEnoughMemoryException e) {
		    outputPrinter.println(e.getMessage());
		}
	    }
	}
    }
}
