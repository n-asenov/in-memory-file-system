package fileSystem.fs;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

public class DirectoryTest {
	@Test
	public void addFile_AddNewFileInDirectory_FileAddedInDirectory() {
		Directory directory = new Directory("test", null);
		String fileName = "f1";

		directory.addFile(fileName);

		int expectedFilesSize = 1;

		assertEquals(fileName, directory.getFiles().get(fileName).getName());
		assertEquals(expectedFilesSize, directory.getFiles().size());
	}

	@Test
	public void addFile_AddSeveralNewFilesInDirectory_FilesAddedInDirectory() {
		Directory directory = new Directory("test", null);

		String fileName1 = "f1";
		String fileName2 = "f2";
		String fileName3 = "f3";

		directory.addFile(fileName1);
		directory.addFile(fileName2);
		directory.addFile(fileName3);

		int expectedFilesSize = 3;

		assertEquals(fileName1, directory.getFiles().get(fileName1).getName());
		assertEquals(fileName2, directory.getFiles().get(fileName2).getName());
		assertEquals(fileName3, directory.getFiles().get(fileName3).getName());
		assertEquals(expectedFilesSize, directory.getFiles().size());
	}

	@Test
	public void addFile_AddExistingFile_PrintMessageOnConsole() {
		Directory directory = new Directory("test");
		String fileName = "f1";
		String expectedResult = "There is already file with such name!\n";
		int expectedSize = 1;

		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		PrintStream originalOut = System.out;

		System.setOut(new PrintStream(outContent));

		directory.addFile(fileName);
		directory.addFile(fileName);

		assertEquals(expectedResult, outContent.toString());
		assertEquals(expectedSize, directory.getFiles().size());

		System.setOut(originalOut);
	}

	@Test
	public void addSubDirectory_addNewDirectory_DirectoryAddedToSubDirectories() {
		Directory directory = new Directory("test");

		String newSubDirectory = "subDir1";

		directory.addSubDirectory(newSubDirectory);

		Directory addedDirectory = directory.getSubDirectories().get(newSubDirectory);

		assertEquals(newSubDirectory, addedDirectory.getName());
		assertSame(directory, addedDirectory.getParent());
		assertEquals(0, addedDirectory.getFiles().size());
		assertEquals(0, addedDirectory.getSubDirectories().size());
	}

	@Test
	public void addSubDirectory_addExistingDirectory_PrintMessageOnConsole() {
		Directory directory = new Directory("Test");

		String subDirectoryName = "dir1";

		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		PrintStream originalOut = System.out;

		System.setOut(new PrintStream(outContent));

		String expectedResult = "There is already directory with such name!\n";
		int expectedSizeOfSubDirectories = 1;

		directory.addSubDirectory(subDirectoryName);
		directory.addSubDirectory(subDirectoryName);

		assertEquals(expectedResult, outContent.toString());
		assertEquals(expectedSizeOfSubDirectories, directory.getSubDirectories().size());

		System.setOut(originalOut);
	}

	@Test
	public void listContent_listContentByDefaultOption_ContentOfDirectoryPrintedOnConsole() {
		Directory directory = new Directory("Test");

		directory.addSubDirectory("dir1");
		directory.addSubDirectory("dir2");
		directory.addSubDirectory("dir3");

		directory.addFile("f1");
		directory.addFile("f2");
		directory.addFile("f3");

		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		PrintStream originalOut = System.out;

		System.setOut(new PrintStream(outContent));

		directory.listContent(FilterBy.DEFAULT);

		String expectedResult = "Directories:\ndir1 dir2 dir3 \nFiles:\nf1 f2 f3 \n";

		assertEquals(expectedResult, outContent.toString());

		System.setOut(originalOut);
	}

	@Test
	public void listContent_listContentBySizeDescending_ContentPrintOnConsoleBySizeDescending() {
		Directory directory = new Directory("Test");

		directory.addSubDirectory("dir1");
		directory.addSubDirectory("dir2");

		directory.addFile("f1");
		directory.addFile("f2");
		directory.addFile("f3");

		directory.getFiles().get("f2").write(1, "Hello, World!", false);
		directory.getFiles().get("f3").write(2, "Hello", false);

		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		PrintStream originalOut = System.out;

		System.setOut(new PrintStream(outContent));
		
		directory.listContent(FilterBy.SIZE_DESCENDING);
		
		String expectedResult = "Files:\nf2 f3 f1 \nDirectories:\ndir1 dir2 \n";
		
		assertEquals(expectedResult, outContent.toString());

		System.setOut(originalOut);
	}

}
