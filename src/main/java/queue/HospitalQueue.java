package queue;

import java.util.LinkedList;
import java.util.Queue;

public abstract class HospitalQueue<T>{
	private Queue<T> queue;
	
	public HospitalQueue() {
		queue = new LinkedList<>();
	}
	
	public void add(T item) {
		queue.add(item);
	}
	
	public T callNext() {
		if (!queue.isEmpty()) {
			return queue.poll();
		} else {
			return null;
		}
	}
	
	public boolean isEmpty() {
		return queue.isEmpty();
	}
	
	public void clear() {
		queue.clear();
	}
	
	public void printAll() {
		if(queue.isEmpty()) {
			System.out.println("empty");
			return;
		}
		
		for(T item : queue) {
			System.out.println(item);
		}
	}
}

