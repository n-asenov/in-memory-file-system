package week3;

public class Queue {
	private Node front;
	private Node rear;
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

	public Queue() {
		front = rear = null;
		size = 0;
	}

	public void enqueue(int data) {
		Node newNode = new Node(data);

		if (size == 0) {
			front = rear = newNode;
		} else {
			rear.setNext(newNode);
			this.rear = newNode;
		}

		size++;
	}

	public void dequeue() {
		if (size > 0) {
			front = front.getNext();
			
			if (front == null) {
				rear = null;
			}

			size--;
		}
	}

	public int front() {
		if (size > 0) {
			return front.getData();
		}

		return -1;
	}

	public int getSize() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}
}
