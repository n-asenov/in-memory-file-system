package parser;

import java.util.List;
import java.util.Scanner;

public class StandardInputParser implements Parser {
    private Scanner scanner;

    public StandardInputParser() {
	scanner = new Scanner(System.in);
    }

    @Override
    public boolean hasNextLine() {
	return scanner.hasNextLine();
    }

    @Override
    public List<String> getCommandLine() {
	return List.of(scanner.nextLine().split(" "));
    }

    @Override
    public void close() {
	scanner.close();
    }
}
