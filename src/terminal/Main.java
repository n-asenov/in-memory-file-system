package terminal;
import filesystem.VirtualFileSystem;
import output.ConsoleOutput;
import parser.Parser;
import parser.StandardInputParser;

public class Main {
    public static void main(String[] args) {
	Parser inputParser = new StandardInputParser(System.in);
	Terminal terminal = new Terminal(new VirtualFileSystem(), inputParser, new ConsoleOutput());

	terminal.run();
	inputParser.close();
    }
}
