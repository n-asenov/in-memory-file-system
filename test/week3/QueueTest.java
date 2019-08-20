package week3;

import static org.junit.Assert.*;

import org.junit.Test;

public class QueueTest {

	@Test
	public void enqueue_EmptyQueue_FillTheQueue() {
		Queue queue = new Queue();
		queue.enqueue(10);
		assertEquals(1, queue.getSize());
		assertEquals(10, queue.front());
		queue.enqueue(20);
		queue.enqueue(30);
		queue.enqueue(40);
		assertEquals(4, queue.getSize());
		assertEquals(10, queue.front());
	}
	
	@Test
	public void dequeue_NonEmptyQueue_EmptyQueue() {
		Queue queue = new Queue();
		queue.enqueue(10);
		queue.enqueue(20);
		queue.enqueue(30);
		queue.enqueue(40);
		queue.dequeue();
		assertEquals(20, queue.front());
		queue.dequeue();
		assertEquals(30, queue.front());
		queue.dequeue();
		assertEquals(40, queue.front());
		queue.dequeue();
		assertTrue(queue.isEmpty());
	}

	
	
}
