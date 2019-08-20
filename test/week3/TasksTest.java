package week3;

import static org.junit.Assert.*;

import org.junit.Test;

public class TasksTest {

	@Test
	public void findMinMissingElementInSortedArray_ArraysWithLastNumberMissing_ReturnTheLastNumber() {
		int[] array1 = { 1, 2, 3, 4, 5 };
		int[] array2 = { 1, 2, 3, 4, 5, 6 };
		assertEquals(6, Tasks.findMinMissingElementInSortedArray(array1));
		assertEquals(7, Tasks.findMinMissingElementInSortedArray(array2));
	}

	@Test
	public void findMinMissingElementInSortedArray_ArraysWithMissingNumberOne_ReturnOne() {
		int[] array1 = { 2, 3, 4, 5 };
		int[] array2 = { 2, 3, 4, 5, 6 };
		assertEquals(1, Tasks.findMinMissingElementInSortedArray(array1));
		assertEquals(1, Tasks.findMinMissingElementInSortedArray(array2));
	}

	@Test
	public void findMinMissingElementInSortedArray_ArraysWithOneMissingNumber_ReturnTheMissingNumber() {
		int[] array1 = { 1, 2, 4, 5 };
		int[] array2 = { 1, 2, 3, 5 };
		int[] array3 = { 1, 3, 4, 5 };

		assertEquals(3, Tasks.findMinMissingElementInSortedArray(array1));
		assertEquals(4, Tasks.findMinMissingElementInSortedArray(array2));
		assertEquals(2, Tasks.findMinMissingElementInSortedArray(array3));
	}

	@Test
	public void findMinMissingElementInSortedArray_ArraysWithSeveralMissingNumbers_ReturnTheSmallestMissingNumber() {
		int[] array1 = { 1, 3, 5 };
		int[] array2 = { 1, 4, 5, 6 };
		int[] array3 = { 1, 2, 3, 4, 6, 8, 10 };

		assertEquals(2, Tasks.findMinMissingElementInSortedArray(array1));
		assertEquals(2, Tasks.findMinMissingElementInSortedArray(array2));
		assertEquals(5, Tasks.findMinMissingElementInSortedArray(array3));
	}

	@Test
	public void areBracketsCorrect_SimpleCorrectBrackets_True() {
		String brackets = "[]{}()";
		assertTrue(Tasks.areBracketsCorrect(brackets));
	}

	@Test
	public void areBracketsCorrect_SimpleInvalidBrackets_False() {
		String brackets = "[]{}()}[]";
		assertFalse(Tasks.areBracketsCorrect(brackets));
	}

	@Test
	public void areBracketsCorrect_CorrectBrackets_True() {
		String brackets = "[()]{}{[()()]()}";
		assertTrue(Tasks.areBracketsCorrect(brackets));
	}

	@Test
	public void areBracketsCorrect_InvalidBrackets_False() {
		String brackets = "[[[]]]{[]}((){})[][{}}[{]}";
		assertFalse(Tasks.areBracketsCorrect(brackets));
	}

	@Test
	public void areBracketsCorrect_NestedCorrectBrackets_True() {
		String brackets = "{{{()[]{}}[][[]]}}((({{}[]})))";
		assertTrue(Tasks.areBracketsCorrect(brackets));
	}

	@Test
	public void expandExpression_Expression_ExpandedExpression() {
		String expr = "AB3(DC)2(F)";
		String result = "ABDCDCDCFF";
		assertEquals(result, Tasks.expandExpression(expr));
	}

	@Test
	public void expandExpression_ExpressionWithNoExpands_ReturnSameExpression() {
		String expr = "alabala";
		assertEquals(expr, Tasks.expandExpression(expr));
	}

	@Test
	public void expandExpression_ExpressionWithNumberForExpand_ReturnExpandedExpression() {
		String expr = "10(Hello!)";
		String result = "Hello!Hello!Hello!Hello!Hello!Hello!Hello!Hello!Hello!Hello!";
		assertEquals(result, Tasks.expandExpression(expr));
	}

	@Test
	public void getNextGreaterElement_ExampleArray_ArrayContaingNextGreaterElementForEachElement() {
		int[] array = { 3, 2, 5, 10, 4 };
		int[] result = {5, 5, 10, -1, -1};
		assertArrayEquals(result, Tasks.getNextGreaterElement(array));
	}
	
	@Test
	public void getNextGreaterElement_Array_ArrayContatingNextGreaterElemenForEachElement() {
		int[] array = {10, 3, 2, 5, 10, 4};
		int[] result = {-1, 5, 5, 10, -1, -1};
		assertArrayEquals(result, Tasks.getNextGreaterElement(array));
	}
	
	@Test
	public void getNextGreaterElement_Array2_ArrayContatingNextGreaterElemenForEachElement() {
		int[] array = {22, 5, 9, 0, 33, 22, 10, 5, 1 , 6};
		int[] result = {33, 9, 33, 33, -1, -1, -1, 6, 6, -1};
		assertArrayEquals(result, Tasks.getNextGreaterElement(array));
	}
	
	@Test
	public void get3NumbersFormingTriangle_ArrayWithTriangleNumbers_ReturnTriangleNumbers() {
		int[] array = {1, 5, 4, 2, 3};
		int[] result = {2, 3, 4};
		assertArrayEquals(result, Tasks.get3NumbersFormingTriangle(array));
	}
	
	@Test
	public void get3NumbersFormingTriangle_ArrayWithNoTriangleNumbers_ReturnEmptyArray() {
		int[] array = {1, 2, 3};
		int[] result = {0, 0, 0};
		assertArrayEquals(result, Tasks.get3NumbersFormingTriangle(array));
	}
	
	@Test
	public void get3NumbersFormingTriangle_ArrayWithSeveralTriangleNumbers_ReturnMinNumbersFormingTriangle() {
		int[] array = { 1, 10, 5, 4, 3, 9, 8, 7, 2};
		int[] result = {2, 3, 4};
		assertArrayEquals(result, Tasks.get3NumbersFormingTriangle(array));
	}
}
