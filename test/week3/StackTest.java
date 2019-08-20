package week3;

import static org.junit.Assert.*;

import org.junit.Test;

public class StackTest {
	@Test
	public void peek_EmptyStack_ReturnMinusOne() {
		StackAsArray stack = new StackAsArray();
		assertEquals(-1, stack.peek());
	}

	@Test
	public void peek_NonEmptyStack_ReturnTheElementOnTop() {
		StackAsArray stack = new StackAsArray();
		stack.push(1);
		stack.push(2);
		stack.push(3);
		assertEquals(3, stack.peek());
	}

	@Test
	public void peek_NonEmptyStackWithPop_ReturnTheElementOnTop() {
		StackAsArray stack = new StackAsArray();
		stack.push(1);
		stack.push(2);
		stack.push(3);
		stack.pop();
		assertEquals(2, stack.peek());
	}

	@Test
	public void pop_EmptyStack_NothingHappens() {
		StackAsArray stack = new StackAsArray();
		stack.pop();
		stack.pop();
		stack.pop();
		assertEquals(-1, stack.peek());
	}

	@Test
	public void pop_NonEmptyStack_RemoveTheElementOnTop() {
		StackAsArray stack = new StackAsArray();
		stack.push(1);
		stack.push(2);
		stack.push(3);
		stack.pop();
		assertEquals(2, stack.peek());
	}

	@Test
	public void pop_NonEmptyStack_EmptyTheStack() {
		StackAsArray stack = new StackAsArray();
		stack.push(1);
		stack.push(2);
		stack.push(3);
		stack.pop();
		stack.pop();
		stack.pop();
		assertEquals(true, stack.empty());
	}

	@Test
	public void push_EmptyStack_FillTheStack() {
		StackAsArray stack = new StackAsArray();
		assertEquals(true, stack.empty());
		stack.push(1);
		assertEquals(1, stack.peek());
		stack.push(2);
		assertEquals(2, stack.peek());
		stack.push(3);
		assertEquals(3, stack.peek());
		assertEquals(3, stack.size());
	}
}
