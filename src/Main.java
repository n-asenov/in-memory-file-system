import filesystem.VirtualFileSystem;
import output.ConsoleOutput;
import parser.Parser;
import parser.StandardInputParser;
import terminal.Terminal;

public class Main {
    public static void main(String[] args) {
	Parser inputParser = new StandardInputParser();
	Terminal terminal = new Terminal(new VirtualFileSystem(), inputParser, new ConsoleOutput());

	terminal.run();
	inputParser.close();
    }
}
