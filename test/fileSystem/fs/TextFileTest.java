package fileSystem.fs;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TextFileTest {
	private TextFile file;
	
	@Before
	public void init() {
		file = new TextFile("test");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void write_WriteContentOnInvalidLine_ThrowIllegalArgumentException() {
		file.write(-20, "Hello, World", false);
	}

	@Test
	public void write_WriteContentOnNewLine_NewContentWrittenOnTheLine() {
		String lineContent = "New line";

		file.write(1, lineContent, false);
		
		assertEquals(lineContent, file.getContent());
		assertEquals(lineContent.length() + 1, file.getSize());
	}

	@Test
	public void write_AppendContent_ContentAppendedToExistingContent() {
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
		int lineNumber = 1;
		String lineContent = "Current content";

		file.write(lineNumber, lineContent, false);

		lineContent = "New content";

		file.write(lineNumber, lineContent, true);
		
		assertEquals(lineContent, file.getContent());
		assertEquals(lineContent.length() + 1, file.getSize());
	}

	@Test
	public void write_OverwriteEmptyLine_NewContentWrittenToTheLine() {
		String newContent = "Hello world";

		file.write(1, newContent, true);

		assertEquals(newContent, file.getContent());
		assertEquals(newContent.length() + 1, file.getSize());
	}

	@Test
	public void getContent_SingleLineContent_ContentReturnedAsString() {
		file.write(1, "Hello", false);

		assertEquals("Hello", file.getContent());
	}

	@Test
	public void getContent_SeveralConsecutiveLines_ContentReturnedAsString() {
		StringBuilder expectedResult = new StringBuilder();
		String newLine = System.lineSeparator();

		file.write(1, "Hello", false);
		expectedResult.append("Hello" + newLine);
		file.write(2, "Hello, World", false);
		expectedResult.append("Hello, World" + newLine);
		file.write(3, "Hello Again", false);
		expectedResult.append("Hello Again");

		assertEquals(expectedResult.toString(), file.getContent());
	}

	@Test
	public void getContent_TwoContentLinesWithGapInbetween_ContentReturnedAsString() {
		StringBuilder expectedResult = new StringBuilder();
		String newLine = System.lineSeparator();

		file.write(1, "Hello", false);
		expectedResult.append("Hello" + newLine);
		expectedResult.append(newLine); // empty line between line 1 and 3
		file.write(3, "Again", false);
		expectedResult.append("Again");

		assertEquals(expectedResult.toString(), file.getContent());
	}

	@Test
	public void getContent_SeveralContentLinesWithGapInbetween_ContentReturnedAsString() {
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

		assertEquals(expectedResult.toString(), file.getContent());
	}

	@Test
	public void getContent_EmptyFile_ReturnedEmptyString() {
		assertEquals("", file.getContent());
		assertEquals(0, file.getSize());
	}
}
