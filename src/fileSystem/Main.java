package fileSystem;

import fileSystem.fs.VirtualFileSystem;
import fileSystem.output.ConsoleOutput;
import fileSystem.parser.StandardInputParser;
import fileSystem.terminal.Terminal;

public class Main {
    public static void main(String[] args) {
        Terminal terminal = new Terminal(new VirtualFileSystem(), new StandardInputParser(), new ConsoleOutput());

        terminal.run();
    }
}
