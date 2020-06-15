package terminal;

import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import commands.Pipe;
import filesystem.VirtualFileSystem;
import filesystem.exceptions.InvalidArgumentException;
import filesystem.exceptions.NotEnoughMemoryException;
import output.Output;
import parser.Parser;
import path.Path;

public class Terminal {
    private Parser parser;
    private Output output;
    private Pipe command;

    public Terminal(VirtualFileSystem fileSystem, Parser parser, Output output) {
	this.parser = parser;
	this.output = output;
	command = new Pipe(fileSystem, new Path());
    }

    public void run() {
	Set<String> options = new HashSet<>();
	
	while (true) {
	    if (parser.hasNextLine()) {
		try {
		    List<String> arguments = parser.getCommandLine();
		    String result = command.execute(arguments, options);
		    output.print(result);
		} catch (FileAlreadyExistsException | FileNotFoundException | NotEnoughMemoryException
			| InvalidArgumentException e) {
		    output.print(e.getMessage());
		}
	    }
	}
    }
}
