package terminal;

import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.util.HashSet;

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

    public Terminal(final VirtualFileSystem fileSystem, final Parser parser, final Output output) {
	this.parser = parser;
	this.output = output;
	command = new Pipe(fileSystem, new Path());
    }

    public void run() {
	while (true) {
	    if (parser.hasNextLine()) {
		try {
		    output.print(command.execute(parser.getCommandLine(), new HashSet<>()));
		} catch (FileAlreadyExistsException | FileNotFoundException | NotEnoughMemoryException
			| InvalidArgumentException e) {
		    output.print(e.getMessage());
		}
	    }
	}
    }
}
