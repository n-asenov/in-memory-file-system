package fileSystem;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

public class FileSystemTest {

	@Test
	public void makeDirectory_newDirectory_DirectoryAddedToFileSystem() {
		FileSystem fs = new FileSystem();

		String absolutePathToNewDirectory = "/home/newDir";

		fs.makeDirectory(absolutePathToNewDirectory);

		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		PrintStream originalOut = System.out;

		System.setOut(new PrintStream(outContent));

		fs.makeDirectory(absolutePathToNewDirectory);

		String expectedOutput = "There is already directory with such name!\n";
		assertEquals(expectedOutput, outContent.toString());

		System.setOut(originalOut);
	}

	@Test
	public void makeDirectory_PathDoesNotExists_MessagePrintedOnConsole() {
		FileSystem fs = new FileSystem();

		String absolutePath = "/wrongPath/dir1";

		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		PrintStream originalOut = System.out;

		System.setOut(new PrintStream(outContent));

		fs.makeDirectory(absolutePath);

		String expectedOutput = "Path doesn't exists!\n";
		assertEquals(expectedOutput, outContent.toString());

		System.setOut(originalOut);
	}

	@Test
	public void createFile_newFile_FileAddedInDirectory() {
		FileSystem fs = new FileSystem();

		String absolutePathToNewFile = "/home/f1";

		fs.createFile(absolutePathToNewFile);

		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		PrintStream originalOut = System.out;

		System.setOut(new PrintStream(outContent));

		fs.createFile(absolutePathToNewFile);

		String expectedOutput = "There is already file with such name!\n";
		assertEquals(expectedOutput, outContent.toString());

		System.setOut(originalOut);
	}

	@Test
	public void createFile_PathDoesNotExists_PrintMessageOnConsole() {
		FileSystem fs = new FileSystem();

		String absolutePath = "/wrongPath/f1";

		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		PrintStream originalOut = System.out;

		System.setOut(new PrintStream(outContent));

		fs.createFile(absolutePath);

		String expectedOutput = "Path doesn't exists!\n";
		assertEquals(expectedOutput, outContent.toString());

		System.setOut(originalOut);
	}

	@Test
	public void printFileContent_ExistingEmptyFile_PrintNothingOnConsole() {
		FileSystem fs = new FileSystem();

		String absolutePath = "/home/f1";

		fs.createFile(absolutePath);

		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		PrintStream originalOut = System.out;

		System.setOut(new PrintStream(outContent));

		fs.printFileContent(absolutePath);

		String expectedOutput = "";
		assertEquals(expectedOutput, outContent.toString());

		System.setOut(originalOut);
	}

	@Test
	public void printFileContent_PathDoesNotExists_PrintMessageOnConsole() {
		FileSystem fs = new FileSystem();

		String absolutePath = "/home/dir1/f1";

		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		PrintStream originalOut = System.out;

		System.setOut(new PrintStream(outContent));

		fs.printFileContent(absolutePath);

		String expectedOutput = "Path doesn't exists!\n";
		assertEquals(expectedOutput, outContent.toString());

		System.setOut(originalOut);
	}

	@Test
	public void printFileContent_FileDoesNotExists_PrintMessageOnConsole() {
		FileSystem fs = new FileSystem();

		String absolutePath = "/home/f1";

		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		PrintStream originalOut = System.out;

		System.setOut(new PrintStream(outContent));

		fs.printFileContent(absolutePath);

		String expectedOutput = "File doesn't exists!\n";
		assertEquals(expectedOutput, outContent.toString());

		System.setOut(originalOut);
	}

	@Test
	public void writeToFile_WriteToNonExistingFile_PrintMessegeOnConsole() {
		FileSystem fs = new FileSystem();

		String absolutePath = "/home/f1";

		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		PrintStream originalOut = System.out;

		System.setOut(new PrintStream(outContent));

		fs.writeToFile(absolutePath, 1, "Test", false);

		String expectedOutput = "File doesn't exists!\n";
		assertEquals(expectedOutput, outContent.toString());

		System.setOut(originalOut);
	}

	@Test
	public void writeToFile_PathToFileDoesNotExists_PrintMessageOnConsole() {
		FileSystem fs = new FileSystem();

		String absolutePath = "/home/dir1/f1";

		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		PrintStream originalOut = System.out;

		System.setOut(new PrintStream(outContent));

		fs.writeToFile(absolutePath, 1, "Test", false);

		String expectedOutput = "Path doesn't exists!\n";
		assertEquals(expectedOutput, outContent.toString());

		System.setOut(originalOut);
	}

	@Test
	public void writeToFile_WriteToEmptyFile_ContentWrittenToFile() {
		FileSystem fs = new FileSystem();

		String absolutePath = "/home/f1";

		fs.createFile(absolutePath);

		fs.writeToFile(absolutePath, 1, "Test", false);

		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		PrintStream originalOut = System.out;

		System.setOut(new PrintStream(outContent));

		fs.printFileContent(absolutePath);

		String expectedOutput = "Test\n";
		assertEquals(expectedOutput, outContent.toString());

		System.setOut(originalOut);
	}

	@Test
	public void writeToFile_WriteContentOnNonEmptyLine_ContentAppendedToLine() {
		FileSystem fs = new FileSystem();

		String absolutePath = "/home/f1";

		fs.createFile(absolutePath);

		fs.writeToFile(absolutePath, 1, "old", false);
		fs.writeToFile(absolutePath, 1, "new", false);

		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		PrintStream originalOut = System.out;

		System.setOut(new PrintStream(outContent));

		fs.printFileContent(absolutePath);

		String expectedOutput = "oldnew\n";
		assertEquals(expectedOutput, outContent.toString());

		System.setOut(originalOut);
	}

	@Test
	public void writeToFile_WriteContentOnNonEmptyLine_OverwriteContent() {
		FileSystem fs = new FileSystem();

		String absolutePath = "/home/f1";

		fs.createFile(absolutePath);

		fs.writeToFile(absolutePath, 1, "old", false);
		fs.writeToFile(absolutePath, 1, "new", true);

		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		PrintStream originalOut = System.out;

		System.setOut(new PrintStream(outContent));

		fs.printFileContent(absolutePath);

		String expectedOutput = "new\n";
		assertEquals(expectedOutput, outContent.toString());

		System.setOut(originalOut);
	}

	@Test
	public void listDirectoryContent_NonExistingDirectory_PrintMessageOnConsole() {
		FileSystem fs = new FileSystem();

		String absolutePath = "/home/dir1";

		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		PrintStream originalOut = System.out;

		System.setOut(new PrintStream(outContent));

		fs.listDirectoryContent(absolutePath, FilterBy.DEFAULT);

		String expectedOutput = "Directory doesn't exists!\n";
		assertEquals(expectedOutput, outContent.toString());

		System.setOut(originalOut);
	}

	@Test
	public void listDirectoryContent_PathDoesNotExists_PrintMessageOnConsole() {
		FileSystem fs = new FileSystem();

		String absolutePath = "/home/dir1/dir2";

		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		PrintStream originalOut = System.out;

		System.setOut(new PrintStream(outContent));

		fs.listDirectoryContent(absolutePath, FilterBy.DEFAULT);

		String expectedOutput = "Path doesn't exists!\n";
		assertEquals(expectedOutput, outContent.toString());

		System.setOut(originalOut);
	}

	@Test
	public void listDirectoryContent_EmptyDirectory_PrintDirectoryContentByDefault() {
		FileSystem fs = new FileSystem();

		String absolutePath = "/home";

		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		PrintStream originalOut = System.out;

		System.setOut(new PrintStream(outContent));

		fs.listDirectoryContent(absolutePath, FilterBy.DEFAULT);

		String expectedOutput = "Directories:\n\nFiles:\n\n";
		assertEquals(expectedOutput, outContent.toString());

		System.setOut(originalOut);
	}

	@Test
	public void listDirectoryContent_EmptyDirectory_PrintDirectoryContentBySize() {
		FileSystem fs = new FileSystem();

		String absolutePath = "/home";

		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		PrintStream originalOut = System.out;

		System.setOut(new PrintStream(outContent));

		fs.listDirectoryContent(absolutePath, FilterBy.SIZE_DESCENDING);

		String expectedOutput = "Files:\n\nDirectories:\n\n";
		assertEquals(expectedOutput, outContent.toString());

		System.setOut(originalOut);
	}
}
