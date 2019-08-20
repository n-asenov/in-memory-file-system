package week1;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.Math;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class SimpleTasks {

	public static void maxPlusOne() {
		int maxValue = Integer.MAX_VALUE;
		maxValue += 1;
		System.out.println("Integer max value + 1: " + maxValue);
	}

	public static boolean isOdd(int n) {
		return n % 2 == 1;
	}

	public static boolean isPrime(int n) {
		if (n == -1 || n == 1 || n == 0) {
			return false;
		}

		n = Math.abs(n);
		boolean flag = true;
		double sqrtN = Math.sqrt(n);
		int i = 2;

		while (i <= sqrtN && flag) {
			if (n % i == 0) {
				flag = false;
			}

			i++;
		}

		return flag;
	}

	public static int minElement(int[] array) {
		int min = array[0];

		for (int i = 1; i < array.length; i++) {
			if (min > array[i]) {
				min = array[i];
			}
		}

		return min;
	}

	public static int kthMin(int k, int[] array) {
		Arrays.sort(array);
		return array[k - 1];
	}

	public static int getOddOccurrence(int[] array) {
		Arrays.sort(array);

		int counter = 1;

		for (int i = 0; i < array.length - 1; i++) {
			if (array[i] == array[i + 1]) {
				counter++;
			} else {
				if (isOdd(counter)) {
					return array[i];
				}

				counter = 1;
			}
		}

		if (isOdd(counter)) {
			return array[array.length - 1];
		}

		return -1;
	}

	public static int getAverage(int[] array) {
		if (array.length == 0) {
			return 0;
		}

		int sum = 0;

		for (int i = 0; i < array.length; i++) {
			sum += array[i];
		}

		return sum / array.length;
	}

	public static long pow(int a, int b) {
		if (b == 0) {
			return 1;
		}

		long temp = pow(a, b / 2);

		if (b % 2 == 0) {
			return temp * temp;
		}

		return temp * temp * a;
	}

	private static long GCD(long a, long b) {
		if (b == 0)
			return a;
		return GCD(b, a % b);
	}

	public static long getSmallestMultiple(int n) {
		long result = 1;
		for (int i = 1; i <= n; i++) {
			result = (result * i) / (GCD(result, i));
		}
		return result;
	}

	private static long factoriel(int n) {
		long fact = 1;
		for (int i = 2; i <= n; i++) {
			fact *= i;
		}
		return fact;
	}

	public static long doubleFactoriel(int n) {
		return factoriel((int) factoriel(n));
	}

	public static long kthFactoriel(int k, int n) {
		long result = factoriel(n);

		for (int i = 1; i < k; i++) {
			result = factoriel((int) result);
		}

		return result;
	}

	private static long scalarProduct(int[] a, int[] b) {
		int length = a.length;

		if (b.length < length) {
			length = b.length;
		}

		long scalarProduct = 0;

		for (int i = 0; i < length; i++) {
			scalarProduct += (a[i] * b[i]);
		}

		return scalarProduct;
	}

	private static int[] reverseArray(int[] array) {
		int i = 0, j = array.length - 1;
		for (i = 0; i < array.length / 2; i++, j--) {
			int temp = array[i];
			array[i] = array[j];
			array[j] = temp;
		}
		return array;
	}

	private static void printArray(int[] array) {
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + " ");
		}
		System.out.println("");
	}

	public static long maximalScalarSum(int[] a, int[] b) {
		Arrays.sort(a);
		a = reverseArray(a);
		Arrays.sort(b);
		b = reverseArray(b);

		System.out.println("Permutation of a and b with max scalar product: ");
		printArray(a);
		printArray(b);

		return scalarProduct(a, b);
	}

	public static int maxSpan(int[] numbers) {
		int maxSpan = 1;

		for (int i = 0; i < numbers.length; i++) {
			int currentSpan = 1;
			for (int j = numbers.length - 1; j > i; j--) {
				if (numbers[j] == numbers[i]) {
					currentSpan = j - i + 1;
					break;
				}
			}

			if (currentSpan > maxSpan) {
				maxSpan = currentSpan;
			}
		}

		return maxSpan;
	}

	private static boolean sumSides(int[] numbers, int index) {
		int leftSum = 0;
		int rightSum = 0;

		for (int i = 0; i < index; i++) {
			leftSum += numbers[i];
		}

		for (int i = index + 1; i < numbers.length; i++) {
			rightSum += numbers[i];
		}

		return rightSum == leftSum;
	}

	public static boolean equalSumSides(int[] numbers) {
		for (int i = 1; i < numbers.length - 1; i++) {
			if (sumSides(numbers, i)) {
				return true;
			}
		}

		return false;
	}

	public static String reverse(String argument) {
		char[] str = argument.toCharArray();
		char[] revStr = new char[str.length];

		int idx = 0;
		for (int i = str.length - 1; i >= 0; i--) {
			revStr[idx] = str[i];
			idx++;
		}

		String reversed = new String(revStr);

		return reversed;
	}

	public static String reverseEveryWord(String arg) {
		String[] words = arg.split(" ");
		String[] reversedWords = new String[words.length];

		for (int i = 0; i < words.length; i++) {
			reversedWords[i] = reverse(words[i]);
		}

		String result = reversedWords[0];

		for (int i = 1; i < reversedWords.length; i++) {
			result = result + " " + reversedWords[i];
		}

		return result;
	}

	public static boolean isPalindrome(String arg) {
		String revArg = reverse(arg);

		return arg.equals(revArg);
	}

	public static boolean isPalindrome(long arg) {
		long number = arg;
		long reversed = 0;

		while (number > 0) {
			reversed = (reversed * 10) + number % 10;
			number /= 10;
		}

		return reversed == arg;
	}

	public static long getLargestPalindrome(long n) {
		for (long i = n - 1; i > 0; i--) {
			if (isPalindrome(i)) {
				return i;
			}
		}

		return 0;
	}

	public static String copyChars(String input, int k) {
		String result = "";
		for (int i = 0; i < k; i++) {
			result += input;
		}
		return result;
	}

	public static int mentions(String word, String text) {
		int counter = 0;
		int index = text.indexOf(word, 0);

		while (index != -1) {
			counter++;
			index = text.indexOf(word, index + word.length());
		}

		return counter;
	}

	public static String decodeUrl(String input) {
		input = input.replaceAll("%20", " ");
		input = input.replaceAll("%3A", ":");
		input = input.replaceAll("%3D", "?");
		input = input.replaceAll("%2F", "/");
		return input;
	}

	public static int sumOfNumbers(String input) {
		char[] arr = input.toCharArray();

		int sum = 0;

		int number = 0;

		for (int i = 0; i < arr.length; i++) {
			if (arr[i] >= '0' && arr[i] <= '9') {
				number = (number * 10) + (arr[i] - '0');
			} else {
				sum += number;
				number = 0;
			}
		}

		return sum + number;
	}

	public static boolean isAnagram(String a, String b) {
		if (a.length() != b.length()) {
			return false;
		}

		char[] strA = a.toCharArray();
		char[] strB = b.toCharArray();

		Arrays.sort(strA);
		Arrays.sort(strB);

		for (int i = 0; i < strA.length; i++) {
			if (strA[i] != strB[i]) {
				return false;
			}
		}

		return true;
	}

	public static boolean hasAnagramOf(String a, String b) {
		int wordLength = b.length();
		char[] str = a.toCharArray();

		for (int i = 0; i < str.length - wordLength + 1; i++) {
			String subStr = a.substring(i, i + wordLength);
			if (isAnagram(subStr, b)) {
				return true;
			}
		}

		return false;
	}

	public static int[] histogram(short[][] image) {
		int[] result = new int[256];

		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image[i].length; j++) {
				result[image[i][j]]++;
			}
		}

		return result;
	}

	public static int[][] rescale(int[][] original, int newWidth, int newHeight) {
		int[][] result = new int[newHeight][newWidth];

		double widthScale = newWidth / (double) original[0].length;
		double heightScale = newHeight / (double) original.length;
		
		for (int row = 0; row < newHeight; row++) {
			for (int col = 0; col < newWidth; col++) {
				double x = Math.floor(row / heightScale);
				double y = Math.floor(col / widthScale);
				result[row][col] = original[(int)x][(int)y];
			}
		}

		return result;
	}
	
	public static void convertToGreyscale(String imgPath) {
		File input = new File(imgPath);
		
		try {
			BufferedImage image = ImageIO.read(input);
			int width = image.getWidth();
			int height = image.getHeight();
			
			for(int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					Color c = new Color(image.getRGB(j, i));
					int red = (int)(c.getRed() * 0.299);
					int green = (int)(c.getGreen() * 0.587);
					int blue  = (int)(c.getBlue() * 0.114);
					int sum = red + green + blue;
					Color newColor = new Color(sum, sum, sum);
					image.setRGB(j, i, newColor.getRGB());
				}
			}
			
			File output = new File("grayscale.jpg");
			ImageIO.write(image, "jpg", output);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
