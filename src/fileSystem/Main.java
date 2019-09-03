package fileSystem;

import java.nio.file.FileAlreadyExistsException;

import fileSystem.fs.FileSystem;
import fileSystem.output.ConsoleOutput;
import fileSystem.parser.StandardInputParser;
import fileSystem.terminal.Terminal;

public class Main {
	public static void main(String[] args) {
		try {
			Terminal terminal = new Terminal(new FileSystem(), new StandardInputParser(), new ConsoleOutput());
			terminal.run();
		} catch (FileAlreadyExistsException e) {
			System.out.println(e.getMessage());
		}
	}
}
