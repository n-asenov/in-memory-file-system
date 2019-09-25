package fileSystem.terminal;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;

import fileSystem.Path;
import fileSystem.commands.*;
import fileSystem.fs.AbstractFileSystem;
import fileSystem.fs.InvalidArgumentException;
import fileSystem.fs.NotEnoughMemoryException;
import fileSystem.output.Output;
import fileSystem.parser.Parser;

public class Terminal {
	private Parser parser;
	private Output output;
	private Pipe command;

	public Terminal(AbstractFileSystem fs, Parser parser, Output output) {
		this.parser = parser;
		this.output = output;
		command = new Pipe(fs, new Path());
	}

	public void run() {
		while (true) {
			if (parser.hasNextLine()) {
				try {
					output.print(command.execute(new ArrayList<String>(), parser.getCommandLine()));
				} catch (NotDirectoryException | FileAlreadyExistsException | FileNotFoundException
						| NotEnoughMemoryException | InvalidArgumentException e) {
					output.print(e.getMessage());
				} catch (IOException e) {
					output.print(e.getMessage());
				}
			}
		}
	}
}
