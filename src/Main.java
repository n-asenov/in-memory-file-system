

import filesystem.VirtualFileSystem;
import output.ConsoleOutput;
import parser.StandardInputParser;
import terminal.Terminal;

public class Main {
    public static void main(String[] args) {
        Terminal terminal = new Terminal(new VirtualFileSystem(), new StandardInputParser(), new ConsoleOutput());

        terminal.run();
    }
}
