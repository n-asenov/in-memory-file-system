package path;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import path.Path;

public class PathTest {
    private Path path;

    @Before
    public void init() {
	path = new Path();
    }

    @Test
    public void getAbsolutePath_AbsolutePath_ReturnTheSameAbsolutePath() {
	String absolutePath = "/home/dir1/";

	assertEquals(absolutePath, path.getAbsolutePath(absolutePath));
    }

    @Test
    public void getAbsolutePath_RelativePath_ReturnTheAbsolutePathOfTheRelativePath() {
	String relativePath = "dir1";

	String expectedResult = path.getCurrentDirectory() + relativePath + "/";

	assertEquals(expectedResult, path.getAbsolutePath(relativePath));
    }

    @Test
    public void getAbsolutePath_Root_ReturnRootPath() {
	assertEquals("/", path.getAbsolutePath("/"));
    }

    @Test
    public void getAbsolutePath_AbsolutePathWithSpecialDirectories_ReturnAbsolutePathWithoutSpecialDirectories() {
	String pathWithSpecialDirectories = "/home/../home/.././dir1";
	assertEquals("/dir1/", path.getAbsolutePath(pathWithSpecialDirectories));
    }

    @Test
    public void getAbsolutePath_RelativePathWithSpecialDirectories_ReturnAbsolutePath() {
	String pathWithSpecialDirectories = "dir1/.././dir2/./..";
	assertEquals("/home/", path.getAbsolutePath(pathWithSpecialDirectories));
    }

    @Test
    public void getAbsolutePath_PathToParentDirectory_ReturnAbsolutePathOfParent() {
	assertEquals("/", path.getAbsolutePath(".."));
    }

    @Test
    public void getAbsolutePath_EmptyString_ReturnCurrentDirectory() {
	assertEquals(path.getCurrentDirectory(), path.getAbsolutePath(""));
    }

    @Test
    public void getAbsolutePath_UseSpecialDirectoriesInRootDirectory_ReturnAbsolutePathOfRoot() {
	assertEquals("/", path.getAbsolutePath("/.././../."));
    }

    @Test
    public void getAbsolutePath_GoToRootDirectoryAndUseSpecialDirectories_ReturnAbsolutePathToRoot() {
	assertEquals("/", path.getAbsolutePath(".././.."));
    }

    @Test
    public void getAbsolutePath_GetRootPathWhileBeingInRootDirectory_ReturnRootPath() {
	path.setCurrentDirectory("/");
	assertEquals("/", path.getAbsolutePath("/"));
    }
}
