package fileSystem.parser;

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
	public String getCommand(List<String> options, List<String> arguments) {
		String[] lineSplitted = scanner.nextLine().split(" ");

		String command = null;

		for (String word : lineSplitted) {
			if (!word.equals("")) {
				if (command == null) {
					command = word;
				} else if (isOption(word)) {
					options.add(word);
				} else {
					arguments.add(word);
				}
			}
		}

		return command;
	}
	
	@Override
	public void close() {
		scanner.close();
	}

	private boolean isOption(String str) {
		return str.matches("-\\w+|--\\w+");
	}
}
