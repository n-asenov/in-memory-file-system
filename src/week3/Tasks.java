package week3;

import java.util.Arrays;
import java.util.Stack;

public class Tasks {
	public static void toBinary(int number) {
		Stack<Integer> stack = new Stack<Integer>();
		int temp = number;

		while (temp > 0) {
			stack.push(temp % 2);
			temp /= 2;
		}

		System.out.print(number + " in binary: ");

		while (!stack.isEmpty()) {
			System.out.print(stack.peek());
			stack.pop();
		}

		System.out.println();
	}

	private static int max(int[] array) {
		int max = array[0];

		for (int i = 1; i < array.length; i++) {
			if (array[i] > max) {
				max = array[i];
			}
		}

		return max;
	}

	public static int findMinMissingElementInRandomArray(int[] array) {
		int size = max(array);

		boolean[] seen = new boolean[size];

		for (int i = 0; i < array.length; i++) {
			seen[array[i] - 1] = true;
		}

		for (int i = 0; i < seen.length; i++) {
			if (!seen[i]) {
				return i + 1;
			}
		}

		return array.length + 1;
	}

	public static int findMinMissingElementInSortedArray(int[] array) {
		int low = 0;
		int high = array.length - 1;
		int result = array.length + 1;

		while (low <= high) {
			int mid = (low + high) / 2;

			if (array[mid] - mid > 1) {
				result = mid + 1;
				high = mid - 1;
			} else {
				low = mid + 1;
			}
		}

		return result;
	}

	public static boolean areBracketsCorrect(String expr) {
		char[] brackets = expr.toCharArray();
		int index = 0;
		boolean flag = true;
		Stack<Character> stack = new Stack<Character>();

		while (index < brackets.length && flag) {
			char symbol = brackets[index];

			if (symbol == '{' || symbol == '[' || symbol == '(') {
				stack.push(symbol);
			} else if (symbol == '}' || symbol == ']' || symbol == ')') {
				if (stack.empty()) {
					flag = false;
				} else {
					int result = symbol - stack.pop();

					if (result != 1 && result != 2) {
						flag = false;
					}
				}
			} else {
				flag = false;
			}

			index++;
		}

		if (!stack.empty()) {
			flag = false;
		}

		return flag;
	}

	public static String expandExpression(String expression) {
		char[] expr = expression.toCharArray();
		StringBuilder result = new StringBuilder();

		for (int index = 0; index < expr.length; index++) {
			if (expr[index] >= '0' && expr[index] <= '9') {
				int number = expr[index] - '0';
				index++;

				while (expr[index] != '(') {
					number *= 10;
					number += (expr[index] - '0');
					index++;
				}
				index++;

				StringBuilder expandWord = new StringBuilder();

				while (expr[index] != ')') {
					expandWord.append(expr[index]);
					index++;
				}

				while (number > 0) {
					result.append(expandWord.toString());
					number--;
				}

			} else {
				result.append(expr[index]);
			}
		}

		return result.toString();
	}

	public static int[] getNextGreaterElement(int[] array) {
		int[] result = new int[array.length];

		Stack<Integer> stack = new Stack<Integer>();
		stack.push(0);

		for (int i = 1; i < array.length; i++) {
			int next = array[i];

			if (!stack.empty()) {
				while (!stack.empty() && array[stack.peek()] < next) {
					result[stack.peek()] = next;
					stack.pop();
				}
			}

			stack.push(i);
		}

		while (!stack.empty()) {
			result[stack.peek()] = -1;
			stack.pop();
		}

		return result;
	}

	public static int[] get3NumbersFormingTriangle(int[] array) {
		int[] result = new int[3];

		Arrays.sort(array);

		for (int i = 0; i < array.length - 2; i++) {
			if(array[i] + array[i + 1] > array[i + 2]) {
				result[0] = array[i];
				result[1] = array[i + 1];
				result[2] = array[i + 2];
				return result;
			}
		}

		return result;
	}

	public static int[] solveStockSpanProblem(int[] input) {
		int[] result = new int[input.length];

		Stack<Integer> stack = new Stack<Integer>();

		stack.push(0);
		result[0] = 1;

		for (int i = 0; i < input.length; i++) {
			while (!stack.isEmpty() && input[stack.peek()] <= input[i]) {
				stack.pop();
			}

			if (stack.isEmpty()) {
				result[i] = i + 1;
			} else {
				result[i] = i - stack.peek();
			}

			stack.push(i);
		}

		return result;
	}
}