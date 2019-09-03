package fileSystem.fs;

import static org.junit.Assert.*;

import org.junit.Test;

public class TextFileTest {
	@Test (expected = IllegalArgumentException.class)
	public void write_WriteContentOnInvalidLine_ThrowIllegalArgumentException() {
		TextFile file = new TextFile("test");

		file.write(-20, "Hello, World", false);
	}

	@Test
	public void write_WriteContentOnNewLine_NewContentWrittenOnTheLine() {
		TextFile file = new TextFile("test");

		int lineNumber = 1;
		String lineContent = "New line";
		int expectedSize = lineContent.length() + 1;

		file.write(lineNumber, lineContent, false);
		
		assertEquals(lineContent, file.getContent());
		assertEquals(expectedSize, file.getSize());
	}

	@Test
	public void write_AppendContent_ContentAppendedToExistingContent() {
		TextFile file = new TextFile("test");

		int lineNumber = 1;
		String lineContent = "Existing content";

		file.write(lineNumber, lineContent, false);

		String expectedContent = lineContent;
		int expectedSize = lineContent.length() + 1;

		lineContent = " New Content";
		expectedContent += lineContent;
		expectedSize += lineContent.length();

		file.write(lineNumber, lineContent, false);
		
		assertEquals(expectedContent, file.getContent());
		assertEquals(expectedSize, file.getSize());
	}

	@Test
	public void write_OverwriteLine_OnlyTheNewContentIsInTheLine() {
		TextFile file = new TextFile("test");

		int lineNumber = 1;
		String lineContent = "Current content";

		file.write(lineNumber, lineContent, false);

		lineContent = "New content";

		String expectedContent = lineContent;
		int expectedSize = lineContent.length() + 1;

		file.write(lineNumber, lineContent, true);
		
		assertEquals(expectedContent, file.getContent());
		assertEquals(expectedSize, file.getSize());
	}

	@Test
	public void write_OverwriteEmptyLine_NewContentWrittenToTheLine() {
		TextFile file = new TextFile("test");

		String newContent = "Hello world";

		file.write(1, newContent, true);

		int expectedSize = newContent.length() + 1;

		assertEquals(newContent, file.getContent());
		assertEquals(expectedSize, file.getSize());
	}

	@Test
	public void getContent_SingleLineContent_ContentReturnedAsString() {
		TextFile file = new TextFile("test");

		file.write(1, "Hello", false);

		String result = file.getContent();
		String expectedResult = "Hello";

		assertEquals(expectedResult, result);
	}

	@Test
	public void getContent_SeveralConsecutiveLines_ContentReturnedAsString() {
		TextFile file = new TextFile("test");
		StringBuilder expectedResult = new StringBuilder();
		String newLine = System.lineSeparator();

		file.write(1, "Hello", false);
		expectedResult.append("Hello" + newLine);
		file.write(2, "Hello, World", false);
		expectedResult.append("Hello, World" + newLine);
		file.write(3, "Hello Again", false);
		expectedResult.append("Hello Again");

		String result = file.getContent();

		assertEquals(expectedResult.toString(), result);
	}

	@Test
	public void getContent_TwoContentLinesWithGapInbetween_ContentReturnedAsString() {
		TextFile file = new TextFile("test");
		StringBuilder expectedResult = new StringBuilder();
		String newLine = System.lineSeparator();

		file.write(1, "Hello", false);
		expectedResult.append("Hello" + newLine);
		expectedResult.append(newLine); // empty line between line 1 and 3
		file.write(3, "Again", false);
		expectedResult.append("Again");

		String result = file.getContent();

		assertEquals(expectedResult.toString(), result);
	}

	@Test
	public void getContent_SeveralContentLinesWithGapInbetween_ContentReturnedAsString() {
		TextFile file = new TextFile("test");
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
		expectedResult.append("World");

		String result = file.getContent();

		assertEquals(expectedResult.toString(), result);
	}

	@Test
	public void getContent_EmptyFile_ReturnedEmptyString() {
		TextFile file = new TextFile("test");

		String result = file.getContent();
		String expectedResult = "";

		assertEquals(expectedResult, result);
		assertEquals(0, file.getSize());
	}
}
