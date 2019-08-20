package fileSystem;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

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

		String expectedContent = lineContent;
		int expectedSize = lineContent.length() + 1;

		file.write(lineNumber, lineContent, false);

		assertEquals(expectedSize, file.getSize());

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
	public void printContent_SingleLineContent_ContentPrintedOnConsole() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		PrintStream originalOut = System.out;

		System.setOut(new PrintStream(outContent));

		File file = new File("test");

		file.write(1, "Hello", false);

		file.printContent();

		assertEquals(file.getContent().get(1) + "\n", outContent.toString());

		System.setOut(originalOut);
	}

	@Test
	public void printContent_SeveralConsecutiveLines_ContentPrintedOnConsole() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		PrintStream originalOut = System.out;

		System.setOut(new PrintStream(outContent));

		File file = new File("test");
		StringBuilder expectedResult = new StringBuilder();

		file.write(1, "Hello", false);
		expectedResult.append("Hello\n");
		file.write(2, "Hello, World", false);
		expectedResult.append("Hello, World\n");
		file.write(3, "Hello Again", false);
		expectedResult.append("Hello Again\n");

		file.printContent();

		assertEquals(expectedResult.toString(), outContent.toString());

		System.setOut(originalOut);
	}

	@Test
	public void printContent_TwoContentLinesWithGapInbetween_ContentPrintedOnConsole() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		PrintStream originalOut = System.out;

		System.setOut(new PrintStream(outContent));

		File file = new File("test");
		StringBuilder expectedResult = new StringBuilder();

		file.write(1, "Hello", false);
		expectedResult.append("Hello\n\n");
		file.write(3, "Again", false);
		expectedResult.append("Again\n");

		file.printContent();

		assertEquals(expectedResult.toString(), outContent.toString());

		System.setOut(originalOut);
	}
	
	@Test
	public void printContent_SeveralContentLinesWithGapInbetween_ContentPrintedOnConsole() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		PrintStream originalOut = System.out;

		System.setOut(new PrintStream(outContent));

		File file = new File("test");
		StringBuilder expectedResult = new StringBuilder();
		
		file.write(3, "Hello", false);
		expectedResult.append("\n\nHello\n\n");
		file.write(5, "Hello Again", false);
		expectedResult.append("Hello Again\n\n\n");
		file.write(8, "World", false);
		expectedResult.append("World\n");

		file.printContent();

		assertEquals(expectedResult.toString(), outContent.toString());

		System.setOut(originalOut);
	}
}
