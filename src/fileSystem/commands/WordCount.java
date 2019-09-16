package fileSystem.commands;

import java.io.FileNotFoundException;
import java.util.List;

import fileSystem.Path;
import fileSystem.fs.AbstractFileSystem;
import fileSystem.fs.InvalidArgumentException;

public class WordCount implements Command {
	private AbstractFileSystem fileSystem;
	private Path currentDirectory;

	public WordCount(AbstractFileSystem fileSystem, Path currentDirectory) {
		this.fileSystem = fileSystem;
		this.currentDirectory = currentDirectory;
	}

	@Override
	public String execute(List<String> options, List<String> arguments)
			throws InvalidArgumentException, FileNotFoundException {
		validateOptions(options);
		validateArguments(arguments);

		Integer result = arguments.size();
		
		if (result.equals(1)) {
			result = fileSystem.getWordCount(currentDirectory.getAbsolutePath(arguments.get(0)));
		}
		
		return result.toString();
	}

	private void validateOptions(List<String> options) throws InvalidArgumentException {
		if (options.size() != 0) {
			throw new InvalidArgumentException("Invalid option");
		}
	}

	private void validateArguments(List<String> arguments) throws InvalidArgumentException {
		if (arguments.size() == 0) {
			throw new InvalidArgumentException("Invalid number of arguments");
		}
	}

	/*
	 * @Override public String execute(List<String> options, List<String> arguments)
	 * throws InvalidArgumentException, FileNotFoundException { boolean countLines =
	 * false;
	 * 
	 * for (String option : options) { validateOption(option); countLines = true; }
	 * 
	 * if (countLines) { return getNumberOfLines(arguments).toString(); }
	 * 
	 * return getNumberOfWords(arguments).toString(); }
	 */
	/*
	 * private Integer getNumberOfWords(List<String> arguments) throws
	 * FileNotFoundException, InvalidArgumentException { if (isText(arguments)) {
	 * return calculateNumberOfWords(arguments.toString()); }
	 * 
	 * int counter = 0;
	 * 
	 * for (String argument : arguments) { counter += calculateNumberOfWords(
	 * fileSystem.getTextFileContent(currentDirectory.getAbsolutePath(argument))); }
	 * 
	 * return counter; }
	 * 
	 * private boolean isText(List<String> arguments) { String firstArgument =
	 * arguments.get(0); String lastArgument = arguments.get(arguments.size() - 1);
	 * 
	 * return firstArgument.indexOf("\"") == 0 && lastArgument.indexOf("\"") ==
	 * lastArgument.length() - 1; }
	 * 
	 * private Integer getNumberOfLines(List<String> arguments) throws
	 * FileNotFoundException, InvalidArgumentException { if (isText(arguments)) {
	 * return calculateNumberOfLines(arguments.toString()); }
	 * 
	 * int counter = 0;
	 * 
	 * for (String argument : arguments) { counter += calculateNumberOfLines(
	 * fileSystem.getTextFileContent(currentDirectory.getAbsolutePath(argument))); }
	 * 
	 * return counter; }
	 * 
	 * private int calculateNumberOfWords(String text) { int numberOfWords = 0;
	 * 
	 * String[] lines = splitTextIntoLines(text);
	 * 
	 * for (String line : lines) { String[] words = line.split(" ");
	 * 
	 * for (String word : words) { if (!word.equals("")) { numberOfWords++; } } }
	 * 
	 * return numberOfWords; }
	 * 
	 * private int calculateNumberOfLines(String text) { return
	 * splitTextIntoLines(text).length; }
	 * 
	 * private String[] splitTextIntoLines(String text) { return
	 * text.split(System.lineSeparator()); }
	 * 
	 * private void validateOption(String option) throws InvalidArgumentException {
	 * if (!option.equals("-l")) { throw new
	 * InvalidArgumentException("Invalid option"); } }
	 */
}
