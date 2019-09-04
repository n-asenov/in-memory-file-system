package fileSystem;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PathTest {
	private Path path;

	@Before
	public void init() {
		path = new Path();
	}

	@Test
	public void getAbsolutePath_AbsolutePath_ReturnTheSameAbsolutePath() {
		String absolutePath = "/home/dir1";

		assertEquals(absolutePath, path.getAbsolutePath(absolutePath));
	}

	@Test
	public void getAbsolutePath_RelativePath_ReturnTheAbsolutePathOfTheRelativePath() {
		String relativePath = "dir1";

		String expectedResult = path.getCurrentDirectory() + "/" + relativePath;

		assertEquals(expectedResult, path.getAbsolutePath(relativePath));
	}

	@Test
	public void getAbsolutePath_EmptyString_ReturnCurrentDirectory() {
		assertEquals(path.getCurrentDirectory() + "/", path.getAbsolutePath(""));
	}
}
