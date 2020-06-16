package terminal;
import java.io.InputStream;

import filesystem.VirtualFileSystem;
import output.ConsoleOutput;

public class Main {
    public static void main(String[] args) {
	InputStream input = System.in;
	Terminal terminal = new Terminal(new VirtualFileSystem(), input, new ConsoleOutput());
	
	terminal.run();
    }
}
