package fileSystem;

import fileSystem.fs.FileSystem;
import fileSystem.output.ConsoleOutput;
import fileSystem.parser.StandardInputParser;
import fileSystem.terminal.Terminal;

public class Main {
	public static void main(String[] args) {
		Terminal terminal = new Terminal(new FileSystem(), new StandardInputParser(), new ConsoleOutput());
		terminal.run();
	}
}
