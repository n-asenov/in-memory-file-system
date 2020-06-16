package terminal;

import java.io.FileNotFoundException;
import java.io.IOException;
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
    private CommandParser commandParser;

    public Terminal(VirtualFileSystem fileSystem, InputStream input, OutputStream output) {
	this.fileSystem = fileSystem;
	this.input = input;
	this.output = output;
	commandParser = new CommandParser();
    }

    public static void main(String[] args) {
	VirtualFileSystem fileSystem = new VirtualFileSystem();
	InputStream input = System.in;
	OutputStream output = System.out;
	Terminal terminal = new Terminal(fileSystem, input, output);
	terminal.start();
    }

    public void start() {
	try (InputParser inputParser = new InputParser(input);
		Output outputPrinter = new Output(output)) {
	    run(inputParser, outputPrinter);
	} catch (IOException e) {
	    System.out.println(e.getMessage());
	}
    }

    private void run(InputParser inputParser, Output outputPrinter) {
	Path currentDirectory = new Path();
	CommandFactory commandFactory = new CommandFactory(fileSystem, currentDirectory);

	while (true) {
	    try {
		if (inputParser.hasNextLine()) {
		    String commmandResult = executeCommandLine(inputParser, commandFactory);
		    outputPrinter.println(commmandResult);
		}
	    } catch (InvalidArgumentException | FileAlreadyExistsException | FileNotFoundException
		    | NotEnoughMemoryException e) {
		outputPrinter.println(e.getMessage());
	    }
	}
    }

    private String executeCommandLine(InputParser inputParser, CommandFactory commandFactory)
	    throws InvalidArgumentException, FileAlreadyExistsException, FileNotFoundException,
	    NotEnoughMemoryException {
	List<String> commandLine = inputParser.getCommandLine();
	String commandName = commandParser.getCommandName(commandLine);
	Command command = commandFactory.getCommand(commandName);
	List<String> commandArguments = commandParser.getCommandArguments(commandLine);
	Set<String> commandOptions = commandParser.getCommandOptions(commandLine);
	String commmandResult = command.execute(commandArguments, commandOptions);
	return commmandResult;
    }
}
