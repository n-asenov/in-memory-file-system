package filesystem;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import commands.exception.InvalidArgumentException;
import filesystem.TextFile;

public class TextFileTest {
    private TextFile file;

    @Before
    public void init() {
	file = new TextFile("test");
    }

    @Test
    public void overwrite_WriteContentOnNewLine_NewContentWrittenOnTheLine() {
	String lineContent = "New line";

	file.overwrite(1, lineContent);

	assertEquals(lineContent, file.getContent());
	int expectedTextFileSize = lineContent.length() + 1;
	int actualTextFileSize = file.getSize();
	assertEquals(expectedTextFileSize, actualTextFileSize);
    }

    @Test
    public void append_AppendContent_ContentAppendedToExistingContent() {
	int lineNumber = 1;
	String lineContent = "Existing content";

	file.append(lineNumber, lineContent);

	String expectedContent = lineContent;
	int expectedSize = lineContent.length() + 1;

	lineContent = " New Content";
	expectedContent += lineContent;
	expectedSize += lineContent.length();

	file.append(lineNumber, lineContent);

	assertEquals(expectedContent, file.getContent());
	assertEquals(expectedSize, file.getSize());
    }

    @Test
    public void overwrite_OverwriteLine_OnlyTheNewContentIsInTheLine() {
	int lineNumber = 1;
	String lineContent = "Current content";

	file.overwrite(lineNumber, lineContent);

	lineContent = "New content";

	file.overwrite(lineNumber, lineContent);

	assertEquals(lineContent, file.getContent());
	int expectedTextFileSize = lineContent.length() + 1;
	assertEquals(expectedTextFileSize, file.getSize());
    }

    @Test
    public void overwrite_OverwriteEmptyLine_NewContentWrittenToTheLine() {
	String newContent = "Hello world";

	file.overwrite(1, newContent);

	assertEquals(newContent, file.getContent());
	int expectedTextFileSize = newContent.length() + 1;
	assertEquals(expectedTextFileSize, file.getSize());
    }

    @Test
    public void getContent_SingleLineContent_ContentReturnedAsString() {
	file.overwrite(1, "Hello");

	assertEquals("Hello", file.getContent());
    }

    @Test
    public void getContent_SeveralConsecutiveLines_ContentReturnedAsString() {
	StringBuilder expectedResult = new StringBuilder();
	String newLine = System.lineSeparator();

	file.overwrite(1, "Hello");
	expectedResult.append("Hello" + newLine);
	file.overwrite(2, "Hello, World");
	expectedResult.append("Hello, World" + newLine);
	file.overwrite(3, "Hello Again");
	expectedResult.append("Hello Again");

	assertEquals(expectedResult.toString(), file.getContent());
    }

    @Test
    public void getContent_TwoContentLinesWithGapInbetween_ContentReturnedAsString() {
	StringBuilder expectedResult = new StringBuilder();
	String newLine = System.lineSeparator();

	file.overwrite(1, "Hello");
	expectedResult.append("Hello" + newLine);
	expectedResult.append(newLine);
	file.overwrite(3, "Again");
	expectedResult.append("Again");

	assertEquals(expectedResult.toString(), file.getContent());
    }

    @Test
    public void getContent_SeveralContentLinesWithGapInbetween_ContentReturnedAsString() {
	StringBuilder expectedResult = new StringBuilder();
	String newLine = System.lineSeparator();

	expectedResult.append(newLine);
	expectedResult.append(newLine);
	file.overwrite(3, "Hello");
	expectedResult.append("Hello" + newLine);
	expectedResult.append(newLine);
	file.overwrite(5, "Hello Again");
	expectedResult.append("Hello Again" + newLine);
	file.overwrite(6, "World");
	expectedResult.append("World");

	assertEquals(expectedResult.toString(), file.getContent());
    }

    @Test
    public void getContent_EmptyFile_ReturnedEmptyString() {
	assertEquals("", file.getContent());
	assertEquals(0, file.getSize());
    }

    @Test(expected = InvalidArgumentException.class)
    public void removeContentFromLines_StartIsBiggerThanEnd_ThrowInvalidArgumentException()
	    throws InvalidArgumentException {
	int start = 1;
	int end = 5;

	file.removeContentFromLines(end, start);
    }

    @Test(expected = InvalidArgumentException.class)
    public void removeContentFromLines_RemoveContentFromNegativeLineNumber_ThrowInvalidArgumentException()
	    throws InvalidArgumentException {
	int start = -1;
	int end = 2;

	file.removeContentFromLines(start, end);
    }

    @Test
    public void removeContentFromLines_RemoveContentFromAllLines_EmptyTextFile() throws InvalidArgumentException {
	int start = 1;
	int end = 5;

	for (int i = start; i < end; i++) {
	    file.overwrite(i, "Hello");
	}

	file.removeContentFromLines(start, end);

	assertEquals("", file.getContent());
	assertEquals(0, file.getSize());
    }

    @Test
    public void getNumberOfWords_EmptyFile_Return0() {
	int expectedNumberOfWords = 0;
	int actualNumberOfWords = file.getNumberOfWords();

	assertEquals(expectedNumberOfWords, actualNumberOfWords);
    }

    @Test
    public void getNumberOfWords_FileWithOneLine_ReturnNumberOfWords() {
	file.overwrite(1, "Hello World");

	int expectedNumberOfWords = 2;
	int actualNumberOfWords = file.getNumberOfWords();

	assertEquals(expectedNumberOfWords, actualNumberOfWords);
    }

    @Test
    public void getNumberOfWords_FileWithSeveralLines_ReturnNumberOfWords() {
	file.overwrite(1, "hello world");
	file.overwrite(3, "hello");
	file.overwrite(5, "hello world");

	int expectedNumberOfWords = 5;
	int actualNumberOfWords = file.getNumberOfWords();

	assertEquals(expectedNumberOfWords, actualNumberOfWords);
    }

    @Test
    public void getNumberOfLines_EmptyTextFile_Return0() {
	assertEquals(0, file.getNumberOfLines());
    }

    @Test
    public void getNumberOfLines_NonEmptyFile_ReturnNumberOfLinesInTextFile() {
	int lastLine = 5;
	file.overwrite(lastLine, "hello world");
	assertEquals(lastLine, file.getNumberOfLines());
    }
}
