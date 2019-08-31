package fileSystem;

import static org.junit.Assert.*;

import org.junit.Test;

public class PathTest {

	@Test
	public void getAbsolutePath_AbsolutePath_ReturnTheSameAbsolutePath() {
		Path path = new Path();
		
		String absolutePath = "/home/dir1";
		
		assertEquals(absolutePath, path.getAbsolutePath(absolutePath));
	}
	
	@Test
	public void getAbsolutePath_RelativePath_ReturnTheAbsolutePathOfTheRelativePath() {
		Path path = new Path();
		
		String relativePath = "dir1";
		
		String result = path.getAbsolutePath(relativePath);
		String expectedResult = path.getCurrentDirectory() + "/" + relativePath;
		
		assertEquals(expectedResult, result);
	}
	
	@Test
	public void getAbsolutePath_EmptyString_ReturnCurrentDirectory() {
		Path path = new Path();
		
		assertEquals(path.getCurrentDirectory() + "/", path.getAbsolutePath(""));
	}
}
