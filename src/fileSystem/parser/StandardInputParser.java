package fileSystem.parser;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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
		return Arrays.stream(scanner.nextLine().split(" ")).collect(Collectors.toList());
	}
	
	@Override
	public void close() {
		scanner.close();
	}
}
