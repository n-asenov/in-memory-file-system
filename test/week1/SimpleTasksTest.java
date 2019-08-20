package week1;

import static org.junit.Assert.*;

import org.junit.Test;

public class SimpleTasksTest {

	@Test
	public void isOdd_EvenNumber_False() {
		assertFalse(SimpleTasks.isOdd(4));
	}

	@Test
	public void isOdd_OddNumber_True() {
		assertTrue(SimpleTasks.isOdd(3));
	}

	@Test
	public void isOdd_Zero_False() {
		assertFalse(SimpleTasks.isOdd(0));
	}

	@Test
	public void isPrime_PrimeNumber_True() {
		assertTrue(SimpleTasks.isPrime(11));
	}

	@Test
	public void isPrime_BigPrimeNumber_True() {
		assertTrue(SimpleTasks.isPrime(1_000_000_007));
	}

	@Test
	public void isPrime_One_False() {
		assertFalse(SimpleTasks.isPrime(1));
	}

	@Test
	public void isPrime_NegativeOne_False() {
		assertFalse(SimpleTasks.isPrime(-1));
	}

	@Test
	public void isPrime_NonPrimeNumber_False() {
		assertFalse(SimpleTasks.isPrime(1_000_007));
	}

	@Test
	public void isPrime_NegativePrimeNumber_True() {
		assertTrue(SimpleTasks.isPrime(-11));
	}

	@Test
	public void isPrime_NegativeNonPrimeNumber_False() {
		assertFalse(SimpleTasks.isPrime(-1_000_007));
	}

	@Test
	public void isPrime_Zero_False() {
		assertFalse(SimpleTasks.isPrime(0));
	}

	@Test
	public void minElement_ArrayHasNegativeNumber_ReturnNegativeNumber() {
		int[] array = { 0, -1, 2, 3, 5 };
		assertEquals(array[1], SimpleTasks.minElement(array));
	}

	@Test
	public void minElement_OnlyPositiveNumbers_ReturnMinPositiveNumber() {
		int[] array = { 10, 20, 30, 40, 50, 2, 60, 100 };
		assertEquals(array[5], SimpleTasks.minElement(array));
	}

	@Test
	public void minElement_SameNumbers_ReturnMinPositiveNumber() {
		int[] array = { 10, 10, 10, 10, 10 };
		assertEquals(array[0], SimpleTasks.minElement(array));
	}

	@Test
	public void kthMin_1stMin_ReturnMinElement() {
		int[] array = { 1, 2, -3, 4, 5 };
		assertEquals(-3, SimpleTasks.kthMin(1, array));
	}

	@Test
	public void kthMin_3rdMin_ReturnMinElement() {
		int[] array = { 1, 2, 3, 4, 5, 6 };
		assertEquals(3, SimpleTasks.kthMin(3, array));
	}

	@Test
	public void kthMin_5thMin_ReturnMinElement() {
		int[] array = { 10, 20, 30, 40, 50 };
		assertEquals(50, SimpleTasks.kthMin(5, array));
	}

	@Test
	public void getOddOccurrence_ArrayWithOneOddOccurrence_ReturnOddOccurrence() {
		int[] array = { 2, 2, 2, 4, 4, 8, 8, 10, 10, 2, 2 };
		assertEquals(2, SimpleTasks.getOddOccurrence(array));
	}

	@Test
	public void getOddOccurrence_ArrayWithSeveralOddOccurrences_ReturnMinOddOccurence() {
		int[] array = { 10, 10, 10, 5, 5, 3, 3, 3, 4, 4 };
		assertEquals(3, SimpleTasks.getOddOccurrence(array));
	}

	@Test
	public void getOddOccurrence_ArrayWithoutOddOccurrence_ReturnNegativeOne() {
		int[] array = { 10, 10, 20, 20, 100, 100, 2, 2 };
		assertEquals(-1, SimpleTasks.getOddOccurrence(array));
	}

	@Test
	public void getAverage_ArrayWithOneNumber_ReturnThatNumber() {
		int[] array = { 10, 10, 10, 10, 10, 10 };
		assertEquals(10, SimpleTasks.getAverage(array));
	}

	@Test
	public void getAverage_ArrayWithNegativeNumbers_ReturnAverage() {
		int[] array = { -10, -20, -10, -5, -10, -5 };
		assertEquals(-10, SimpleTasks.getAverage(array));
	}

	@Test
	public void getAverage_ArrayWithPositiveNumbers_ReturnAvarege() {
		int[] array = { 10, 20, 30, 40, 50 };
		assertEquals(30, SimpleTasks.getAverage(array));
	}

	@Test
	public void getAverage_EmptyArray_0() {
		int[] array = {};
		assertEquals(0, SimpleTasks.getAverage(array));
	}

	@Test
	public void pow_PowerofZero_One() {
		assertEquals(1, SimpleTasks.pow(20, 0));
	}

	@Test
	public void pow_TwotoPowerOf8_256() {
		assertEquals(256, SimpleTasks.pow(2, 8));
	}

	@Test
	public void pow_PowerOfOddNumber_ReturnAtoPowerOfB() {
		assertEquals(243, SimpleTasks.pow(3, 5));
	}

	@Test
	public void getSmallestMultiple_One_One() {
		assertEquals(1, SimpleTasks.getSmallestMultiple(1));
	}

	@Test
	public void getSmallestMultiple_4_12() {
		assertEquals(12, SimpleTasks.getSmallestMultiple(4));
	}

	@Test
	public void getSmallestMultiple_5_60() {
		assertEquals(60, SimpleTasks.getSmallestMultiple(5));
	}

	@Test
	public void getSmallestMultiple_6_60() {
		assertEquals(60, SimpleTasks.getSmallestMultiple(6));
	}

	@Test
	public void getSmallestMultiple_NegativeNumber_One() {
		assertEquals(1, SimpleTasks.getSmallestMultiple(-5));
	}

	@Test
	public void doubleFactoriel_3_DoubleFactorielOf3() {
		long expectedResult = 720;
		assertEquals(expectedResult, SimpleTasks.doubleFactoriel(3));
	}

	@Test
	public void doubleFactoriel_4_LongOverflow() {
		assertTrue(SimpleTasks.doubleFactoriel(4) < 0);
	}

	@Test
	public void doubleFactoriel_2_FactorielOf2() {
		int expectedValue = 2;
		assertEquals(expectedValue, SimpleTasks.doubleFactoriel(2));
	}

	@Test
	public void kthFactoriel_DoubleFactorielOf3_720() {
		long expectedResult = 720;
		assertEquals(expectedResult, SimpleTasks.kthFactoriel(2, 3));
	}

	@Test
	public void kthFactoriel_kthFactorielOf2_2() {
		int expectedValue = 2;
		assertEquals(expectedValue, SimpleTasks.kthFactoriel(7, 2));
	}

	@Test
	public void kthFactoriel_TripleFactorielOf3_LongOverflow() {
		assertEquals(1, SimpleTasks.kthFactoriel(5, 3));
	}

	@Test
	public void maximalScalarSum_VectorWithZeros_0() {
		int[] a = { 2, 3, 4, 5 };
		int[] b = { 0, 0, 0, 0 };
		assertEquals(0, SimpleTasks.maximalScalarSum(a, b));
	}

	@Test
	public void maximalScalarSum_VectorsWithPositiveNumbers_MaxScalarProduct() {
		int[] a = { 10, 20, 30 };
		int[] b = { 30, 20, 10, 5, 1 };
		assertEquals(1400, SimpleTasks.maximalScalarSum(a, b));
	}

	@Test
	public void maximalScalarSum_EmptyVector_0() {
		int[] a = {};
		int[] b = { 10, 20, 30 };
		assertEquals(0, SimpleTasks.maximalScalarSum(a, b));
	}

	@Test
	public void maxSpan_SingleElement_1() {
		int[] array = { 2 };
		assertEquals(1, SimpleTasks.maxSpan(array));
	}

	@Test
	public void maxSpan_ArrayWithMaxSpanInTheEnd_MaxNumberOfElementsInSpan() {
		int[] array = { 2, 5, 4, 1, 3, 4 };
		assertEquals(4, SimpleTasks.maxSpan(array));
	}

	@Test
	public void maxSpan_ArrayWithMaxSpanInTheEnd2_MaxNumberOfElementsInSpan() {
		int[] array = { 8, 12, 7, 1, 7, 2, 12 };
		assertEquals(6, SimpleTasks.maxSpan(array));
	}

	@Test
	public void maxSpan_ArrayWithMaxSpanInTheEnd3_MaxNumberOfElementsInSpan() {
		int[] array = { 3, 6, 6, 8, 4, 3, 6 };
		assertEquals(6, SimpleTasks.maxSpan(array));
	}

	@Test
	public void equalSumSides_ArrayWithOddLengthAndEqualSumSides_True() {
		int[] array = { 3, 0, -1, 2, 1 };
		assertTrue(SimpleTasks.equalSumSides(array));
	}

	@Test
	public void equalSumSides_ArrayWithEvenLengthAndEqualSumSides_True() {
		int[] array = { 2, 1, 2, 3, 1, 4 };
		assertTrue(SimpleTasks.equalSumSides(array));
	}

	@Test
	public void equalSumSides_ArrayWithOutEqualSumSides_False() {
		int[] array = { 8, 8 };
		assertFalse(SimpleTasks.equalSumSides(array));
	}

	@Test
	public void reverse_EmptyString_ReturnEmptyString() {
		assertEquals("", SimpleTasks.reverse(""));
	}

	@Test
	public void reverse_Palindrome_ReturnTheSameWord() {
		String word = "alabala";
		assertEquals(word, SimpleTasks.reverse(word));
	}

	@Test
	public void reverse_Word_ReversedWord() {
		String word = "word";
		String expectedResult = "drow";
		assertEquals(expectedResult, SimpleTasks.reverse(word));
	}

	@Test
	public void reverseEveryWord_EmptyString_ReturnEmptyString() {
		assertEquals("", SimpleTasks.reverseEveryWord(""));
	}

	@Test
	public void reverseEveryWord_Sentence_ReversedSentence() {
		String argument = "What is this";
		String expectedResult = "tahW si siht";
		assertEquals(expectedResult, SimpleTasks.reverseEveryWord(argument));
	}

	@Test
	public void isPalindrome_EmptyString_True() {
		assertTrue(SimpleTasks.isPalindrome(""));
	}

	@Test
	public void isPalindrome_PalindromeWithOddLength_True() {
		String palindrome = "alabala";
		assertTrue(SimpleTasks.isPalindrome(palindrome));
	}

	@Test
	public void isPalindrome_PalindromeWithEvenLength_True() {
		String palindrome = "abbbba";
		assertTrue(SimpleTasks.isPalindrome(palindrome));
	}

	@Test
	public void isPalindrome_NonPalindromeWord_False() {
		String nonPalindrome = "alabal";
		assertFalse(SimpleTasks.isPalindrome(nonPalindrome));
	}

	@Test
	public void isPalindrome_NegativeNumber_False() {
		assertFalse(SimpleTasks.isPalindrome(-101));
	}

	@Test
	public void isPalindrome_PalindromeNumber_True() {
		assertTrue(SimpleTasks.isPalindrome(10000001));
	}

	@Test
	public void isPalindrome_Digit_True() {
		assertTrue(SimpleTasks.isPalindrome(5));
	}

	@Test
	public void isPalindrome_NonPalindromeNumber_False() {
		assertFalse(SimpleTasks.isPalindrome(1011));
	}

	@Test
	public void getLargestPalindrome_1002_1001() {
		assertEquals(1001, SimpleTasks.getLargestPalindrome(1002));
	}

	@Test
	public void getLargestPalindrome_BigNumber_LargestPalindrome() {
		assertEquals(123454321, SimpleTasks.getLargestPalindrome(123456789));
	}

	@Test
	public void getLargestPalindrome_Digit_DigitMinusOne() {
		int n = 9;
		assertEquals(n - 1, SimpleTasks.getLargestPalindrome(n));
	}

	@Test
	public void copyChars_EmptyString_EmptyString() {
		String word = "";
		assertEquals(word, SimpleTasks.copyChars(word, 200));
	}

	@Test
	public void copyChars_Word_3TimesWord() {
		String word = "nbsp;";
		String expectedResult = "nbsp;nbsp;nbsp;";
		assertEquals(expectedResult, SimpleTasks.copyChars(word, 3));
	}

	@Test
	public void mentions_OverlappingWord_DontCountOverlappingOccurrences() {
		String text = "texttexttexttext";
		String word = "texttext";
		assertEquals(2, SimpleTasks.mentions(word, text));
	}

	@Test
	public void mentions_NonOverLappingWord_ExactOccurrencesOfTheWordInTheText() {
		String text = "whattfwahtfwhatawhathwatwhat";
		String word = "what";
		assertEquals(4, SimpleTasks.mentions(word, text));
	}

	@Test
	public void sumOfNumbers_OnlyDigitsInString_CorrectSum() {
		String str = "1a2b3c4d5c";
		int expectedResult = 15;
		assertEquals(expectedResult, SimpleTasks.sumOfNumbers(str));
	}

	@Test
	public void sumOfNumbers_StringWithNumbers_SumoOfTheNumbers() {
		String str = "100a100b100:100?1000";
		int expectedResult = 1400;
		assertEquals(expectedResult, SimpleTasks.sumOfNumbers(str));
	}

	@Test
	public void isAnagram_EmptyString_True() {
		String word = "";
		assertTrue(SimpleTasks.isAnagram(word, word));
	}

	@Test
	public void isAnagram_AnagramWords_True() {
		String word1 = "elvis";
		String word2 = "lives";
		assertTrue(SimpleTasks.isAnagram(word1, word2));
	}

	@Test
	public void isAnagram_AnagramWords2_True() {
		String word1 = "listen";
		String word2 = "silent";
		assertTrue(SimpleTasks.isAnagram(word1, word2));
	}

	@Test
	public void isAnagram_SimilarWordsButNotAnagrams_False() {
		String word1 = "llisten";
		String word2 = "silent";
		assertFalse(SimpleTasks.isAnagram(word1, word2));
	}

	@Test
	public void hasAnagramOf_StringWithSubstringAnagram_True() {
		String word1 = "asdasdasdlsinetasdasdas";
		String word2 = "silent";
		assertTrue(SimpleTasks.hasAnagramOf(word1, word2));
	}

	@Test
	public void hasAnagramOf_StringWithoutSubstringAnagram_False() {
		String word1 = "asdasdsadsadsadasdsadas";
		String word2 = "elvis";
		assertFalse(SimpleTasks.hasAnagramOf(word1, word2));
	}

	@Test
	public void hasAnagramOf_EmptyStrings_True() {
		String word = "";
		assertTrue(SimpleTasks.hasAnagramOf(word, word));
	}

	@Test
	public void histogram_MatrixWithZerosAndOnes_CorrectHistogram() {
		short[][] matrix = { { 1, 0, 0 }, { 1, 0, 0 }, { 1, 0, 0 } };
		int[] expectedResult = new int[256];
		expectedResult[0] = 6;
		expectedResult[1] = 3;
		assertArrayEquals(expectedResult, SimpleTasks.histogram(matrix));
	}

	@Test
	public void rescale_Matrix2x2_RescaleTo9x9() {
		int[][] original = { { 1, 0 }, { 0, 1 } };
		int[][] expectedResult = { { 1, 1, 1, 1, 1, 0, 0, 0, 0 }, { 1, 1, 1, 1, 1, 0, 0, 0, 0 },
				{ 1, 1, 1, 1, 1, 0, 0, 0, 0 }, { 1, 1, 1, 1, 1, 0, 0, 0, 0 }, { 1, 1, 1, 1, 1, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 1, 1, 1, 1 }, { 0, 0, 0, 0, 0, 1, 1, 1, 1 }, { 0, 0, 0, 0, 0, 1, 1, 1, 1 },
				{ 0, 0, 0, 0, 0, 1, 1, 1, 1 } };

		assertArrayEquals(expectedResult, SimpleTasks.rescale(original, 9, 9));
	}

	@Test
	public void rescale_Matrix2x2_RescaleTo4x4() {
		int[][] original = { { 1, 0 }, { 0, 1 } };
		int[][] expectedResult = { { 1, 1, 0, 0 }, { 1, 1, 0, 0 }, { 0, 0, 1, 1 }, { 0, 0, 1, 1 } };
		assertArrayEquals(expectedResult, SimpleTasks.rescale(original, 4, 4));
	}

	@Test
	public void rescale_Matrix3x3_RescaleTo6x6() {
		int[][] original = { { 1, 0, 1}, { 0, 1, 0 } };
		int[][] expectedResult = { {1, 1, 0, 0, 1, 1}, {1, 1, 0, 0, 1, 1}, {1, 1, 0, 0, 1, 1}, {0, 0, 1, 1, 0, 0}, {0, 0, 1, 1, 0, 0}, {0, 0, 1, 1, 0, 0} };
		assertArrayEquals(expectedResult, SimpleTasks.rescale(original, 6, 6));
	}
	
	@Test
	public void rescale_Matrix2x2_RescaleTo3x3() {
		int[][] original = { {0, 1}, { 1, 0}};
		int[][] expectedResult = { {0, 0, 1}, {0, 0, 1}, {1, 1, 0}};
		assertArrayEquals(expectedResult, SimpleTasks.rescale(original, 3, 3));
	}
}
