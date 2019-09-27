package fileSystem;

import java.util.List;

import fileSystem.fs.RealFileSystem;
import fileSystem.fs.VirtualFileSystem;
import fileSystem.output.ConsoleOutput;
import fileSystem.output.Output;
import fileSystem.parser.Parser;
import fileSystem.parser.StandardInputParser;
import fileSystem.terminal.Terminal;

public class Main {
	public static void main(String[] args) {
		Output output = new ConsoleOutput();
		output.print("real or virtual?");
		Parser input = new StandardInputParser();
		List<String> option = null;

		if (input.hasNextLine()) {
			option = input.getCommandLine();
		}

		Terminal terminal = null;
		
		if (option.get(0).equals("real")) {
			terminal =  new Terminal(new RealFileSystem(), input, output);
		}
		else {
			terminal = new Terminal(new VirtualFileSystem(), input, output);
		}
		
		terminal.run();
	}
}
