package fileSystem.fs;

import static org.junit.Assert.*;

import org.junit.Test;

public class FileTest {
	@Test
	public void write_WriteContentOnNewLine_NewContentWrittenOnTheLine() {
		File file = new File("test");

		int lineNumber = 1;
		String lineContent = "New line";
		int expectedSize = lineContent.length() + 1;

		file.write(lineNumber, lineContent, false);

		assertEquals(lineContent, file.getContent().get(lineNumber));
		assertEquals(expectedSize, file.getSize());
	}

	@Test
	public void write_AppendContent_ContentAppendedToExistingContent() {
		File file = new File("test");

		int lineNumber = 1;
		String lineContent = "Existing content";

		file.write(lineNumber, lineContent, false);

		String expectedContent = lineContent;
		int expectedSize = lineContent.length() + 1;
		
		lineContent = " New Content";
		expectedContent += lineContent;
		expectedSize += lineContent.length();

		file.write(lineNumber, lineContent, false);

		assertEquals(expectedContent, file.getContent().get(lineNumber));
		assertEquals(expectedSize, file.getSize());
	}

	@Test
	public void write_OverwriteLine_OnlyTheNewContentIsInTheLine() {
		File file = new File("test");

		int lineNumber = 1;
		String lineContent = "Current content";

		file.write(lineNumber, lineContent, false);

		lineContent = "New content";

		String expectedContent = lineContent;
		int expectedSize = lineContent.length() + 1;

		file.write(lineNumber, lineContent, true);

		assertEquals(expectedContent, file.getContent().get(lineNumber));
		assertEquals(expectedSize, file.getSize());
	}

	@Test
	public void printContent_SingleLineContent_ContentReturnedAsString() {
		File file = new File("test");

		file.write(1, "Hello", false);
		
		String result = file.printContent();
		String expectedResult = "Hello" + System.lineSeparator();
		
		assertEquals(expectedResult, result);
	}

	@Test
	public void printContent_SeveralConsecutiveLines_ContentReturnedAsString() {
		File file = new File("test");
		StringBuilder expectedResult = new StringBuilder();
		String newLine = System.lineSeparator();
		
		file.write(1, "Hello", false);
		expectedResult.append("Hello" + newLine);
		file.write(2, "Hello, World", false);
		expectedResult.append("Hello, World" + newLine);
		file.write(3, "Hello Again", false);
		expectedResult.append("Hello Again" + newLine);

		String result = file.printContent();

		assertEquals(expectedResult.toString(),result);
	}

	@Test
	public void printContent_TwoContentLinesWithGapInbetween_ContentPrintedOnConsole() {
		File file = new File("test");
		StringBuilder expectedResult = new StringBuilder();
		String newLine = System.lineSeparator();
		
		file.write(1, "Hello", false);
		expectedResult.append("Hello" + newLine);
		expectedResult.append(newLine); //empty line between line 1 and 3
		file.write(3, "Again", false);
		expectedResult.append("Again" + newLine);

		String result = file.printContent();

		assertEquals(expectedResult.toString(), result);
	}
	
	@Test
	public void printContent_SeveralContentLinesWithGapInbetween_ContentPrintedOnConsole() {
		File file = new File("test");
		StringBuilder expectedResult = new StringBuilder();
		String newLine = System.lineSeparator();
		
		expectedResult.append(newLine);
		expectedResult.append(newLine);
		
		file.write(3, "Hello", false);
		expectedResult.append("Hello" + newLine);
		
		expectedResult.append(newLine);
		
		file.write(5, "Hello Again", false);
		expectedResult.append("Hello Again" + newLine);
		
		expectedResult.append(newLine);
		expectedResult.append(newLine);
		
		file.write(8, "World", false);
		expectedResult.append("World" + newLine);

		String result = file.printContent();

		assertEquals(expectedResult.toString(), result);
	}
}
