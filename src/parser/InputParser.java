package parser;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

public class InputParser implements AutoCloseable, Closeable {
    private Scanner scanner;

    public InputParser(InputStream input) {
	scanner = new Scanner(input);
    }

    public boolean hasNextLine() {
	return scanner.hasNextLine();
    }

    public List<String> getCommandLine() {
	return List.of(scanner.nextLine().split(" "));
    }

    @Override
    public void close() throws IOException {
	scanner.close();
    }
}
