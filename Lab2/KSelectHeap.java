/* Henry Pratama Suryadi
A0105182R Lab 2 Submission*/

import java.util.Comparator;
import java.util.PriorityQueue;

public class KSelectHeap {
	private PriorityQueue<Integer> maxheap;
	private PriorityQueue<Integer> minheap;
	private int order;
	
	// Initialise the comparator for every heap
	private static final maxHeapComparator maxComp = new maxHeapComparator();
	private static final minHeapComparator minComp = new minHeapComparator();

	private static class maxHeapComparator implements Comparator<Integer> {
		@Override
		public int compare(Integer i, Integer j) {
			return i > j ? -1 : 1 == j ? 0 : 1;
		}
	}

	private static class minHeapComparator implements Comparator<Integer> {
		@Override
		public int compare(Integer i, Integer j) {
			return i > j ? 1 : 1 == j ? 0 : -1;
		}
	}
	
	// Heap constructor
	public KSelectHeap(int input) {
		order = input;
		maxheap = new PriorityQueue<Integer>(11, maxComp);
		minheap = new PriorityQueue<Integer>(11, minComp);
	}

	public boolean isEmpty() {
		return maxheap.size() == 0 && minheap.size() == 0;
	}
	
	public void insert(int input) {
		// Insert to max heap if it is empty
		if (maxheap.isEmpty()) { maxheap.add(input); }
		else {
			// If the input is lower than the top element of max heap, insert to max heap
			// Insert to min heap otherwise
			if (input < maxheap.peek()) { maxheap.add(input); }
			else { minheap.add(input); }
		}

		// If the size of max heap is bigger than k, move the element to min heap
		if (maxheap.size() > order) { 
			minheap.add(maxheap.poll());
		}
		// If the size of max heap is smaller than k and there is any element in min heap, move element to max heap
		else if (maxheap.size() < order && minheap.size() != 0) { 
			maxheap.add(minheap.poll());
		}
	}

	public String peek() {
		// If the size of max heap is lower than k, element is not found
		if (maxheap.size() < order) { return null; }
		else { return maxheap.peek().toString(); }
	}

	public String delete() {
		String result;
		// If the size of max heap is lower than k, element is not found
		if (maxheap.size() < order) { return null; }
		else { 
			// Remove element from max heap, and insert element from min heap to max heap
			result = maxheap.poll().toString();
			if (minheap.size() != 0) maxheap.add(minheap.poll());
			return result;
		}
	}
}