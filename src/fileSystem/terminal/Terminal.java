package fileSystem.terminal;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.List;

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
	private CommandFactory factory;
	private Path currentDirectory;

	public Terminal(AbstractFileSystem fs, Parser parser, Output output) {
		this.parser = parser;
		this.output = output;
		currentDirectory = new Path();	
		factory = new CommandFactory(fs, currentDirectory);
	}

	public void run() {
		while (true) {
			if (parser.hasNextLine()) {
				List<String> options = new ArrayList<String>();
				List<String> arguments = new ArrayList<String>();
				String commnandName = parser.getCommand(options, arguments);

				if (commnandName != null) {
					Command command = factory.make(commnandName);

					if (command != null) {
						 try {
							output.print(command.execute(options, arguments));
						} catch (NotDirectoryException | FileAlreadyExistsException | IllegalArgumentException
								| FileNotFoundException | NotEnoughMemoryException | InvalidArgumentException e) {
							output.print(e.getMessage());
						} catch (IOException e) {
							output.print(e.getMessage());
						}
					} else {
						output.print("Invalid commnand!");
					}
				}
			}
		}
	}
}
