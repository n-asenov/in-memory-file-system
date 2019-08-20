package week3;

public class StackAsArray {
	private static final int MAX_SIZE = 624;
	private int[] stack;
	private int size;
	
	public StackAsArray() {
		this.stack = new int[MAX_SIZE];
		this.size = 0;
	}
	
	public void push(int value) {
		if(size != MAX_SIZE) {
			stack[size] = value;
			size++;
		}
	}
	
	public void pop() {
		if(size > 0) {
			size--;
		}
	}
	
	public int peek() {
		if(size > 0) {
			return stack[size - 1];
		}
		
		return -1;
	}
	
	public int size() {
		return this.size;
	}
	
	public boolean empty() {
		return this.size ==  0;
	}
}
