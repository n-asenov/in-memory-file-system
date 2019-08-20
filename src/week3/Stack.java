package week3;

public class Stack {
	private Node head;
	private int size;

	class Node {
		private int data;
		private Node next;

		public Node(int data) {
			this.data = data;
			this.next = null;
		}

		public Node(int data, Node next) {
			this.data = data;
			this.next = next;
		}

		public int getData() {
			return data;
		}

		public Node getNext() {
			return next;
		}

		public void setData(int data) {
			this.data = data;
		}

		public void setNext(Node next) {
			this.next = next;
		}
	}

	public Stack() {
		head = null;
		size = 0;
	}

	public void push(int data) {
		Node newNode = new Node(data, head);
		head = newNode;
		size++;
	}

	public void pop() {
		if (size > 0) {
			head = head.getNext();
			size--;
		}
	}

	public int peek() {
		if (size > 0) {
			return head.getData();
		}

		return -1;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public int getSize() {
		return size;
	}
}
